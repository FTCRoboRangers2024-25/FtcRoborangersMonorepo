package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.Functions.targetTolerance;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MecanumJoystick extends Component {
    private final Gamepads gamepads;
    private final MecanumBase mecanumBase;

    @Inject
    public MecanumJoystick(Gamepads gamepads, MecanumBase mecanumBase) {
        this.gamepads = gamepads;
        this.mecanumBase = mecanumBase;
    }

    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
        double forwardSpeed = targetTolerance(0, RobotConstants.STICK_TOLERANCE, gamepads.gamepad1.getLeftY());
        double strafeSpeed = targetTolerance(0, RobotConstants.STICK_TOLERANCE, gamepads.gamepad1.getLeftX());
        double turnSpeed = targetTolerance(0, RobotConstants.STICK_TOLERANCE, gamepads.gamepad1.getRightX());

        mecanumBase.fieldCentric(forwardSpeed, strafeSpeed, turnSpeed);
    }
}
