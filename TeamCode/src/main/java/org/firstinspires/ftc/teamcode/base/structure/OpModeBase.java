package org.firstinspires.ftc.teamcode.base.structure;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.List;

public abstract class OpModeBase extends OpMode {
    public final static List<Component> components = new ArrayList<>();

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        for (Component component : components) {
            component.init();
        }
    }

    @Override
    public void init_loop() {
        for (Component component : components) {
            component.initLoop();
        }
    }

    @Override
    public void start() {
        for (Component component : components) {
            component.start();
        }
    }

    @Override
    public void loop() {
        for (Component component : components) {
            component.loop();
        }
    }

    @Override
    public void stop() {
        for (Component component : components) {
            component.stop();
        }
    }
}
