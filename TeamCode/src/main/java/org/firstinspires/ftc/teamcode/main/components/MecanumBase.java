package org.firstinspires.ftc.teamcode.main.components;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.RobotGyro;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

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

    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
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

    public void stop() {
        mecanum.setWeightedDrivePower(
                new Pose2d(0, 0, 0)
        );
    }

    @Override
    public void onStop() {
        stop();
    }
}

//@Singleton
//@Config
//public class MecanumBase extends Component {
//    private final MecanumDrive mecanum;
//
//    @Inject
//    public MecanumBase(HardwareMap hardwareMap,  Telemetry telemetry) {
//        Motor backLeft = new Motor(hardwareMap, RobotConstants.MECANUM_BACK_LEFT, Motor.GoBILDA.RPM_435);
//        Motor backRight = new Motor(hardwareMap, RobotConstants.MECANUM_BACK_RIGHT, Motor.GoBILDA.RPM_435);
//        Motor frontLeft = new Motor(hardwareMap, RobotConstants.MECANUM_FRONT_LEFT, Motor.GoBILDA.RPM_435);
//        Motor frontRight = new Motor(hardwareMap, RobotConstants.MECANUM_FRONT_RIGHT, Motor.GoBILDA.RPM_435);
//        MotorGroup motors = new MotorGroup(frontLeft, frontRight, backLeft, backRight);
//        motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//
//        // DO NOT INVERT ANY MOTORS FROM BASE HERE
//
//        mecanum = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
//    }
//
//    @Override
//    public void init(boolean isAuto) {
//
//    }
//
//    @Override
//    public void loop() {
//    }
//
//    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed) {
//        robotCentric(-forwardSpeed, -strafeSpeed, -turnSpeed, false);
//    }
//
//    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed, boolean squareInput) {
//        mecanum.driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed, squareInput);
//    }
//
//    public void stop() {
//        mecanum.stop();
//    }
//
//    @Override
//    public void onStop() {
//        stop();
//    }
//}
