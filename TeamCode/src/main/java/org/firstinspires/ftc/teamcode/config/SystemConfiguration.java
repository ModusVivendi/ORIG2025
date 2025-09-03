package org.firstinspires.ftc.teamcode.config;

public class SystemConfiguration {
    public boolean enableDrivetrain;
    public boolean enableVerticalSlides;
    public boolean enableHorizontalSlides;
    public boolean enableImuSensor;
    public boolean enableColorSensors;
    public boolean enableDistanceSensors;

    public SystemConfiguration(
            boolean enableDrivetrain,
            boolean enableVerticalSlides,
            boolean enableHorizontalSlides,
            boolean enableImuSensor,
            boolean enableColorSensors,
            boolean enableDistanceSensors) {
        this.enableDrivetrain = enableDrivetrain;
        this.enableVerticalSlides = enableVerticalSlides;
        this.enableHorizontalSlides = enableHorizontalSlides;
        this.enableImuSensor = enableImuSensor;
        this.enableColorSensors = enableColorSensors;
        this.enableDistanceSensors = enableDistanceSensors;
    }
}
