package org.firstinspires.ftc.teamcode.main;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.OpModeBase;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.main.components.ClawTransfer;
import org.firstinspires.ftc.teamcode.main.components.IntakeClaw;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtension;
import org.firstinspires.ftc.teamcode.main.components.MecanumCameraAligner;
import org.firstinspires.ftc.teamcode.main.components.OutputClaw;
import org.firstinspires.ftc.teamcode.main.components.OutputExtension;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import javax.inject.Inject;

@Autonomous(name = "Yellow blocks")
public class AutoYellow extends OpModeBase {

    @Inject
    public SampleMecanumDrive drive;

    @Inject
    public MecanumCameraAligner camera;

    @Inject
    public IntakeClaw intakeClaw;

    @Inject
    public IntakeExtension intakeExtension;

    @Inject
    public OutputExtension outputExtension;

    @Inject
    public OutputClaw outputClaw;

    @Inject
    public ClawTransfer clawTransfer;

    TrajectorySequence trajectory1;
    Trajectory trajectory2;
    Trajectory trajectory3, trajectory4, trajectory5, trajectory6, trajectory7, trajectory8, trajectory9;

    @Override
    protected void startup() {
        isAutonomous();

        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2)))
                .build();

        main.inject(this);

        trajectory3 = drive.trajectoryBuilder(new Pose2d())
                        .lineToLinearHeading(new Pose2d(3, 24, Math.toRadians(-45)))
                                .build();

        trajectory1 = drive.trajectorySequenceBuilder(trajectory3.end())
                .lineToLinearHeading(new Pose2d(20, 14, 0))
                .build();

        trajectory2 = drive.trajectoryBuilder(trajectory1.end(), true)
                        .lineToLinearHeading(new Pose2d(3, 24, Math.toRadians(-45)))
                                .build();

        trajectory4 = drive.trajectoryBuilder(trajectory2.end())
                .lineToLinearHeading(new Pose2d(20, 28, 0))
                .build();

        trajectory5 = drive.trajectoryBuilder(trajectory4.end(), true)
                .lineToLinearHeading(new Pose2d(3, 24, Math.toRadians(-45)))
                .build();

        trajectory6 = drive.trajectoryBuilder(trajectory5.end())
                .lineToLinearHeading(new Pose2d(35, 20, Math.toRadians(90)))
                .build();

        trajectory7 = drive.trajectoryBuilder(trajectory6.end(), true)
                .lineToLinearHeading(new Pose2d(3, 24, Math.toRadians(-45)))
                .build();

        addComponent(camera);
        addComponent(intakeClaw);
        addComponent(intakeExtension);
        addComponent(outputExtension);
        addComponent(outputClaw);
        addComponent(clawTransfer);
    }

    boolean bool1, bool2, bool3, bool4, bool5, bool6, bool7, bool8, bool9, bool10, bool11, bool12, bool13;
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    @Override
    protected void autonomous() {
        if (!bool1) {
            drive.followTrajectory(trajectory3);
            time.reset();
            bool1 = true;
        } else if (!bool4) {
            outputExtension.setTargetPosition(1500);
            bool4 = time.time() > 1000;
            if (bool4) {
                outputClaw.basketPlacePos();
                waitMillis(500);
                outputClaw.basketRelease();
                intakeClaw.intakePreGrab();
                drive.followTrajectorySequence(trajectory1);
                outputExtension.setTargetPosition(0);
                time.reset();
            }
        } else if (!bool2) {
            bool2 = camera.align() && time.time() > 1200;
        } else if (!bool3) {
            intakeClaw.intakeGrab();
            waitMillis(500);
            drive.followTrajectory(trajectory2);
            clawTransfer.transfer();
            waitMillis(1700);
            time.reset();
            bool3 = true;
        } else if (!bool5) {
            outputExtension.setTargetPosition(1500);
            bool5 = time.time() > 1000;
            if (bool5) {
                outputClaw.basketPlacePos();
                waitMillis(500);
                outputClaw.basketRelease();
                drive.followTrajectory(trajectory4);
                time.reset();
            }
        } else if (!bool6) {
            outputExtension.setTargetPosition(0);
            bool6 = camera.align() && time.time() > 1000;
        } else if (!bool7) {
            intakeClaw.intakeGrab();
            waitMillis(500);
            drive.followTrajectory(trajectory5);
            clawTransfer.transfer();
            waitMillis(1700);
            time.reset();
            bool7 = true;
        } else if (!bool8) {
            outputExtension.setTargetPosition(1500);
            bool8 = time.time() > 1000;
            if (bool8) {
                outputClaw.basketPlacePos();
                waitMillis(500);
                outputClaw.basketRelease();
                drive.followTrajectory(trajectory6);
                intakeClaw.setYaw(1);
                time.reset();
            }
        } else if (!bool9) {
            outputExtension.setTargetPosition(0);
            bool9 = camera.align() && time.time() > 1000;
            if (bool9) {
                intakeClaw.intakeGrab();
                waitMillis(500);
                drive.followTrajectory(trajectory7);
                clawTransfer.transfer();
                waitMillis(1700);
                time.reset();
            }
        } else if (!bool10) {
            outputExtension.setTargetPosition(1500);
            bool10 = time.time() > 1000;
            if (bool10) {
                outputClaw.basketPlacePos();
                waitMillis(500);
                outputClaw.basketRelease();
                waitMillis(200);
                time.reset();
            }
        } else if (!bool11) {
            outputExtension.setTargetPosition(0);
            bool11 = time.time() > 800;
        }
        outputExtension.loop();
    }

    private void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
