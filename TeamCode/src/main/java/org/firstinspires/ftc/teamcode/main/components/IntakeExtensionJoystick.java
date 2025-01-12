package org.firstinspires.ftc.teamcode.main.components;

import static org.firstinspires.ftc.teamcode.base.Functions.clamp01;
import static org.firstinspires.ftc.teamcode.base.Functions.targetTolerance;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntakeExtensionJoystick extends Component {
    private final IntakeExtension intakeExtension;
    private final Gamepads gamepads;
    private final double intakeExtensionSpeed = 1;

    private double currentExtension;
    private ElapsedTime time;

    @Inject
    public IntakeExtensionJoystick(Gamepads gamepads, IntakeExtension intakeExtension) {
        this.gamepads = gamepads;
        this.intakeExtension = intakeExtension;
    }

    @Override
    public void init(boolean isAuto) {
        time = new ElapsedTime();
    }

    @Override
    public void onStart(boolean isAuto) {
        time.reset();
    }

    @Override
    public void loop() {
        double joystickInput = targetTolerance(0, RobotConstants.STICK_TOLERANCE, gamepads.gamepad2.getLeftY());
        double scaledInput = joystickInput * intakeExtensionSpeed * time.seconds();
        currentExtension = clamp01(currentExtension + scaledInput);

        intakeExtension.extendIntake(currentExtension);

        time.reset();
    }
}
