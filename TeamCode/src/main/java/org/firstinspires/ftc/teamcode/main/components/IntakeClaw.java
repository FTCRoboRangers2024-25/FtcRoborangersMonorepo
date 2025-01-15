package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.CrazyServo;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeClaw extends Component {
    private final CrazyServo pitch, yaw, claw;
    private double currentPitch;
    private boolean clawClosed = false;

    @Inject
    public IntakeClaw(HardwareMap hardwareMap) {
        pitch = new CrazyServo(
                new SimpleServo(hardwareMap, RobotConstants.INTAKE_SERVO_PITCH, 0, 180),
                0.1,
                0.8);
        pitch.innerServo.setInverted(true);
        yaw = new CrazyServo(
                new SimpleServo(hardwareMap, RobotConstants.INTAKE_SERVO_YAW, 0, 180),
                0.1,
                0.9);
        claw = new CrazyServo(
                new SimpleServo(hardwareMap, RobotConstants.INTAKE_SERVO_CLAW, 0, 180),
                0.13,
                0.6);
    }

    @Override
    public void init(boolean isAuto) {
        intakeInit();
    }

    private void intakeInit() {
        setPitch(0);
        setYaw(0.5);
        clawOpen();
    }

    public void intakeTransfer() {
        setPitch(0);
    }

    public void intakePreGrab() {
        setPitch(0.7);
        clawOpen();
    }

    public void intakeGrab() {
        if (currentPitch > 0.6) {
            try {
                setPitch(1);
                Thread.sleep(200);
                clawClose();
                Thread.sleep(200);
                setPitch(0.7);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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
