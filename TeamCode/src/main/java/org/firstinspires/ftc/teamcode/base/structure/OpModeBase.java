package org.firstinspires.ftc.teamcode.base.structure;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.di.DaggerMainDefaultComponent;
import org.firstinspires.ftc.teamcode.base.messaging.IMessageBroadcaster;
import org.firstinspires.ftc.teamcode.base.di.MainDefaultComponent;
import org.firstinspires.ftc.teamcode.base.di.MainModule;

import java.util.HashSet;

import javax.inject.Inject;

public abstract class OpModeBase extends LinearOpMode {
    @Inject public IMessageBroadcaster messageBroadcaster;

    protected final HashSet<Component> components = new HashSet<>();

    protected abstract void startup();

    protected void autonomous() {
        telemetry.addLine("No autonomous configured");
        telemetry.update();
    }

    private boolean isAuto = false;

    @Override
    public void runOpMode() {
        startup();

        MainDefaultComponent defaultComponent = DaggerMainDefaultComponent.builder()
                        .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2))).build();
        defaultComponent.inject(this);

        messageBroadcaster.addReceiverRange(new HashSet<>(components));

        for (Component component : components) {
            component.init(isAuto);
        }

        waitForStart();

        if (opModeIsActive()) {
            if (!isAuto) {
                while (opModeIsActive()) {
                    for (Component component : components) {
                        component.loop();
                    }

                    telemetry.update();
                }
            } else {
                autonomous();
            }
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
