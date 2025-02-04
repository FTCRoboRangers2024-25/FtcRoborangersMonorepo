package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.targetTolerance;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeClawJoystick extends Component {
    @Inject
    public IntakeClaw intakeClaw;

    @Inject
    public Gamepads gamepads;

    private boolean flag;
    private double clawYaw;

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
            intakeClaw.intakeGrab();
        }

        if (gamepads.gamepad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5 ||
        gamepads.gamepad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5) {
            double rightJoystick = targetTolerance(0, Constants.STICK_TOLERANCE, gamepads.gamepad1.getLeftX());
            intakeClaw.setYaw((-rightJoystick + 1) / 2);
        } else {
            if (gamepads.gamepad1.isDown(GamepadKeys.Button.DPAD_UP) && !flag) {
                flag = true;
                clawYaw -= 0.25;
                if (clawYaw > 1) {
                    clawYaw = 1;
                }
            } else if (gamepads.gamepad1.isDown(GamepadKeys.Button.DPAD_DOWN) && !flag) {
                flag = true;
                clawYaw += 0.25;
                if (clawYaw < 0) {
                    clawYaw = 0;
                }
            } else if (!gamepads.gamepad1.isDown(GamepadKeys.Button.DPAD_UP) &&
                        !gamepads.gamepad1.isDown(GamepadKeys.Button.DPAD_DOWN)) {
                flag = false;
            }

            if (intakeClaw.clawJoystickPosChangedFlag) {
                intakeClaw.clawJoystickPosChangedFlag = false;
                clawYaw = intakeClaw.clawJoystickTargetYawSet;
            }

            intakeClaw.setYaw(clawYaw, true);
        }
    }
}
