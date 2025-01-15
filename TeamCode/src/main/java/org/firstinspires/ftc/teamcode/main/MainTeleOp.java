package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.OpModeBase;
import org.firstinspires.ftc.teamcode.main.components.ControllerTelemetry;
import org.firstinspires.ftc.teamcode.main.components.IntakeClaw;
import org.firstinspires.ftc.teamcode.main.components.IntakeClawJoystick;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtension;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtensionJoystick;
import org.firstinspires.ftc.teamcode.main.components.LocalizerTelemetry;
import org.firstinspires.ftc.teamcode.main.components.MecanumBase;
import org.firstinspires.ftc.teamcode.main.components.MecanumJoystick;
import org.firstinspires.ftc.teamcode.main.components.OutputExtension;
import org.firstinspires.ftc.teamcode.main.components.OutputExtensionJoystick;

import javax.inject.Inject;

@TeleOp(name = "Main")
public class MainTeleOp extends OpModeBase {
    @Inject
    public MecanumBase mecanumBase;

    @Inject
    public MecanumJoystick mecanumJoystick;

    @Inject
    public ControllerTelemetry controllerTelemetry;

    @Inject
    public IntakeExtension intakeExtension;

    @Inject
    public IntakeExtensionJoystick intakeExtensionJoystick;

    @Inject
    public OutputExtension outputExtension;

    @Inject
    public OutputExtensionJoystick outputExtensionJoystick;

    @Inject
    public IntakeClaw intakeClaw;

    @Inject
    public IntakeClawJoystick intakeClawJoystick;

    @Inject
    public LocalizerTelemetry localizerTelemetry;

    @Override
    public void startup() {
        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2)))
                .build();

        main.inject(this);

        addComponent(mecanumBase);
        addComponent(mecanumJoystick);
        addComponent(controllerTelemetry);
        addComponent(localizerTelemetry);
        addComponent(intakeExtension);
        addComponent(intakeExtensionJoystick);
        addComponent(outputExtension);
        addComponent(outputExtensionJoystick);
        addComponent(intakeClaw);
        addComponent(intakeClawJoystick);
    }
}
