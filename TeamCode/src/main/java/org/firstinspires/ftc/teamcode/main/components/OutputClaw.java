package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.Functions.clamp01;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.CrazyServo;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OutputClaw extends Component {
    private final CrazyServo left, right, pitch, yaw, claw;

    private boolean hangFinished = true;

    @Inject
    public OutputClaw(HardwareMap hardwareMap) {
        left = new CrazyServo(
                new SimpleServo(hardwareMap, RobotConstants.OUTPUT_SERVO_LEFT, 0, 180),
                1,
                0);
        right = new CrazyServo(
                new SimpleServo(hardwareMap, RobotConstants.OUTPUT_SERVO_RIGHT, 0, 180),
                0,
                1
        );
        pitch = new CrazyServo(
                new SimpleServo(hardwareMap, RobotConstants.OUTPUT_SERVO_PITCH, 0, 180),
                1,
                0
        );
        yaw = new CrazyServo(
                new SimpleServo(hardwareMap, RobotConstants.OUTPUT_SERVO_YAW, 0, 180),
                0.15,
                0.8);
        claw = new CrazyServo(
                new SimpleServo(hardwareMap, RobotConstants.OUTPUT_SERVO_CLAW, 0, 180),
                1,
                0.4);
    }

    @Override
    public void init(boolean isAuto) {
        initPosition();
    }

    public void initPosition() {
        setArm(0);
        setPitch(0.6);
        setYaw(0.5);
        closeClaw();
    }

    public void transfer(double yaw) {
        setArm(0.13);
        setPitch(0.62);
        setYaw(yaw);
        openClaw();
    }

    public void basketPlacePos() {
        setArm(0.6);
        setPitch(0.65);
        setYaw(0.5);
    }

    public void basketRelease() {
        openClaw();
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //
            } finally {
                transfer(0.5);
            }
        });
    }

    public void hang(double yaw) {
        if (hangFinished) {
            setYaw(yaw);
            hangFinished = false;
            setArm(0.5);
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(500);
                    setPitch(1);
                    Thread.sleep(300);
                    setArm(0.2);
                    Thread.sleep(500);
                    transfer(0.5);
                } catch (InterruptedException e) {

                } finally {
                    hangFinished = true;
                }
            });
        }
    }

    public void openClaw() {
        claw.setPosition(0);
    }

    public void closeClaw() {
        claw.setPosition(1);
    }

    public void setYaw(double pos) {
        yaw.setPosition(pos);
    }

    public void setPitch(double pos) {
        pitch.setPosition(pos);
    }

    public void setArm(double pos) {
        pos = clamp01(pos);

        left.setPosition(pos);
        right.setPosition(pos);
    }
}
