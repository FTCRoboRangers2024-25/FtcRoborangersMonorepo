package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.Functions.clamp;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Config
public class OutputExtension extends Component {
    public static PIDCoefficients pid;

    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;

    private Motor motorLeft, motorRight;
    private MotorGroup motors;

    private final double feedforward = 0.1;
    private final PIDController pidController = new PIDController(0.001, 0, 0);
    private final int minPosition = 0, maxPosition = 1000;
    private int targetPosition;
    private boolean goingToPos;

    @Inject
    public OutputExtension(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
    }

    @Override
    public void init(boolean isAuto) {
        motorLeft = new Motor(hardwareMap, RobotConstants.OUTPUT_EXTENSION_LEFT, Motor.GoBILDA.RPM_312);
        motorRight = new Motor(hardwareMap, RobotConstants.OUTPUT_EXTENSION_RIGHT, Motor.GoBILDA.RPM_312);
        motors = new MotorGroup(motorLeft, motorRight);

        motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        // For FTC Dashboard
        pidController.setPID(pid.p, pid.i, pid.d);

        double currentPosition = motors.getPositions().get(0);
        telemetry.addData("Output extension", currentPosition);

        if (goingToPos) {
            double out = pidController.calculate(currentPosition, targetPosition);
            motors.set(out + feedforward);
        }
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = clamp(minPosition, maxPosition, targetPosition);
        goingToPos = true;
    }

    public void set(double speed) {
        double clampedSpeed = clamp(-1, 1, speed);

        motors.set(clampedSpeed + feedforward);

        goingToPos = false;
    }

    public void stop() {
        goingToPos = false;
        motors.set(feedforward);
    }

    @Override
    public void onStop() {
        motors.set(0);
    }
}
