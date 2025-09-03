package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.slides.VerticalSlides;
import org.firstinspires.ftc.teamcode.config.RobotConfig;
import org.firstinspires.ftc.teamcode.config.FieldConfig;
import org.firstinspires.ftc.teamcode.config.SystemConfiguration;
import org.firstinspires.ftc.teamcode.config.RobotBuilds;

@TeleOp(name = "Main TeleOp", group = "ORIG")
//@Disabled
public class MainTeleOp extends LinearOpMode {
    //Subsystems
    private MecanumDrive drive = null;
    private VerticalSlides slides = null;
    private RobotConfig robotConfig = null;
    private SystemConfiguration activeConfig = null;

    // Control variables
    private double targetHeading = 0.0;
    private ElapsedTime runtime = new ElapsedTime();

    // IMU - you'll need to add this when you implement IMU
    // private IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        // Choose your robot build here
        activeConfig = RobotBuilds.COMPETITION_ROBOT;

        // Initialize hardware
        initializeHardware();

        // Setup and configuration
        setupSubsystems();

        // Wait for start
        telemetry.addLine("Robot Ready!");
        telemetry.addLine("Press START to begin TeleOp");
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (isStopRequested()) return;

        // Main control loop
        while (opModeIsActive() && !isStopRequested()) {

            // Drive control
            handleDriveControl();

            // Slide control
            handleSlideControl();

            // Update subsystems, but only if they are enabled
            if (slides != null) {
                slides.update();
            }

            // Telemetry
            updateTelemetry();

            // Small delay to prevent overwhelming the system
            sleep(10);
        }
    }

    /**
     * Initialize all hardware components
     */
    private void initializeHardware() {
        telemetry.addLine("Initializing hardware...");
        telemetry.update();

        try {
            robotConfig = new RobotConfig(hardwareMap, activeConfig);
            if (activeConfig.enableDrivetrain) {
                drive = new MecanumDrive(robotConfig);
            }
            if (activeConfig.enableVerticalSlides) {
                slides = new VerticalSlides(robotConfig);
            }

            telemetry.addLine("✓ Hardware initialized successfully");
        } catch (Exception e) {
            telemetry.addLine("✗ Hardware initialization failed: " + e.getMessage());
            telemetry.update();
            throw e;
        }
    }

    /**
     * Setup subsystem configurations
     */
    private void setupSubsystems() {
        telemetry.addLine("Configuring subsystems...");
        telemetry.update();

        // Reset heading target
        targetHeading = 0.0;

        // TODO: Initialize IMU when implemented
        // imu = robotConfig.getIMUIfEnabled(SensorConfig.IMU_NAME, SensorConfig.ENABLE_IMU);

        telemetry.addLine("✓ Subsystems configured");
        telemetry.update();
    }

    /**
     * Handle drive control from gamepad1
     */
    private void handleDriveControl() {
        if (drive != null) {
            // Get current heading (replace with actual IMU reading)
            double currentHeading = 0.0; // TODO: Get from IMU

            // Drive inputs
            double forward = -gamepad1.left_stick_y;  // Negative because Y is inverted
            double strafe = gamepad1.left_stick_x;

            // Heading control - update target with right stick
            if (Math.abs(gamepad1.right_stick_x) > 0.1) {
                targetHeading += gamepad1.right_stick_x * 0.05; // Adjust sensitivity as needed
            }

            // Reset heading with right bumper
            if (gamepad1.right_bumper) {
                targetHeading = currentHeading; // Maintain current heading
            }

            // Apply drive commands
            drive.driveFieldCentric(forward, strafe, targetHeading, currentHeading);
        }
    }

    /**
     * Handle slide control from gamepad2
     */
    private void handleSlideControl() {
        if (slides != null) {
            // Preset heights using D-pad
            if (gamepad2.dpad_up) {
                slides.setHeight(FieldConfig.JUNCTION_HEIGHT_HIGH_MM, 1000);  // High junction
                telemetry.addLine("→ Moving to HIGH junction");
            } else if (gamepad2.dpad_right) {
                slides.setHeight(FieldConfig.JUNCTION_HEIGHT_MEDIUM_MM, 800); // Medium junction
                telemetry.addLine("→ Moving to MEDIUM junction");
            } else if (gamepad2.dpad_down) {
                slides.setHeight(FieldConfig.JUNCTION_HEIGHT_LOW_MM, 600);    // Low junction
                telemetry.addLine("→ Moving to LOW level");
            } else if (gamepad2.dpad_left) {
                slides.setHeight(FieldConfig.JUNCTION_HEIGHT_GROUND_MM, 400); // Ground level
                telemetry.addLine("→ Moving to GROUND level");
            }

            // Manual slide control with left stick Y
            if (Math.abs(gamepad2.left_stick_y) > 0.1) {
                double currentHeight = slides.getCurrentHeight();
                double adjustment = -gamepad2.left_stick_y * 5.0; // 5mm per update
                double newHeight = currentHeight + adjustment;
                slides.setHeight(newHeight, 500);
            }
        }
    }

    /**
     * Update telemetry display
     */
    private void updateTelemetry() {
        telemetry.addLine("=== ORIG MAIN TELEOP ===");
        telemetry.addData("Runtime", "%.1f sec", runtime.seconds());
        telemetry.addLine("");

        // Drive status
        telemetry.addLine("--- DRIVE ---");
        if (drive != null) {
            telemetry.addData("Target Heading", "%.1f°", Math.toDegrees(targetHeading));
            telemetry.addData("Current Heading", "%.1f°", 0.0); // TODO: Add actual IMU reading
        } else {
            telemetry.addData("Drivetrain", "DISABLED");
        }
        telemetry.addLine("");

        // Slide status
        telemetry.addLine("--- SLIDES ---");
        if (slides != null) {
            telemetry.addData("Current Height", "%.1f mm", slides.getCurrentHeight());
            telemetry.addData("At Target", slides.isAtTarget(5.0) ? "YES" : "NO");
        } else {
            telemetry.addData("Slides", "DISABLED");
        }
        telemetry.addLine("");

        // Controls help
        telemetry.addLine("--- CONTROLS ---");
        telemetry.addLine("GamePad 1: Left stick = drive, Right stick = turn");
        telemetry.addLine("GamePad 2: D-pad = slide presets, Left stick Y = manual");

        telemetry.update();
    }
}