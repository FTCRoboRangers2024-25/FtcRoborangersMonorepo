package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeClawJoystick extends Component {
    @Inject
    public IntakeClaw intakeClaw;

    @Inject
    public Gamepads gamepads;

    private boolean flag;

    @Inject
    public IntakeClawJoystick(IntakeClaw intakeClaw, Gamepads gamepads) {
        this.intakeClaw = intakeClaw;
        this.gamepads = gamepads;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {
//        if (gamepads.gamepad2.getButton(GamepadKeys.Button.Y)) {
//            intakeClaw.intakeInit();
//        } else if (gamepads.gamepad2.getButton(GamepadKeys.Button.B)) {
//            intakeClaw.intakePreGrab();
//        } else if (gamepads.gamepad2.getButton(GamepadKeys.Button.X)) {
//            intakeClaw.intakeGrab();
//        }
//
//        if (gamepads.gamepad2.isDown(GamepadKeys.Button.A) && !flag) {
//            intakeClaw.clawToggle();
//            flag = true;
//        } else if (!gamepads.gamepad2.isDown(GamepadKeys.Button.A) && flag) {
//            flag = false;
//        }
//
//        double rightJoystick = targetTolerance(0, Constants.STICK_TOLERANCE, gamepads.gamepad2.getLeftX());
//        intakeClaw.setYaw((-rightJoystick + 1) / 2);

        if (gamepads.gamepad1.isDown(GamepadKeys.Button.A)) {
            intakeClaw.clawOpen();
            intakeClaw.intakeGrab();
        }
    }
}
