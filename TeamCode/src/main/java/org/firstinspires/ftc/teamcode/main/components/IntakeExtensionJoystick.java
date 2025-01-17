package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.Functions.clamp01;
import static org.firstinspires.ftc.teamcode.base.Functions.targetTolerance;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeExtensionJoystick extends Component {
    private final IntakeExtension intakeExtension;
    private final Gamepads gamepads;

    private double currentExtension;

    private boolean flag;

    @Inject
    public IntakeExtensionJoystick(Gamepads gamepads, IntakeExtension intakeExtension) {
        this.gamepads = gamepads;
        this.intakeExtension = intakeExtension;
    }

    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
        if (gamepads.gamepad2.isDown(GamepadKeys.Button.DPAD_UP) && !flag) {
            currentExtension = 1;
            flag = true;
        } else if (gamepads.gamepad2.isDown(GamepadKeys.Button.LEFT_BUMPER) && !flag) {
            currentExtension -= 0.1;
            flag = true;
        } else if (gamepads.gamepad2.isDown(GamepadKeys.Button.DPAD_DOWN) && !flag) {
            currentExtension = 0;
            flag = true;
        } else if (gamepads.gamepad2.isDown(GamepadKeys.Button.RIGHT_BUMPER) && !flag) {
            currentExtension += 0.1;
            flag = true;
        } else if (!gamepads.gamepad2.isDown(GamepadKeys.Button.DPAD_UP) &&
                !gamepads.gamepad2.isDown(GamepadKeys.Button.DPAD_DOWN) &&
                !gamepads.gamepad2.isDown(GamepadKeys.Button.LEFT_BUMPER) &&
                !gamepads.gamepad2.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
            flag = false;
        }

        currentExtension = clamp01(currentExtension);

        intakeExtension.extendIntake(currentExtension);
    }
}
