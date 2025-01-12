package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.structure.Component;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ControllerTelemetry extends Component {
    private final Gamepads gamepads;
    private final Telemetry telemetry;

    @Inject
    public ControllerTelemetry(Gamepads gamepads, Telemetry telemetry) {
        this.gamepads = gamepads;
        this.telemetry = telemetry;
    }

    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
        outputGamepadInputs(gamepads.gamepad1, "Driver Gamepad");
        outputGamepadInputs(gamepads.gamepad2, "Mechanism Gamepad");
    }

    public void outputGamepadInputs(GamepadEx gamepadEx, String gamepadName) {
        // Iterate through joystick axes
        telemetry.addData(gamepadName + " Left Stick X", gamepadEx.getLeftX());
        telemetry.addData(gamepadName + " Left Stick Y", gamepadEx.getLeftY());
        telemetry.addData(gamepadName + " Right Stick X", gamepadEx.getRightX());
        telemetry.addData(gamepadName + " Right Stick Y", gamepadEx.getRightY());

        // Iterate through button states
        telemetry.addData(gamepadName + " A", gamepadEx.getGamepadButton(GamepadKeys.Button.A));
        telemetry.addData(gamepadName + " B", gamepadEx.getGamepadButton(GamepadKeys.Button.B));
        telemetry.addData(gamepadName + " X", gamepadEx.getGamepadButton(GamepadKeys.Button.X));
        telemetry.addData(gamepadName + " Y", gamepadEx.getGamepadButton(GamepadKeys.Button.Y));
        telemetry.addData(gamepadName + " Left Bumper", gamepadEx.getButton(GamepadKeys.Button.LEFT_BUMPER));
        telemetry.addData(gamepadName + " Right Bumper", gamepadEx.getButton(GamepadKeys.Button.RIGHT_BUMPER));
        telemetry.addData(gamepadName + " Start", gamepadEx.getButton(GamepadKeys.Button.START));
        telemetry.addData(gamepadName + " Back", gamepadEx.getButton(GamepadKeys.Button.BACK));
    }
}
