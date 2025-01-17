package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.Functions.clamp01;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.CrazyServo;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeExtension extends Component {
    private final Telemetry telemetry;

    private CrazyServo servoLeft, servoRight;

    private double extension;

    @Inject
    public IntakeExtension(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        servoLeft = new CrazyServo(new SimpleServo(hardwareMap, RobotConstants.INTAKE_SERVO_LEFT, 0, 180), 0.3, 0.8);
        servoRight = new CrazyServo(new SimpleServo(hardwareMap, RobotConstants.INTAKE_SERVO_RIGHT, 0, 180), 0.3, 0.8);
    }

    @Override
    public void init(boolean isAuto) {
        closeIntake();
    }

    @Override
    public void loop() {
        telemetry.addData("Intake extension", extension);
    }

    public void extendIntake(double extension) {
        extension = clamp01(extension);
        this.extension = extension;

        servoLeft.setPosition(extension);
        servoRight.setPosition(extension);
    }

    public void closeIntake() {
        extendIntake(0);
    }

    public void extendIntakeFully() {
        extendIntake(1);
    }
}
