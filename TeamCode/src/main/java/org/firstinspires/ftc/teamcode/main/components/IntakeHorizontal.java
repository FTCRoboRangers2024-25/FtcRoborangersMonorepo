package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp;

import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.RobotPorts;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeHorizontal extends Component {
    private final PController servoController = new PController(0.2);

    private final CRServo crServo;
    private final DistanceSensor distanceSensor;
    private final Telemetry telemetry;

    private final double center = 10, range = 10;

    private double targetDistance;

    @Inject
    public IntakeHorizontal(HardwareMap hardwareMap, Telemetry telemetry) {
        crServo = new CRServo(hardwareMap, RobotPorts.CHS2);
        distanceSensor = hardwareMap.get(DistanceSensor.class, RobotPorts.CHI2C1);
        this.telemetry = telemetry;
        setTargetDistance(0);
    }

    @Override
    public void loop() {
        crServo.set(servoController.calculate(distanceSensor.getDistance(DistanceUnit.CM), targetDistance));

        telemetry.addData("Intake horizontal target distance", targetDistance);
        telemetry.addData("Intake horizontal distance", distanceSensor.getDistance(DistanceUnit.CM));
    }

    /**
     * The target of the horizontal sliding part on the intake
     * @param cm The distance in cm from the center
     */
    public void setTargetDistance(double cm) {
        targetDistance = clamp(center - range, center + range, cm);
    }
}
