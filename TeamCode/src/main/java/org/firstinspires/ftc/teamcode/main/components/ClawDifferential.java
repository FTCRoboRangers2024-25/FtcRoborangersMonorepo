package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClawDifferential {
    private final ServoEx servoLeft, servoRight;

    private double pitchAngle, yawDeltaServoAngle;

    private double pitchInversionMultiplier = 1, yawInversionMultiplier = 1;

    public ClawDifferential(HardwareMap hardwareMap, String servoLeftPort, String servoRightPort) {
        servoLeft = new SimpleServo(hardwareMap, servoLeftPort, 0, 355);
        servoRight = new SimpleServo(hardwareMap, servoRightPort, 0, 355);

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

    public void setPitchInverted(boolean inverted) {
        pitchInversionMultiplier = inverted ? -1 : 1;
    }

    public void setYawInverted(boolean inverted) {
        yawInversionMultiplier = inverted ? -1 : 1;
    }

    private void updateServos() {
        servoLeft.turnToAngle(pitchAngle * pitchInversionMultiplier - yawDeltaServoAngle * yawInversionMultiplier);
        servoRight.turnToAngle(pitchAngle * pitchInversionMultiplier + yawDeltaServoAngle * yawInversionMultiplier);
    }
}
