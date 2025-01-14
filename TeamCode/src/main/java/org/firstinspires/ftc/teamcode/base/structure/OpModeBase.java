package org.firstinspires.ftc.teamcode.base.structure;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.di.DaggerMainDefaultComponent;
import org.firstinspires.ftc.teamcode.base.messaging.IMessageBroadcaster;
import org.firstinspires.ftc.teamcode.base.di.MainDefaultComponent;
import org.firstinspires.ftc.teamcode.base.di.MainModule;

import java.util.HashSet;

import javax.inject.Inject;

public abstract class OpModeBase extends OpMode {
    @Inject public IMessageBroadcaster messageBroadcaster;

    protected final HashSet<Component> components = new HashSet<>();

    protected abstract void startup();

    protected void autonomous() {
        telemetry.addLine("No autonomous configured");
        telemetry.update();
    }

    private boolean isAuto = false;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        startup();

        MainDefaultComponent defaultComponent = DaggerMainDefaultComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2))).build();
        defaultComponent.inject(this);

        messageBroadcaster.addReceiverRange(new HashSet<>(components));

        for (Component component : components) {
            component.init(isAuto);
        }
    }

    @Override
    public void start() {
        for (Component component : components) {
            component.onStart(isAuto);
        }
    }

    @Override
    public void loop() {
        if (!isAuto) {
            for (Component component : components) {
                component.loop();
            }

            telemetry.update();
        } else {
            autonomous();
        }
    }

    @Override
    public void stop() {
        for (Component component : components) {
            component.onStop();
        }
    }

    protected void isAutonomous() {
        isAuto = true;
    }

    protected <T extends Component> T addComponent(T instance) {
        components.add(instance);
        return instance;
    }
}
