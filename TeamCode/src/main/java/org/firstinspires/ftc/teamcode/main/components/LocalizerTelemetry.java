package org.firstinspires.ftc.teamcode.main.components;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;
import org.firstinspires.ftc.teamcode.base.structure.Component;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalizerTelemetry extends Component {
    private final TwoWheelTrackingLocalizer twoWheelTrackingLocalizer;
    private final Telemetry telemetry;

    @Inject
    public LocalizerTelemetry(TwoWheelTrackingLocalizer twoWheelTrackingLocalizer, Telemetry telemetry) {
        this.twoWheelTrackingLocalizer = twoWheelTrackingLocalizer;
        this.telemetry = telemetry;
    }


    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
        List<Double> wheels = twoWheelTrackingLocalizer.getWheelPositions();
        telemetry.addData("Parallel", wheels.get(0));
        telemetry.addData("Perpendicular", wheels.get(1));
        telemetry.addData("Heading", twoWheelTrackingLocalizer.getHeading());
    }
}
