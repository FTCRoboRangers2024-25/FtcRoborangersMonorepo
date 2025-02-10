package org.firstinspires.ftc.teamcode.main.components.joystick;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.components.IntakeHorizontal;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeHorizontalJoystick extends Component {
    private final IntakeHorizontal intakeHorizontal;
    private final Gamepads gamepads;

    @Inject
    public IntakeHorizontalJoystick(IntakeHorizontal intakeHorizontal, Gamepads gamepads) {
        this.intakeHorizontal = intakeHorizontal;
        this.gamepads = gamepads;
    }

    @Override
    public void loop() {
        if (gamepads.gamepad2.isDown(GamepadKeys.Button.X)) {
            intakeHorizontal.setTargetDistance(-5);
        } else if (gamepads.gamepad2.isDown(GamepadKeys.Button.Y)) {
            intakeHorizontal.setTargetDistance(5);
        }
    }
}
