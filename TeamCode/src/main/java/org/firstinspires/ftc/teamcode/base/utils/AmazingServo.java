package org.firstinspires.ftc.teamcode.base.utils;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp01;
import static org.firstinspires.ftc.teamcode.base.utils.Functions.linearInterpolation;
import static org.firstinspires.ftc.teamcode.base.utils.Functions.targetTolerance;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.AnalogInput;

// TO BE TESTED
public class AmazingServo {
    private final AnalogInput encoder;
    public final ServoEx innerServo;
    private final double lowerLimit, higherLimit;
    private double currentTarget;

    public AmazingServo(ServoEx servo, AnalogInput encoder, double lowerLimit, double higherLimit) {
        innerServo = servo;
        this.encoder = encoder;
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
    }

    public AmazingServo(ServoEx servo, AnalogInput encoder, double lowerLimit, double higherLimit, double initPos) {
        this(servo, encoder, lowerLimit, higherLimit);
        currentTarget = initPos;
        setPosition(initPos);
    }

    public void setPosition(double position) {
        position = clamp01(position);

        double realPosition = linearInterpolation(lowerLimit, higherLimit, position);

        innerServo.setPosition(realPosition);
    }

    public double getPosition() {
        return encoder.getVoltage() / 3.3;
    }

    public boolean isBusy() {
        if (targetTolerance(0, 0.05, Math.abs(currentTarget - getPosition())) == 0) {
            return true;
        }
        return false;
    }
}
