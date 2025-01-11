package org.firstinspires.ftc.teamcode.main.components;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import javax.inject.Inject;

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

        mecanum = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
    }

    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed) {
        robotCentric(forwardSpeed, strafeSpeed, turnSpeed, false);
    }

    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed, boolean squareInput) {
        mecanum.driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed, squareInput);
    }

    public void stop() {
        mecanum.stop();
    }
}
