package org.firstinspires.ftc.teamcode.main.components;


import static org.firstinspires.ftc.teamcode.base.utils.Functions.targetTolerance;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MecanumJoystickRed extends Component {
    private final Gamepads gamepads;
    private final MecanumBase mecanumBase;
    private final MecanumCameraAligner mecanumCameraAligner;

    @Inject
    public MecanumJoystickRed(Gamepads gamepads, MecanumBase mecanumBase, MecanumCameraAligner mecanumCameraAligner) {
        this.gamepads = gamepads;
        this.mecanumBase = mecanumBase;
        this.mecanumCameraAligner = mecanumCameraAligner;
    }

    @Override
    public void loop() {
        double forwardSpeed = targetTolerance(0, Constants.STICK_TOLERANCE, gamepads.gamepad1.getLeftY());
        double strafeSpeed = targetTolerance(0, Constants.STICK_TOLERANCE, gamepads.gamepad1.getLeftX());
        double turnSpeed = targetTolerance(0, Constants.STICK_TOLERANCE, gamepads.gamepad1.getRightX());

        double multiplier = 1, turn = 1;
        if (gamepads.gamepad1.isDown(GamepadKeys.Button.LEFT_BUMPER)) {
            multiplier = 0.5;
            turn = 0.4;
        }

        if (gamepads.gamepad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5) {
            mecanumCameraAligner.alignYellow();
        } else if (gamepads.gamepad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5) {
            mecanumCameraAligner.alignRed();
        } else {
            mecanumBase.robotCentric(forwardSpeed * multiplier, strafeSpeed * multiplier, turnSpeed * turn);
        }
    }
}
