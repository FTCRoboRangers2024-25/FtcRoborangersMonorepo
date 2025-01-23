package org.firstinspires.ftc.teamcode.base.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Motor Direction Testing", group = "Base Tests")
public class MotorDirectionTesting extends OpMode {
    private String[] ports = { "CHM0", "CHM1", "CHM2", "CHM3", "EHM0", "EHM1", "EHM2", "EHM3" };
    private int currentIndex = 0;
    private Motor target;
    private GamepadEx gamepad;

    @Override
    public void init() {
        target = new Motor(hardwareMap, ports[currentIndex]);
        gamepad = new GamepadEx(gamepad2);
    }

    @Override
    public void loop() {
        target.set(-gamepad.getRightY());

        gamepad.readButtons();
        if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
            switchMotor(-1);
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
            switchMotor(1);
        }

        telemetry.addData("Current motor", ports[currentIndex]);
        telemetry.addData("Output to motor", -gamepad.getRightY());
        telemetry.update();
    }

    private void switchMotor(int direction) {
        if (direction == 1) {
            currentIndex += 1;
            if (currentIndex >= ports.length) currentIndex = ports.length - 1;

            target.set(0);
            target = new Motor(hardwareMap, ports[currentIndex]);
        } else if (direction == -1) {
            currentIndex -= 1;
            if (currentIndex < 0) currentIndex = 0;

            target.set(0);
            target = new Motor(hardwareMap, ports[currentIndex]);
        }
    }
}
