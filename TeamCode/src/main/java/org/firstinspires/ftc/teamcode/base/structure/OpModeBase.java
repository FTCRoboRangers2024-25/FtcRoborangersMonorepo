package org.firstinspires.ftc.teamcode.base.structure;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.di.DaggerMainBaseComponent;
import org.firstinspires.ftc.teamcode.base.di.MainBaseComponent;
import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.messaging.IMessageBroadcaster;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.testing.DaggerMainComponent;
import org.firstinspires.ftc.teamcode.testing.MainComponent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

public abstract class OpModeBase extends OpMode {
    @Inject
    public IMessageBroadcaster messageBroadcaster;

    private final List<Component> components = new ArrayList<>();

    protected abstract void startup();

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        MainBaseComponent main = DaggerMainBaseComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2))).build();

        main.inject(this);

        startup();

        messageBroadcaster.addReceiverRange(new HashSet<>(components));

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
