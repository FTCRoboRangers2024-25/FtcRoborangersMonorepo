package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.OpModeBase;
import org.firstinspires.ftc.teamcode.main.components.ControllerTelemetry;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtension;
import org.firstinspires.ftc.teamcode.main.components.MecanumBase;
import org.firstinspires.ftc.teamcode.main.components.MecanumJoystick;

import javax.inject.Inject;

@TeleOp(name = "Test")
public class TestTeleOp extends OpModeBase {
    @Inject
    public MecanumBase mecanumBase;

    @Inject
    public MecanumJoystick mecanumJoystick;

    @Inject
    public ControllerTelemetry controllerTelemetry;

    @Inject
    public IntakeExtension intakeExtension;

    @Override
    public void startup() {
        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2)))
                .build();

        main.inject(this);

        addComponent(mecanumBase);
        addComponent(mecanumJoystick);
        addComponent(controllerTelemetry);
        addComponent(intakeExtension);
    }
}
