package org.firstinspires.ftc.teamcode.config;

/**
 * Defines various static robot configurations for different purposes.
 * This allows easi switch between a full robot build and a test build
 * without changing code in your OpMode.
 */

public class RobotBuilds {

    /**
     * Configuration for the complete robot with all subsystems.
     * This is the standard build for competition.
     */
    public static final SystemConfiguration FULL_ROBOT = new SystemConfiguration(
            true,   // enableDrivetrain
            true,   // enableVerticalSlides
            true,   // enableHorizontalSlides
            true,   // enableVerticalClaw
            true,   // enableHorizontalClaw
            true,   // enableImuSensor
            true,   // enableColorSensors
            true    // enableDistanceSensors
    );

    /**
     * Configuration for a robot that is a base chassis with vertical slides, but no claws.
     * Useful for testing slide mechanisms separately.
     */
    public static final SystemConfiguration VERTICAL_SLIDE_TEST_ROBOT = new SystemConfiguration(
            true,   // enableDrivetrain
            true,   // enableVerticalSlides
            false,  // enableHorizontalSlides
            false,  // enableVerticalClaw
            false,  // enableHorizontalClaw
            false,  // enableImuSensor
            false,  // enableColorSensors
            false   // enableDistanceSensors
    );

    /**
     * Configuration for a robot that is a base chassis with horizontal slides, but no claws.
     * Useful for testing slide mechanisms separately.
     */
    public static final SystemConfiguration HORIZONTAL_SLIDE_TEST_ROBOT = new SystemConfiguration(
            true,   // enableDrivetrain
            false,  // enableVerticalSlides
            true,   // enableHorizontalSlides
            false,  // enableVerticalClaw
            false,  // enableHorizontalClaw
            false,  // enableImuSensor
            false,  // enableColorSensors
            false   // enableDistanceSensors
    );

    /**
     * Configuration for a robot with a drivetrain only.
     * Useful for testing drive code and Road Runner functionality.
     */
    public static final SystemConfiguration DRIVE_ONLY_ROBOT = new SystemConfiguration(
            true,   // enableDrivetrain
            false,  // enableVerticalSlides
            false,  // enableHorizontalSlides
            false,  // enableVerticalClaw
            false,  // enableHorizontalClaw
            false,  // enableImuSensor
            false,  // enableColorSensors
            false   // enableDistanceSensors
    );
}
