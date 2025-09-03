package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.PoseStorage;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

@TeleOp(name="CMR_tele", group = "CMR")
public class CMR_tele extends LinearOpMode {

    // Drive system
    private SampleMecanumDrive drive;

    // Vertical slider motors
    private DcMotorEx VSL, VSR;

    // Arm rotation servos (servo programmer controlled)
    private Servo SL, SR;

    // Claw servo
    private Servo SC;

    // Vertical slide positions (encoder ticks)
    private static final int SLIDE_BOTTOM = 50;    // Low position - right bumper
    private static final int SLIDE_MIDDLE = 600;   // Mid position - right trigger
    private static final int SLIDE_TOP = 1500;      // High position - left bumper
    private static final int SLIDE_TOLERANCE = 20;  // Position tolerance

    // Servo positions
    private static final double ARM_MIN_POS = 0.0;
    private static final double ARM_MAX_POS = 1.0;
    private static final double ARM_INCREMENT = 0.02;
    private static final double CLAW_OPEN = 0.0;
    private static final double CLAW_CLOSED = 0.8;

    private static final double ARM_UP = 0.5;
    private static final double ARM_DOWN = 0.8 ;

    // State tracking
    private enum SlideState {
        IDLE,
        MOVING_TO_POSITION,
        MANUAL_CONTROL
    }

    private SlideState slideState = SlideState.IDLE;
    private int targetSlidePosition = SLIDE_BOTTOM;
    private double currentArmPosition = 0.5; // Start at middle position
    private boolean isClawOpen = true;
    private boolean IsArmUp = true;

    // PID constants for slides
    private static final double SLIDE_P = 0.003;
    private static final double SLIDE_MAX_POWER = 0.8;

    // Timing
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime buttonCooldown = new ElapsedTime();
    private static final double COOLDOWN_TIME = 0.3; // 300ms cooldown

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize hardware
        initializeHardware();

        // Setup motors
        setupMotors();

        // Wait for start
        telemetry.addLine("CMR TeleOp Ready");
        telemetry.addLine("RB = Low, RT = Mid, LB = High");
        telemetry.addLine("Left stick = Arm, X/A = Claw");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            // Handle drive
            handleDrive();

            // Handle vertical slides
            handleVerticalSlides();

            // Handle arm servos
            handleArmServos();

            // Handle claw
            handleClaw();

            // Update telemetry
            updateTelemetry();

