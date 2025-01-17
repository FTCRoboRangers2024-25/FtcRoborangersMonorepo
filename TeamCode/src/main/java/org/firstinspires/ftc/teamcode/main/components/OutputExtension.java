package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.Functions.clamp;
import static org.firstinspires.ftc.teamcode.base.Functions.clamp01;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Config
public class OutputExtension extends Component {
    public static PIDCoefficients pid = new PIDCoefficients();
    public static PIDCoefficients followerPid = new PIDCoefficients();

    private final Telemetry telemetry;
    private final OutputClaw outputClaw;

    private final Motor motorLeft, motorRight;

    private final TouchSensor magLimitLeft, magLimitRight;

    private final double feedforward = 0.1;
    private double ffMult = 1;
    private final PIDController pidController = new PIDController(0.002, 0, 0);
    private final PIDController followerPidController = new PIDController(0.01, 0, 0.0001);
    private final int minPosition = 0, maxPosition = 1580;
    private int targetPosition;
    private boolean goingToPos;
    private double previousPos;

    @Inject
    public OutputExtension(HardwareMap hardwareMap, Telemetry telemetry, OutputClaw outputClaw) {
        this.telemetry = telemetry;
        motorLeft = new Motor(hardwareMap, RobotConstants.OUTPUT_EXTENSION_LEFT, Motor.GoBILDA.RPM_312);
        motorLeft.setInverted(true);
        motorRight = new Motor(hardwareMap, RobotConstants.OUTPUT_EXTENSION_RIGHT, Motor.GoBILDA.RPM_312);
        motorLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        magLimitLeft = hardwareMap.get(TouchSensor.class, RobotConstants.OUTPUT_LIMIT_LEFT);
        magLimitRight = hardwareMap.get(TouchSensor.class, RobotConstants.OUTPUT_LIMIT_RIGHT);

        this.outputClaw = outputClaw;
    }

    @Override
    public void init(boolean isAuto) {
        motorLeft.resetEncoder();
        motorRight.resetEncoder();
    }

    @Override
    public void loop() {
        // For FTC Dashboard
//        pidController.setPID(pid.p, pid.i, pid.d);
//        followerPidController.setPID(followerPid.p, followerPid.i, followerPid.d);

        double posLeft = motorLeft.getCurrentPosition();
        double posRight = motorRight.getCurrentPosition();
        telemetry.addData("Output target position", targetPosition);
        telemetry.addData("Output extension left", posLeft);
        telemetry.addData("Output extension right", posRight);
        telemetry.addData("Output limit left", magLimitLeft.isPressed());
        telemetry.addData("Output limit right", magLimitRight.isPressed());

        if (goingToPos) {
            double out = pidController.calculate(posLeft, targetPosition);
            motorsSet(out + feedforward * ffMult);
        }

        if (magLimitLeft.isPressed()) {
            motorLeft.resetEncoder();
            ffMult = 0;
        }
        if (magLimitRight.isPressed()) {
            motorRight.resetEncoder();
            ffMult = 0;
        }
        if (!magLimitLeft.isPressed() && !magLimitRight.isPressed()) {
            ffMult = 1;
        }

        if (posLeft > 1000 && previousPos <= 1000) {
            outputClaw.basketPlacePos();
        } else if (posLeft < 1000 && previousPos >= 1000) {
            outputClaw.transfer(0.5);
        }
        previousPos = posLeft;
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = clamp(minPosition, maxPosition, targetPosition);
        goingToPos = true;
    }

    public void set(double speed) {
        double clampedSpeed = clamp(-1, 1, speed);

        motorsSet(clampedSpeed + feedforward * ffMult);

        goingToPos = false;
    }

    public void stop() {
        goingToPos = false;
        motorsSet(0);
    }

    private void motorsSet(double speed) {
        if (magLimitLeft.isPressed() || motorLeft.getCurrentPosition() <= minPosition) {
            motorLeft.set(clamp01(speed));
        } else if (motorLeft.getCurrentPosition() >= maxPosition) {
            motorLeft.set(clamp(-1, 0, speed));
        } else {
            motorLeft.set(speed);
        }

        if (speed == 0) {
            double posLeft = motorLeft.getCurrentPosition();
            double posRight = motorRight.getCurrentPosition();
            motorRight.set(followerPidController.calculate(posRight, posLeft));
        } else {
            if (magLimitRight.isPressed() || motorRight.getCurrentPosition() <= minPosition) {
                motorRight.set(clamp01(speed));
            } else if (motorRight.getCurrentPosition() >= maxPosition) {
                motorRight.set(clamp(-1, 0, speed));
            } else {
                motorRight.set(speed);
            }
        }
    }

    @Override
    public void onStop() {
        motorsSet(0);
    }
}
