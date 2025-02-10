package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.RestrictedMotor;
import org.firstinspires.ftc.teamcode.base.utils.RobotPorts;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Config
public class IntakeExtension extends Component {
    public static PIDCoefficients intakePID = new PIDCoefficients(0, 0, 0);
    private final PIDController pidController = new PIDController(0, 0, 0);

    private final RestrictedMotor extensionMotor;
    private final TouchSensor touchSensor;
    private final Telemetry telemetry;

    private boolean goingToPosition;
    private int targetPosition;

    @Inject
    public IntakeExtension(HardwareMap hardwareMap, Telemetry telemetry) {
        touchSensor = hardwareMap.get(TouchSensor.class, RobotPorts.CHD0);
        this.telemetry = telemetry;
        Motor motor = new Motor(hardwareMap, RobotPorts.EHM0);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        this.extensionMotor = new RestrictedMotor(
                motor,
                touchSensor::isPressed,
                () -> motor.getCurrentPosition() > 500
        );
    }

    @Override
    public void loop() {
        // For FTC Dashboard
        pidController.setPID(intakePID.p, intakePID.i, intakePID.d);

        if (goingToPosition) {
            extensionMotor.set(pidController.calculate(extensionMotor.getCurrentPosition(), targetPosition));
        }

        telemetry.addData("Intake extension going to position", goingToPosition);
        telemetry.addData("Intake extension target", targetPosition);
    }

    public void set(double speed) {
        extensionMotor.set(clamp(-1, 1, speed));
        goingToPosition = false;
    }

    public void goToPosition(int position) {
        targetPosition = position;
        goingToPosition = true;
    }
}
