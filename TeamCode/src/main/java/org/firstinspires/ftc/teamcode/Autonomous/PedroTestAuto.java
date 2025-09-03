package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Pedro Test Auto CMR (ce mere!)")
public class PedroTestAuto extends LinearOpMode {

    private Follower follower;
    private Timer pathTimer;
    private int pathState;
    private FtcDashboard dashboard;

    private final Pose startPose = new Pose(0, 0, 0);
    private final Pose forwardPose = new Pose(30, 0, 0);
    private final Pose leftPose = new Pose(30, 0, 0 );

    private PathChain forwardPath, leftPath;

    public void buildPaths() {
        forwardPath = follower.pathBuilder()
                .addPath(new BezierLine(startPose, forwardPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), forwardPose.getHeading())
                .build();

        leftPath = follower.pathBuilder()
                .addPath(new BezierLine(forwardPose, leftPose))
                .setLinearHeadingInterpolation(forwardPose.getHeading(), leftPose.getHeading())
                .build();
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(forwardPath, true);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(leftPath, true);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    setPathState(-1); // Finished
                }
                break;
        }
    }

    @Override
    public void runOpMode() {
        pathTimer = new Timer();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        dashboard = FtcDashboard.getInstance();

        buildPaths();

        waitForStart();

        if (opModeIsActive()) {
            setPathState(0);

            while (opModeIsActive() && pathState != -1) {
                follower.update();
                autonomousPathUpdate();

                Pose robotPose = follower.getPose();

                TelemetryPacket packet = new TelemetryPacket();
                packet.put("path state", pathState);
                packet.put("x", robotPose.getX());
                packet.put("y", robotPose.getY());
                packet.put("heading", Math.toDegrees(robotPose.getHeading()));

                // Draw robot position on field
                packet.fieldOverlay()
                        .setFill("blue")
                        .fillRect(robotPose.getX() - 2, robotPose.getY() - 2, 4, 4);

                dashboard.sendTelemetryPacket(packet);

                idle();
            }
        }
    }
}
