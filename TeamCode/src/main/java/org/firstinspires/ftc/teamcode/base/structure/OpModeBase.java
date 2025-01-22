package org.firstinspires.ftc.teamcode.base.structure;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.List;

public abstract class OpModeBase extends OpMode {
    private final static List<Component> components = new ArrayList<>();

    protected abstract void startup();

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        startup();

        for (Component component : components) {
            component.init();
        }

        telemetry.update();
    }

    @Override
    public void init_loop() {
        for (Component component : components) {
            component.initLoop();
        }

        telemetry.update();
    }

    @Override
    public void start() {
        for (Component component : components) {
            component.start();
        }

        telemetry.update();
    }

    @Override
    public void loop() {
        for (Component component : components) {
            component.loop();
        }

        telemetry.update();
    }

    @Override
    public void stop() {
        for (Component component : components) {
            component.stop();
        }

        telemetry.update();
    }

    protected void addComponent(Component component) {
        components.add(component);
    }
}
