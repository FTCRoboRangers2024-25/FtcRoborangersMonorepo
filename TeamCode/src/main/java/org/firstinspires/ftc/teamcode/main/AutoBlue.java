package org.firstinspires.ftc.teamcode.main;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.OpModeBase;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.main.components.ClawTransfer;
import org.firstinspires.ftc.teamcode.main.components.IntakeClaw;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtension;
import org.firstinspires.ftc.teamcode.main.components.MecanumCameraAligner;
import org.firstinspires.ftc.teamcode.main.components.MecanumCameraBlueAligner;
import org.firstinspires.ftc.teamcode.main.components.OutputClaw;
import org.firstinspires.ftc.teamcode.main.components.OutputExtension;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import javax.inject.Inject;

@Autonomous(name = "Blue blocks")
public class AutoBlue extends OpModeBase {
    @Inject
    public SampleMecanumDrive drive;

    @Inject
    public MecanumCameraBlueAligner camera;

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

    Trajectory trajectory1, trajectory2, trajectory3, trajectory4;

    @Override
    protected void startup() {
        isAutonomous();

        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2)))
                .build();

        main.inject(this);

        trajectory1 = drive.trajectoryBuilder(new Pose2d())
                        .back(30)
                                .build();

        trajectory2 = drive.trajectoryBuilder(trajectory1.end())
                        .lineToConstantHeading(new Vector2d(-10, 55))
                                .build();

        trajectory3 = drive.trajectoryBuilder(trajectory2.end())
                .lineToConstantHeading(new Vector2d(-34, -10))
                .build();

        trajectory4 = drive.trajectoryBuilder(trajectory3.end())
                .lineToConstantHeading(new Vector2d(-10, 55))
                .build();

        addComponent(camera);
        addComponent(intakeClaw);
        addComponent(intakeExtension);
        addComponent(outputExtension);
        addComponent(outputClaw);
        addComponent(clawTransfer);
    }

    boolean bool1, bool2, bool3, bool4, bool5, bool6;
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    @Override
    protected void autonomous() {
        if (!bool1) {
            drive.followTrajectory(trajectory1);
            outputClaw.hang(0.5);
            time.reset();
            bool1 = true;
        } else if (!bool2) {
            bool2 = time.time() > 2000;
            if (bool2) {
                drive.followTrajectory(trajectory2);
                intakeClaw.intakePreGrab();
                time.reset();
            }
        } else if (!bool3) {
            bool3 = camera.align() && time.time() > 1000;
            if (bool3) {
                intakeClaw.intakeGrab();
                waitMillis(500);
                drive.followTrajectory(trajectory3);
                clawTransfer.transfer();
                waitMillis(1700);
                intakeClaw.intakePreGrab();
                waitMillis(400);
                outputClaw.hang(1);
                waitMillis(2000);
                drive.followTrajectory(trajectory4);
            }
        }
    }

    private void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
