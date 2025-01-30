package org.firstinspires.ftc.teamcode.main.components;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Config
public class MecanumBase extends Component {
    private final SampleMecanumDrive mecanum;

    @Inject
    public MecanumBase(SampleMecanumDrive sampleMecanumDrive) {
        this.mecanum = sampleMecanumDrive;
    }

    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed) {
        mecanum.setWeightedDrivePower(
                new Pose2d(
                        forwardSpeed,
                        -strafeSpeed,
                        -turnSpeed
                )
        );
    }

    public void stopBase() {
        mecanum.setWeightedDrivePower(
                new Pose2d(0, 0, 0)
        );
    }

    @Override
    public void stop() {
        stopBase();
    }
}