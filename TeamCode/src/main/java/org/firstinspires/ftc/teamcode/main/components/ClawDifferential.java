package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.RobotPorts;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClawDifferential extends Component {
    private final ServoEx servoLeft, servoRight;

    private double pitchAngle, yawDeltaServoAngle;

    @Inject
    public ClawDifferential(HardwareMap hardwareMap) {
        servoLeft = new SimpleServo(hardwareMap, RobotPorts.CHS0, 0, 355);
        servoRight = new SimpleServo(hardwareMap, RobotPorts.CHS3, 0, 355);
    }

    @Override
    public void init() {
        setPitch(0.5);
        setYaw(0);
    }

    /**
     * Set the pitch angle of the claw
     * @param pitchAngle The pitch angle of the claw: -90 to 90
     */
    public void setPitch(double pitchAngle) {
        pitchAngle = clamp(-90, 90, pitchAngle);
        this.pitchAngle = 177.5 + pitchAngle;
        updateServos();
    }

    /**
     * Set the yaw angle of the claw
     * @param yawAngle The yaw angle of the claw: -180 -> 180
     */
    public void setYaw(double yawAngle) {
        yawAngle = clamp(-180, 180, yawAngle);
        this.yawDeltaServoAngle = yawAngle * ((double) 18 / 52);
        updateServos();
    }

    private void updateServos() {
        servoLeft.turnToAngle(pitchAngle - yawDeltaServoAngle);
        servoRight.turnToAngle(pitchAngle + yawDeltaServoAngle);
    }
}
