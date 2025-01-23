package org.firstinspires.ftc.teamcode.base.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Servo Position Testing", group = "Base Tests")
public class ServoPositionTesting extends OpMode {
    private String[] ports = { "CHS0", "CHS1", "CHS2", "CHS3", "CHS4", "CHS5", "EHS0", "EHS1", "EHS2", "EHS3", "EHS4", "EHS5" };
    private int currentIndex = 0;
    private ServoEx target;
    private GamepadEx gamepad;
    private double currentSignal = 0.5;

    @Override
    public void init() {
        target = new SimpleServo(hardwareMap, ports[currentIndex], 0, 180);
        gamepad = new GamepadEx(gamepad2);
    }

    @Override
    public void loop() {
        target.setPosition(currentSignal);

        gamepad.readButtons();
        if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
            switchServo(-1);
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
            switchServo(1);
        }

        if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
            currentSignal += 0.1;
            if (currentSignal > 1) currentSignal = 1;
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
            currentSignal -= 0.1;
            if (currentSignal < 0) currentSignal = 0;
        }

        telemetry.addData("Current servo", ports[currentIndex]);
        telemetry.addData("Signal to servo", currentSignal);
        telemetry.update();
    }

    private void switchServo(int direction) {
        if (direction == 1) {
            currentIndex += 1;
            if (currentIndex >= ports.length) currentIndex = ports.length - 1;

            currentSignal = 0.5;
            target = new SimpleServo(hardwareMap, ports[currentIndex], 0, 180);
        } else if (direction == -1) {
            currentIndex -= 1;
            if (currentIndex < 0) currentIndex = 0;

            currentSignal = 0.5;
            target = new SimpleServo(hardwareMap, ports[currentIndex], 0, 180);
        }
    }
}
