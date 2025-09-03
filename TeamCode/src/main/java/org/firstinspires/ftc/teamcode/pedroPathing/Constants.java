package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(16.2)
            .forwardZeroPowerAcceleration(-25.93)
            .lateralZeroPowerAcceleration(-67.34)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.03, 0, 0, 0.015))
            .translationalPIDFSwitch(4)
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.4, 0, 0.005, 0.0006))
            .headingPIDFCoefficients(new PIDFCoefficients(0.8, 0, 0, 0.01));

    private static MecanumConstants driveConstants = new MecanumConstants()
            .leftFrontMotorName("FL")
            .rightFrontMotorName("FR")
            .leftRearMotorName("BL")
            .rightRearMotorName("BR")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .maxPower(1.0);

    private static ThreeWheelConstants localizerConstants = new ThreeWheelConstants()
            .forwardTicksToInches(0.001989)   // placeholder; tune with Forward Tuner
            .strafeTicksToInches(0.001989)    // placeholder; tune with Lateral Tuner
            .turnTicksToInches(0.001989)      // placeholder; tune with Turn Tuner
            .leftPodY(+6.0)
            .rightPodY(-6.0)
            .strafePodX(-2.5)
            .leftEncoder_HardwareMapName("BL")
            .rightEncoder_HardwareMapName("FR")
            .strafeEncoder_HardwareMapName("OS")
            .leftEncoderDirection(1)   // 1 = forward, -1 = reverse
            .rightEncoderDirection(-1)
            .strafeEncoderDirection(1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .mecanumDrivetrain(driveConstants)
                .threeWheelLocalizer(localizerConstants)
                .build();
    }
}
