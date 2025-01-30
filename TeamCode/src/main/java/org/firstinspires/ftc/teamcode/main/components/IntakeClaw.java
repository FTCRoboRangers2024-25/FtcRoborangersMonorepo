package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.CrazyServo;
import org.firstinspires.ftc.teamcode.base.utils.RobotPorts;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeClaw extends Component {
    private final CrazyServo pitch, pitch2, yaw, claw;
    private double currentPitch;
    private boolean clawClosed = false;
    private boolean finishedPickUp = true;

    private boolean transferDone = true;

    @Inject
    public IntakeClaw(HardwareMap hardwareMap) {
        pitch = new CrazyServo(
                new SimpleServo(hardwareMap, RobotPorts.EHS2, 0, 180),
                1,
                0);
        pitch2 = new CrazyServo(
                new SimpleServo(hardwareMap, RobotPorts.EHS0, 0, 180),
                1,
                0
        );
        yaw = new CrazyServo(
                new SimpleServo(hardwareMap, RobotPorts.EHS1, 0, 180),
                0.05,
                0.9);
        claw = new CrazyServo(
                new SimpleServo(hardwareMap, RobotPorts.EHS3, 0, 180),
                0.2,
                0.65);
    }

    @Override
    public void init() {
        intakeInit();
        clawOpen();
    }

    @Override
    public void start() {
        intakePreGrab();
    }

    public void intakeInit() {
        setPitch(0.1);
        setYaw(0.5);
        setPitch2(0.3);
    }

    public void transfer() {
        if (transferDone) {
            transferDone = false;
            setPitch(0.5);
            setPitch2(0.2);
            setYaw(0.5);
            try {
                Thread.sleep(300);
                setPitch2(0.45);
                Thread.sleep(300);
                setPitch(0);
                setPitch2(0.57);
                Thread.sleep(300);
            } catch (InterruptedException e) {

            } finally {
                transferDone = true;
            }
        }
    }

    public void intakePreGrab() {
        intakePostGrab();
        clawOpen();
    }

    private void intakePostGrab() {
        setPitch(0.3);
        setPitch2(1);
    }

    public void intakeGrab() {
        if (finishedPickUp) {
            finishedPickUp = false;
            CompletableFuture.runAsync(() -> {
                try {
                    setPitch(0.55);
                    setPitch2(0.8);
                    Thread.sleep(200);
                    clawClose();
                    Thread.sleep(200);
                    intakePostGrab();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishedPickUp = true;
                }
            });
        }
    }

    public void clawClose() {
        claw.setPosition(1);
        clawClosed = true;
    }

    public void clawOpen() {
        claw.setPosition(0);
        clawClosed = false;
    }

    public void setPitch(double position) {
        pitch.setPosition(position);
        currentPitch = position;
    }

    public void setPitch2(double position) {
        pitch2.setPosition(position);
    }

    public void setYaw(double position) {
        yaw.setPosition(position);
    }

    public void clawToggle() {
        if (clawClosed) {
            clawOpen();
        } else {
            clawClose();
        }
    }
}
