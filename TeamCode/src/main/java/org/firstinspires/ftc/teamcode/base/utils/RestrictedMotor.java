package org.firstinspires.ftc.teamcode.base.utils;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.function.BooleanSupplier;

// TO BE TESTED
public class RestrictedMotor {
    private final Motor motor;
    private final BooleanSupplier isLow, isHigh;
    private double kStatic = 0;

    public RestrictedMotor(Motor motor, BooleanSupplier isLow, BooleanSupplier isHigh) {
        this.motor = motor;
        this.isLow = isLow;
        this.isHigh = isHigh;
    }

    public RestrictedMotor(Motor motor, BooleanSupplier isLow, BooleanSupplier isHigh, double kStatic) {
        this(motor, isLow, isHigh);
        this.kStatic = kStatic;
    }

    public void set(double speed) {
        if (isLow.getAsBoolean()) {
            speed = clamp(0, 1, speed);
        } else if (isHigh.getAsBoolean()) {
            speed = clamp(-1, 0, speed);
        } else {
            speed = clamp(-1, 1, speed);
        }

        motor.set(speed + kStatic);
    }
}
