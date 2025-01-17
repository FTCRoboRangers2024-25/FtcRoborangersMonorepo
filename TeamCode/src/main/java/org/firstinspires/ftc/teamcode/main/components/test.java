package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class test extends Component {
    private final Telemetry telemetry;
    private final Motor left;

    @Inject
    public test(Telemetry telemetry, HardwareMap hardwareMap) {
        this.telemetry = telemetry;
        left = new Motor(hardwareMap, "test");
    }

    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
        telemetry.addData("wheel", left.getCurrentPosition());
    }
}
