package org.firstinspires.ftc.teamcode.base.utils;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.function.BooleanSupplier;

public class RestrictedMotor {
    private final Motor motor;
    private final BooleanSupplier isLow, isHigh;
    private double kStatic = 0;

    /**
     * @param isLow returns true when the speed of the motor shouldn't be negative. <br/>
     * @param isHigh returns true when the speed of the motor shouldn't be positive. <br/>
     */
    public RestrictedMotor(Motor motor, BooleanSupplier isLow, BooleanSupplier isHigh) {
        this.motor = motor;
        this.isLow = isLow;
        this.isHigh = isHigh;
    }

    /**
     * @param isLow returns true when the speed of the motor shouldn't be negative. <br/>
     * @param isHigh returns true when the speed of the motor shouldn't be positive. <br/>
     * @param kStatic is a constant offset of the output of the motor.
     */
    public RestrictedMotor(Motor motor, BooleanSupplier isLow, BooleanSupplier isHigh, double kStatic) {
        this(motor, isLow, isHigh);
        this.kStatic = kStatic;
    }

    /**
     * Speed is clamped to positive if isLow == true. <br/>
     * Speed is clamped to negative if isHigh == true. <br/>
     * Speed is not clamped if in between limits.
     */
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

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public void resetEncoder() {
        motor.resetEncoder();
    }
}
