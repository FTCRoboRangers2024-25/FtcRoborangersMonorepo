package org.firstinspires.ftc.teamcode.main.components.joystick;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.targetTolerance;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtension;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeExtensionJoystick extends Component {
    private final IntakeExtension intakeExtension;
    private final Gamepads gamepads;
    private final Telemetry telemetry;

    private boolean goingToPosition;

    @Inject
    public IntakeExtensionJoystick(IntakeExtension intakeExtension, Gamepads gamepads, Telemetry telemetry) {
        this.intakeExtension = intakeExtension;
        this.gamepads = gamepads;
        this.telemetry = telemetry;
    }

    @Override
    public void loop() {
        double input = targetTolerance(0, 0.1, -gamepads.gamepad2.getRightY());

        if (input != 0) {
            goingToPosition = false;
            intakeExtension.set(input);
        }
        else if (!goingToPosition) {
            intakeExtension.set(0);
        }

        if (gamepads.gamepad2.isDown(GamepadKeys.Button.X)) {
            goingToPosition = true;
            intakeExtension.goToPosition(500);
        }

        telemetry.addData("Intake extension input", input);
        telemetry.addData("Intake extension joystick going to position", goingToPosition);
    }
}
