package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;
import java.util.Map;

@TeleOp(name="Debug Hardware Names", group="Testing")
public class DebugHardwareNames extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.addLine("=== HARDWARE CONFIGURATION DEBUG ===");
        telemetry.addLine();

        // Debug DC Motors
        telemetry.addLine("=== DC MOTORS ===");
        if (hardwareMap.dcMotor.size() == 0) {
            telemetry.addLine("No DC Motors found");
        } else {
            telemetry.addLine("Available motors in hardware map:");
            for (Map.Entry<String, com.qualcomm.robotcore.hardware.DcMotor> entry : hardwareMap.dcMotor.entrySet()) {
                String name = entry.getKey();
                telemetry.addData("  Motor", name);
            }
        }

        // Show what names we're looking for
        telemetry.addLine("Expected motor names from config:");
        telemetry.addData("FL_NAME", "'" + HardwareConfig.DrivetrainConfig.FL_NAME + "'");
        telemetry.addData("FR_NAME", "'" + HardwareConfig.DrivetrainConfig.FR_NAME + "'");
        telemetry.addData("BL_NAME", "'" + HardwareConfig.DrivetrainConfig.BL_NAME + "'");
        telemetry.addData("BR_NAME", "'" + HardwareConfig.DrivetrainConfig.BR_NAME + "'");
        telemetry.addLine();

        // Test each motor individually
        telemetry.addLine("Testing motor initialization:");

        // Test front left
        try {
            DcMotor fl = hardwareMap.dcMotor.get(HardwareConfig.DrivetrainConfig.FL_NAME);
            telemetry.addData("Front Left", "✓ SUCCESS");
        } catch (Exception e) {
            telemetry.addData("Front Left", "✗ FAILED: " + e.getMessage());
        }

        // Test front right
        try {
            DcMotor fr = hardwareMap.dcMotor.get(HardwareConfig.DrivetrainConfig.FR_NAME);
            telemetry.addData("Front Right", "✓ SUCCESS");
        } catch (Exception e) {
            telemetry.addData("Front Right", "✗ FAILED: " + e.getMessage());
        }

        // Test back left
        try {
            DcMotor bl = hardwareMap.dcMotor.get(HardwareConfig.DrivetrainConfig.BL_NAME);
            telemetry.addData("Back Left", "✓ SUCCESS");
        } catch (Exception e) {
            telemetry.addData("Back Left", "✗ FAILED: " + e.getMessage());
        }

        // Test back right
        try {
            DcMotor br = hardwareMap.dcMotor.get(HardwareConfig.DrivetrainConfig.BR_NAME);
            telemetry.addData("Back Right", "✓ SUCCESS");
        } catch (Exception e) {
            telemetry.addData("Back Right", "✗ FAILED: " + e.getMessage());
        }

        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("Check the above information to debug hardware configuration");
            telemetry.update();
            sleep(100);
        }
    }
}