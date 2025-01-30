package org.firstinspires.ftc.teamcode.main.components;


import static org.firstinspires.ftc.teamcode.base.utils.Functions.clamp01;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeExtensionJoystick extends Component {
    private final IntakeExtension intakeExtension;
    private final Gamepads gamepads;
    private final ToggleButtonReader joystickToggle;

    private double currentExtension;

    private boolean flag;

    @Inject
    public IntakeExtensionJoystick(Gamepads gamepads, IntakeExtension intakeExtension) {
        this.gamepads = gamepads;
        this.intakeExtension = intakeExtension;
        joystickToggle = new ToggleButtonReader(gamepads.gamepad1, GamepadKeys.Button.B);
    }
    @Override
    public void loop() {
//        if (gamepads.gamepad2.isDown(GamepadKeys.Button.DPAD_UP) && !flag) {
//            currentExtension = 1;
//            flag = true;
//        } else if (gamepads.gamepad2.isDown(GamepadKeys.Button.LEFT_BUMPER) && !flag) {
//            currentExtension -= 0.1;
//            flag = true;
//        } else if (gamepads.gamepad2.isDown(GamepadKeys.Button.DPAD_DOWN) && !flag) {
//            currentExtension = 0;
//            flag = true;
//        } else if (gamepads.gamepad2.isDown(GamepadKeys.Button.RIGHT_BUMPER) && !flag) {
//            currentExtension += 0.1;
//            flag = true;
//        } else if (!gamepads.gamepad2.isDown(GamepadKeys.Button.DPAD_UP) &&
//                !gamepads.gamepad2.isDown(GamepadKeys.Button.DPAD_DOWN) &&
//                !gamepads.gamepad2.isDown(GamepadKeys.Button.LEFT_BUMPER) &&
//                !gamepads.gamepad2.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
//            flag = false;
//        }
//
//        currentExtension = clamp01(currentExtension);

        joystickToggle.readValue();
        if (joystickToggle.getState()) {
            currentExtension = 1;
        } else {
            currentExtension = 0;
        }

        intakeExtension.extendIntake(currentExtension);
    }

    public void resetIntakeJoystick() {
        currentExtension = 0;
    }
}
