package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OutputClawJoystick extends Component {
    private final OutputClaw outputClaw;
    private final Gamepads gamepads;

    private double hangYaw = 0.5;

    @Inject
    public OutputClawJoystick(OutputClaw outputClaw, Gamepads gamepads) {
        this.outputClaw = outputClaw;
        this.gamepads = gamepads;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {
//        if (gamepads.gamepad2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5) {
//            outputClaw.basketRelease();
//        }

        if (gamepads.gamepad1.isDown(GamepadKeys.Button.Y)) {
            outputClaw.basketRelease();
        }

        if (gamepads.gamepad1.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
            outputClaw.hang(hangYaw);
        }

        if (gamepads.gamepad1.isDown(GamepadKeys.Button.DPAD_LEFT)) {
            hangYaw = 0;
        } else if (gamepads.gamepad1.isDown(GamepadKeys.Button.DPAD_RIGHT)) {
            hangYaw = 1;
        } else {
            hangYaw = 0.5;
        }
    }
}
