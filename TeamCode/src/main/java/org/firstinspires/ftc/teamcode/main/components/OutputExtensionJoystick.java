package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.Functions.targetTolerance;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OutputExtensionJoystick extends Component {
    private final OutputExtension outputExtension;
    private final Gamepads gamepads;

    private boolean goingToPos = false;

    @Inject
    public OutputExtensionJoystick(OutputExtension outputExtension, Gamepads gamepads) {
        this.outputExtension = outputExtension;
        this.gamepads = gamepads;
    }

    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
        double joystickInput = targetTolerance(0, RobotConstants.STICK_TOLERANCE, gamepads.gamepad2.getRightY());

        if (joystickInput != 0 || !goingToPos) {
            outputExtension.set(-joystickInput);
            goingToPos = false;
        }

        if (gamepads.gamepad2.getButton(GamepadKeys.Button.A)) {
            outputExtension.setTargetPosition(1000);
            goingToPos = true;
        } else if (gamepads.gamepad2.getButton(GamepadKeys.Button.B)) {
            outputExtension.setTargetPosition(0);
            goingToPos = true;
        }
    }
}
