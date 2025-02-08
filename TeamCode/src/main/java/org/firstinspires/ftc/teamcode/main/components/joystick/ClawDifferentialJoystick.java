package org.firstinspires.ftc.teamcode.main.components.joystick;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.targetTolerance;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.components.ClawDifferential;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClawDifferentialJoystick extends Component {
    private final Gamepads gamepads;
    private final ClawDifferential clawDifferential;
    private final Telemetry telemetry;

    @Inject
    public ClawDifferentialJoystick(Gamepads gamepads, ClawDifferential clawDifferential, Telemetry telemetry) {
        this.gamepads = gamepads;
        this.clawDifferential = clawDifferential;
        this.telemetry = telemetry;
    }

    @Override
    public void loop() {
        double joystickValueYaw = targetTolerance(0, 0.1, gamepads.gamepad2.getLeftX());
        double yawAngle = joystickValueYaw * 180;

        double joystickValuePitch = targetTolerance(0, 0.1, -gamepads.gamepad2.getRightY());
        double pitchAngle = joystickValuePitch * 90;

        clawDifferential.setYaw(yawAngle);
        clawDifferential.setPitch(pitchAngle);

        telemetry.addData("Joystick yaw value", joystickValueYaw);
        telemetry.addData("Joystick pitch value", joystickValuePitch);
        telemetry.addData("Yaw angle", yawAngle);
        telemetry.addData("Pitch angle", pitchAngle);
    }
}
