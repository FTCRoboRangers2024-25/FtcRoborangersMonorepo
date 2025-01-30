package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp01;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.CrazyServo;
import org.firstinspires.ftc.teamcode.base.utils.RobotPorts;

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

        servoLeft = new CrazyServo(new SimpleServo(hardwareMap, RobotPorts.CHS3, 0, 180), 0.3, 0.8);
        servoRight = new CrazyServo(new SimpleServo(hardwareMap, RobotPorts.CHS5, 0, 180), 0.3, 0.8);
    }

    @Override
    public void init() {
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
