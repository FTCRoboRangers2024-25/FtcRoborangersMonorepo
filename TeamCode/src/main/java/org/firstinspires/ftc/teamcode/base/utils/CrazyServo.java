package org.firstinspires.ftc.teamcode.base.utils;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp01;
import static org.firstinspires.ftc.teamcode.base.utils.Functions.linearInterpolation;

import com.arcrobotics.ftclib.hardware.ServoEx;

public class CrazyServo {
    public final ServoEx innerServo;
    private final double lowerLimit, higherLimit;

    public CrazyServo(ServoEx servo, double lowerLimit, double higherLimit) {
        innerServo = servo;
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
    }

    public CrazyServo(ServoEx servo, double lowerLimit, double higherLimit, double initPos) {
        this(servo, lowerLimit, higherLimit);
        setPosition(initPos);
    }

    public void setPosition(double position) {
        position = clamp01(position);

        double realPosition = linearInterpolation(lowerLimit, higherLimit, position);

        innerServo.setPosition(realPosition);
    }
}
