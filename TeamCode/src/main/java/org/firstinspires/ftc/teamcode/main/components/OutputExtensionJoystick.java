package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.targetTolerance;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OutputExtensionJoystick extends Component {
    private final OutputExtension outputExtension;
    private final Gamepads gamepads;
    private final ToggleButtonReader joystickToggle;

    private boolean goingToPos = false;

    @Inject
    public OutputExtensionJoystick(OutputExtension outputExtension, Gamepads gamepads) {
        this.outputExtension = outputExtension;
        this.gamepads = gamepads;
        joystickToggle = new ToggleButtonReader(gamepads.gamepad1, GamepadKeys.Button.X);
    }

    @Override
    public void loop() {
//        double joystickInput = targetTolerance(0, Constants.STICK_TOLERANCE, gamepads.gamepad2.getRightY());
//
//        if (joystickInput != 0 || !goingToPos) {
//            outputExtension.set(-joystickInput);
//            goingToPos = false;
//        }

        joystickToggle.readValue();
        if (joystickToggle.getState()) {
            outputExtension.setTargetPosition(1580);
        } else {
            outputExtension.setTargetPosition(0);
        }
    }
}
