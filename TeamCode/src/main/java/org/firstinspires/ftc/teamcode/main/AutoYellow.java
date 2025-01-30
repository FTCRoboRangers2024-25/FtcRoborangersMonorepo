package org.firstinspires.ftc.teamcode.main;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.AutonomousBase;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.main.components.ClawTransfer;
import org.firstinspires.ftc.teamcode.main.components.IntakeClaw;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtension;
import org.firstinspires.ftc.teamcode.main.components.MecanumCameraAligner;
import org.firstinspires.ftc.teamcode.main.components.OutputClaw;
import org.firstinspires.ftc.teamcode.main.components.OutputExtension;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import javax.inject.Inject;

@Autonomous(name = "Auto Yellow")
public class AutoYellow extends AutonomousBase {
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
    Trajectory trajectory3, trajectory4, trajectory5, trajectory6, trajectory7;

    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    @Override
    protected void startup() {
        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2)))
                .build();

        main.inject(this);

        Pose2d basketPos = new Pose2d(2, 25, Math.toRadians(-45));

        trajectory3 = drive.trajectoryBuilder(new Pose2d())
                .lineToLinearHeading(basketPos)
                .build();

        trajectory1 = drive.trajectorySequenceBuilder(trajectory3.end())
                .lineToLinearHeading(new Pose2d(24, 17, 0))
                .build();

        trajectory2 = drive.trajectoryBuilder(trajectory1.end(), true)
                .lineToLinearHeading(basketPos)
                .build();

        trajectory4 = drive.trajectoryBuilder(trajectory2.end())
                .lineToLinearHeading(new Pose2d(24, 28, 0))
                .build();

        trajectory5 = drive.trajectoryBuilder(trajectory4.end(), true)
                .lineToLinearHeading(basketPos)
                .build();

        trajectory6 = drive.trajectoryBuilder(trajectory5.end())
                .lineToLinearHeading(new Pose2d(35, 20, Math.toRadians(90)))
                .build();

        trajectory7 = drive.trajectoryBuilder(trajectory6.end(), true)
                .lineToLinearHeading(basketPos)
                .build();

        drive.followTrajectoryAsync(trajectory3);
        actions.addAction(() -> {
            drive.update();
            outputExtension.setTargetPosition(1580);
            return !drive.isBusy();
        }).addAction(() -> {
            outputClaw.basketPlacePos();
            time.reset();
            drive.followTrajectorySequenceAsync(trajectory1);
            return true;
        }).addAction(() -> time.time() > 500).addAction(() -> {
            outputClaw.basketRelease();
            time.reset();
            return true;
        }).addAction(() -> time.time() > 800).addAction(() -> {
            drive.update();
            intakeClaw.intakePreGrab();
            outputExtension.setTargetPosition(0);
            time.reset();
            return !drive.isBusy();
        }).addAction(() -> (camera.alignYellow() && time.time() > 1200) || time.time() > 2000).addAction(() -> {
            intakeClaw.intakeGrab();
            time.reset();
            drive.followTrajectoryAsync(trajectory2);
            return true;
        }).addAction(() -> time.time() > 500).addAction(() -> {
            clawTransfer.transfer();
            return true;
        }).addAction(() -> {
            drive.update();
            time.reset();
            return !drive.isBusy();
        }).addAction(() -> time.time() > 300).addAction(() -> {
            outputExtension.setTargetPosition(1580);
            return true;
        }).addAction(() -> time.time() > 1000).addAction(() -> {
            outputClaw.basketPlacePos();
            time.reset();
            drive.followTrajectoryAsync(trajectory4);
            return true;
        }).addAction(() -> time.time() > 500).addAction(() -> {
            outputClaw.basketRelease();
            time.reset();
            return true;
        }).addAction(() -> time.time() > 800).addAction(() -> {
            intakeClaw.intakePreGrab();
            outputExtension.setTargetPosition(0);
            drive.update();
            time.reset();
            return !drive.isBusy();
        }).addAction(() -> (camera.alignYellow() && time.time() > 1200) || time.time() > 2000).addAction(() -> {
            intakeClaw.intakeGrab();
            time.reset();
            drive.followTrajectoryAsync(trajectory5);
            return true;
        }).addAction(() -> time.time() > 500).addAction(() -> {
            clawTransfer.transfer();
            return true;
        }).addAction(() -> {
            drive.update();
            time.reset();
            return !drive.isBusy();
        }).addAction(() -> time.time() > 300).addAction(() -> {
            outputExtension.setTargetPosition(1580);
            return true;
        }).addAction(() -> time.time() > 1000).addAction(() -> {
            outputClaw.basketPlacePos();
            time.reset();
            drive.followTrajectoryAsync(trajectory6);
            return true;
        }).addAction(() -> time.time() > 500).addAction(() -> {
            outputClaw.basketRelease();
            time.reset();
            return true;
        }).addAction(() -> time.time() > 800).addAction(() -> {
            drive.update();
            intakeClaw.intakePreGrab();
            outputExtension.setTargetPosition(0);
            time.reset();
            intakeClaw.setYaw(1);
            return !drive.isBusy();
        }).addAction(() -> (camera.alignYellow() && time.time() > 1200) || time.time() > 2000).addAction(() -> {
            intakeClaw.intakeGrab();
            time.reset();
            drive.followTrajectoryAsync(trajectory7);
            return true;
        }).addAction(() -> time.time() > 500).addAction(() -> {
            clawTransfer.transfer();
            return true;
        }).addAction(() -> {
            drive.update();
            time.reset();
            return !drive.isBusy();
        }).addAction(() -> time.time() > 300).addAction(() -> {
            outputExtension.setTargetPosition(1580);
            time.reset();
            return true;
        }).addAction(() -> time.time() > 1000).addAction(() -> {
            outputClaw.basketPlacePos();
            time.reset();
            return true;
        }).addAction(() -> time.time() > 500).addAction(() -> {
            outputClaw.basketRelease();
            time.reset();
            return true;
        }).addAction(() -> time.time() > 800).addAction(() -> {
            outputExtension.setTargetPosition(0);
            time.reset();
            return true;
        }).addAction(() -> time.time() > 1000);

        addComponent(camera);
        addComponent(intakeClaw);
        addComponent(intakeExtension);
        addComponent(outputExtension);
        addComponent(outputClaw);
        addComponent(clawTransfer);
    }
}
