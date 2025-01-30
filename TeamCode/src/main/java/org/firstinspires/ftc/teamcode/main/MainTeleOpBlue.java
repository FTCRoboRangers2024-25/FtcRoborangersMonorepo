package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.TeleOpBase;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.components.ClawTransfer;
import org.firstinspires.ftc.teamcode.main.components.ControllerTelemetry;
import org.firstinspires.ftc.teamcode.main.components.IntakeClaw;
import org.firstinspires.ftc.teamcode.main.components.IntakeClawJoystick;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtension;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtensionJoystick;
import org.firstinspires.ftc.teamcode.main.components.MecanumBase;
import org.firstinspires.ftc.teamcode.main.components.MecanumCameraAligner;
import org.firstinspires.ftc.teamcode.main.components.MecanumJoystickBlue;
import org.firstinspires.ftc.teamcode.main.components.MecanumJoystickRed;
import org.firstinspires.ftc.teamcode.main.components.OutputClaw;
import org.firstinspires.ftc.teamcode.main.components.OutputClawJoystick;
import org.firstinspires.ftc.teamcode.main.components.OutputExtension;
import org.firstinspires.ftc.teamcode.main.components.OutputExtensionJoystick;

import javax.inject.Inject;

@TeleOp(name = "Main Blue")
public class MainTeleOpBlue extends TeleOpBase {
    @Inject
    public MecanumBase mecanumBase;

    @Inject
    public MecanumJoystickBlue mecanumJoystickRed;

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
    public OutputClaw outputClaw;

    @Inject
    public OutputClawJoystick outputClawJoystick;

    @Inject
    public ClawTransfer clawTransfer;

    @Inject
    public MecanumCameraAligner mecanumCameraAligner;

    @Override
    public void startup() {
        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2)))
                .build();

        main.inject(this);

        addComponent(mecanumBase);
        addComponent(mecanumJoystickRed);
        addComponent(controllerTelemetry);
        addComponent(intakeExtension);
        addComponent(intakeExtensionJoystick);
        addComponent(outputExtension);
        addComponent(outputExtensionJoystick);
        addComponent(intakeClaw);
        addComponent(intakeClawJoystick);
        addComponent(outputClaw);
        addComponent(outputClawJoystick);
        addComponent(clawTransfer);
        addComponent(mecanumCameraAligner);
    }
}