            // Update drive
            drive.update();
        }
    }

    private void initializeHardware() {
        // Initialize drive
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(PoseStorage.currentPose);

        // Initialize slide motors
        VSL = hardwareMap.get(DcMotorEx.class, "VSL");
        VSR = hardwareMap.get(DcMotorEx.class, "VSR");

        // Initialize servos
        SL = hardwareMap.get(Servo.class, "SL");
        SR = hardwareMap.get(Servo.class, "SR");
        SC = hardwareMap.get(Servo.class, "SC");
    }

    private void setupMotors() {
        // Setup slide motors
        VSL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VSR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        VSL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        VSR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        VSL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        VSR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set motor directions (adjust based on your robot)
        VSL.setDirection(DcMotor.Direction.REVERSE);
        VSR.setDirection(DcMotor.Direction.FORWARD);

        // Initialize servo positions
        SL.setPosition(currentArmPosition);
        SR.setPosition(1.0 - currentArmPosition); // Mirrored
        SC.setPosition(CLAW_OPEN);
        SL.setPosition(ARM_DOWN);
        SR.setPosition(ARM_DOWN);

        // Set drive mode
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void handleDrive() {
        // Standard mecanum drive
        double y = -gamepad1.left_stick_y;  // Forward/back
        double x = gamepad1.left_stick_x;   // Strafe
        double rx = -gamepad1.right_stick_x; // Rotation

        // Apply deadzones
        if (Math.abs(y) < 0.1) y = 0;
        if (Math.abs(x) < 0.1) x = 0;
        if (Math.abs(rx) < 0.1) rx = 0;

        // Create drive vector
        Vector2d input = new Vector2d(y, x);

        // Set drive power
        drive.setWeightedDrivePower(
                new Pose2d(
                        input.getX(),
                        input.getY(),
                        rx
                )
        );
    }

    private void handleVerticalSlides() {
        // Check for position buttons (with cooldown to prevent spam)
        if (buttonCooldown.seconds() > COOLDOWN_TIME) {
            if (gamepad1.dpad_down) {
                // Low position
                setSlideTarget(SLIDE_BOTTOM);
                buttonCooldown.reset();
            } else if ((gamepad1.dpad_right)||(gamepad1.dpad_left)) {
                // Middle position
                setSlideTarget(SLIDE_MIDDLE);
                buttonCooldown.reset();
            } else if (gamepad1.dpad_up) {
                // High position
                setSlideTarget(SLIDE_TOP);
                buttonCooldown.reset();
            }
        }

        // Manual control override
        double manualPower = -gamepad2.left_stick_y;
        if (Math.abs(manualPower) > 0.1) {
            slideState = SlideState.MANUAL_CONTROL;

            // Force manual mode
            VSL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            VSR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



            VSL.setPower(manualPower);
            VSR.setPower(manualPower);
        } else if (slideState == SlideState.MANUAL_CONTROL) {
            // Stick released → stop slides
            slideState = SlideState.IDLE;
            stopSlides();
        }

        // Automatic position control
        if (slideState == SlideState.MOVING_TO_POSITION) {
            updateSlidePosition();
        }
    }

    private void handleArmServos() {
        // X to open, A to close
        if ((gamepad1.right_trigger>0.1) && !IsArmUp) {
            SL.setPosition(ARM_UP);
            SR.setPosition(ARM_UP);
            IsArmUp = true;
        } else if (gamepad1.right_bumper && IsArmUp) {
            SL.setPosition(ARM_DOWN);
            SR.setPosition(ARM_DOWN);
            IsArmUp= false;
        }
    }

    private void handleClaw() {
        // X to open, A to close
        if (gamepad1.circle && !isClawOpen) {
            SC.setPosition(CLAW_OPEN);
            isClawOpen = true;
        } else if (gamepad1.square && isClawOpen) {
            SC.setPosition(CLAW_CLOSED);
            isClawOpen = false;
        }
    }

    private void setSlideTarget(int target) {
        targetSlidePosition = target;
        slideState = SlideState.MOVING_TO_POSITION;
    }

    private void updateSlidePosition() {
        int currentPos = getCurrentSlidePosition();
        int error = targetSlidePosition - currentPos;

        // Check if at target
        if (Math.abs(error) < SLIDE_TOLERANCE) {
            slideState = SlideState.IDLE;
            stopSlides();
            return;
        }

        // Simple proportional control
        double power = error * SLIDE_P;
        power = Range.clip(power, -SLIDE_MAX_POWER, SLIDE_MAX_POWER);

        // Apply safety limits


        // Set motor power
        VSL.setPower(power);
        VSR.setPower(power);
    }



    private int getCurrentSlidePosition() {
        return (VSL.getCurrentPosition() + VSR.getCurrentPosition()) / 2;
    }

    private void stopSlides() {
        VSL.setPower(0);
        VSR.setPower(0);
    }

    private void updateTelemetry() {
        telemetry.addLine("=== CMR TeleOp Status ===");

        // Drive info
        telemetry.addData("Drive Mode", "Field Centric");

        // Slide info
        telemetry.addLine("\n=== Vertical Slides ===");
        telemetry.addData("State", slideState);
        telemetry.addData("Current Pos", getCurrentSlidePosition());
        telemetry.addData("Target Pos", targetSlidePosition);
        telemetry.addData("Left Power", VSL.getPower());
        telemetry.addData("Right Power", VSR.getPower());

        // Arm info
        telemetry.addLine("\n=== Arm Servos ===");
       // telemetry.addData("Position", (isArmInPosition1) ? "Position 1" : "Position 2");
        telemetry.addData("Left Servo", String.format("%.2f", SL.getPosition()));
        telemetry.addData("Right Servo", String.format("%.2f", SR.getPosition()));

        // Claw info
        telemetry.addLine("\n=== Claw ===");
        telemetry.addData("State", isClawOpen ? "OPEN" : "CLOSED");
        telemetry.addData("Position", String.format("%.2f", SC.getPosition()));

        // Controls reminder
        telemetry.addLine("\n=== Controls ===");
        telemetry.addLine("GP2: RB=Low, RT=Mid, LB=High");
        telemetry.addLine("GP2: Left stick=Manual slides");
        telemetry.addLine("GP2: Right stick=Arm, X/A=Claw");

        telemetry.update();
    }
}