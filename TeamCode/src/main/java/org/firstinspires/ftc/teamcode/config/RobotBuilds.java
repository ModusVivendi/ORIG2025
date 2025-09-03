package org.firstinspires.ftc.teamcode.config;

public class RobotBuilds {
    // A full-featured robot build
    public static final SystemConfiguration COMPETITION_ROBOT = new SystemConfiguration(
            /*enableDrivetrain=*/ true,
            /*enableVerticalSlides=*/ true,
            /*enableHorizontalSlides=*/ false,
            /*enableImuSensor=*/ true,
            /*enableColorSensors=*/ true,
            /*enableDistanceSensors=*/ true
    );

    // A lightweight build for practice or specific tasks
    public static final SystemConfiguration VERTICAL_SLIDES_ROBOT = new SystemConfiguration(
            /*enableDrivetrain=*/ true,
            /*enableVerticalSlides=*/ true,
            /*enableHorizontalSlides=*/ false,
            /*enableImuSensor=*/ false,
            /*enableColorSensors=*/ false,
            /*enableDistanceSensors=*/ false
    );

    // A lightweight build for practice or specific tasks
    public static final SystemConfiguration HORIZONTAL_SLIDES_ROBOT = new SystemConfiguration(
            /*enableDrivetrain=*/ true,
            /*enableVerticalSlides=*/ false,
            /*enableHorizontalSlides=*/ true,
            /*enableImuSensor=*/ false,
            /*enableColorSensors=*/ false,
            /*enableDistanceSensors=*/ false
    );

    // A lightweight build for practice or specific tasks
    public static final SystemConfiguration DRIVE_ROBOT = new SystemConfiguration(
            /*enableDrivetrain=*/ true,
            /*enableVerticalSlides=*/ false,
            /*enableHorizontalSlides=*/ false,
            /*enableImuSensor=*/ false,
            /*enableColorSensors=*/ false,
            /*enableDistanceSensors=*/ false
    );
}
