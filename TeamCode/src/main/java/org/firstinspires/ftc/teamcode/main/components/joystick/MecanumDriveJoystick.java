package org.firstinspires.ftc.teamcode.main.components.joystick;

import static org.firstinspires.ftc.teamcode.base.utils.Functions.targetTolerance;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MecanumDriveJoystick extends Component {
    private final SampleMecanumDrive sampleMecanumDrive;
    private final Gamepads gamepads;

    @Inject
    public MecanumDriveJoystick(SampleMecanumDrive sampleMecanumDrive, Gamepads gamepads) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.gamepads = gamepads;
    }

    @Override
    public void loop() {
        double forward = targetTolerance(0, 0.1, gamepads.gamepad1.getLeftY());
        double strafe = targetTolerance(0, 0.1, gamepads.gamepad1.getLeftX());
        double turn = targetTolerance(0, 0.1, gamepads.gamepad1.getRightX());

        sampleMecanumDrive.setWeightedDrivePower(
                new Pose2d(
                        forward, strafe, turn
                )
        );
    }
}
