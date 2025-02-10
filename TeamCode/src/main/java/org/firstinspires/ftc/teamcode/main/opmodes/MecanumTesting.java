package org.firstinspires.ftc.teamcode.main.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.OpModeBase;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.DaggerMainComponent;
import org.firstinspires.ftc.teamcode.main.MainComponent;
import org.firstinspires.ftc.teamcode.main.components.joystick.ControllerTelemetry;
import org.firstinspires.ftc.teamcode.main.components.joystick.MecanumDriveJoystick;

import javax.inject.Inject;

@TeleOp(name = "Mecanum Testing", group = "Test")
public class MecanumTesting extends OpModeBase {
    @Inject
    public MecanumDriveJoystick mecanumDriveJoystick;

    @Inject
    public ControllerTelemetry controllerTelemetry;

    @Override
    protected void startup() {
        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2))).build();

        main.inject(this);

        addComponent(mecanumDriveJoystick);
        addComponent(controllerTelemetry);
    }
}
