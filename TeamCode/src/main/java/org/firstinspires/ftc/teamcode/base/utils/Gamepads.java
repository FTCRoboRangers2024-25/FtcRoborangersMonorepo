package org.firstinspires.ftc.teamcode.base.utils;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Gamepads {
    public GamepadEx gamepad1;
    public GamepadEx gamepad2;

    public Gamepads(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = new GamepadEx(gamepad1);
        this.gamepad2 = new GamepadEx(gamepad2);
    }
}
