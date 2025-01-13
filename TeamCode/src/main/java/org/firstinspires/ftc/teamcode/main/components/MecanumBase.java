package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MecanumBase extends Component {
    private final HardwareMap hardwareMap;
    private MecanumDrive mecanum;

    @Inject
    public MecanumBase(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    @Override
    public void init(boolean isAuto) {
        Motor backLeft = new Motor(hardwareMap, RobotConstants.MECANUM_BACK_LEFT, Motor.GoBILDA.RPM_435);
        Motor backRight = new Motor(hardwareMap, RobotConstants.MECANUM_BACK_RIGHT, Motor.GoBILDA.RPM_435);
        Motor frontLeft = new Motor(hardwareMap, RobotConstants.MECANUM_FRONT_LEFT, Motor.GoBILDA.RPM_435);
        Motor frontRight = new Motor(hardwareMap, RobotConstants.MECANUM_FRONT_RIGHT, Motor.GoBILDA.RPM_435);
        MotorGroup motors = new MotorGroup(frontLeft, frontRight, backLeft, backRight);
        motors.setInverted(true);
        motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        mecanum = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
    }

    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed) {
        robotCentric(forwardSpeed, strafeSpeed, turnSpeed, false);
    }

    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed, boolean squareInput) {
        mecanum.driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed, squareInput);
    }

    public void fieldCentric(double forwardSpeed, double strafeSpeed, double turnSpeed, double gyroAngle, boolean squareInput) {
        mecanum.driveFieldCentric(strafeSpeed, forwardSpeed, turnSpeed, gyroAngle, squareInput);
    }

    public void stop() {
        mecanum.stop();
    }

    @Override
    public void onStop() {
        stop();
    }
}
