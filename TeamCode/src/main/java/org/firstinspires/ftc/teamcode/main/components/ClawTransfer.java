package org.firstinspires.ftc.teamcode.main.components;

import android.icu.util.Output;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.structure.Component;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClawTransfer extends Component {

    private final IntakeClaw intakeClaw;
    private final OutputClaw outputClaw;
    private final Gamepads gamepads;

    private boolean finished = true;
    private double transferYaw = 0.5;

    @Inject
    public ClawTransfer(IntakeClaw intakeClaw, OutputClaw outputClaw, Gamepads gamepads) {
        this.intakeClaw = intakeClaw;
        this.outputClaw = outputClaw;
        this.gamepads = gamepads;
    }

    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
        if (gamepads.gamepad2.isDown(GamepadKeys.Button.START) && finished) {
            transfer();
        }
    }

    public void transfer() {
        if (!finished) return;

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
