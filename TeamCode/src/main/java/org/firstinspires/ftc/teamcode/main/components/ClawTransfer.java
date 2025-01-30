package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClawTransfer extends Component {

    private final IntakeClaw intakeClaw;
    private final IntakeExtension intakeExtension;
    private final IntakeExtensionJoystick intakeExtensionJoystick;
    private final OutputClaw outputClaw;
    private final Gamepads gamepads;

    private boolean finished = true;
    private double transferYaw = 0.5;

    @Inject
    public ClawTransfer(IntakeClaw intakeClaw, IntakeExtension intakeExtension, IntakeExtensionJoystick intakeExtensionJoystick, OutputClaw outputClaw, Gamepads gamepads) {
        this.intakeClaw = intakeClaw;
        this.intakeExtension = intakeExtension;
        this.intakeExtensionJoystick = intakeExtensionJoystick;
        this.outputClaw = outputClaw;
        this.gamepads = gamepads;
    }

    @Override
    public void loop() {
        if (gamepads.gamepad2.isDown(GamepadKeys.Button.START) && finished) {
            transfer();
        }
    }

    public void transfer() {
        if (!finished) return;
        intakeExtensionJoystick.resetIntakeJoystick();
        intakeExtension.closeIntake();

        CompletableFuture.runAsync(() -> {
            finished = false;
            outputClaw.transfer(transferYaw);
            intakeClaw.transfer();

            try {
                Thread.sleep(500);
                outputClaw.closeClaw();
                Thread.sleep(500);
                intakeClaw.intakePreGrab();
                outputClaw.setYaw(0.5);

            } catch (InterruptedException e) {

            } finally {
                finished = true;
            }
        });
    }
}
