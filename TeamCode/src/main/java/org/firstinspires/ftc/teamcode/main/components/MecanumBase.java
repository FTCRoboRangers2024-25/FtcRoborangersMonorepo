package org.firstinspires.ftc.teamcode.main.components;

import android.util.TimeUtils;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.RobotGyro;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.main.RobotConstants;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Config
public class MecanumBase extends Component {
    public static PIDCoefficients turnPid = new PIDCoefficients();

    private final PIDController turnController = new PIDController(0, 0, 0);
    private final MecanumDrive mecanum;
    private final Telemetry telemetry;
    private final RobotGyro robotGyro;
    private final ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    private double currentHeading;

    @Inject
    public MecanumBase(HardwareMap hardwareMap, RobotGyro robotGyro, Telemetry telemetry) {
        Motor backLeft = new Motor(hardwareMap, RobotConstants.MECANUM_BACK_LEFT, Motor.GoBILDA.RPM_435);
        Motor backRight = new Motor(hardwareMap, RobotConstants.MECANUM_BACK_RIGHT, Motor.GoBILDA.RPM_435);
        Motor frontLeft = new Motor(hardwareMap, RobotConstants.MECANUM_FRONT_LEFT, Motor.GoBILDA.RPM_435);
        Motor frontRight = new Motor(hardwareMap, RobotConstants.MECANUM_FRONT_RIGHT, Motor.GoBILDA.RPM_435);
        MotorGroup motors = new MotorGroup(frontLeft, frontRight, backLeft, backRight);
        motors.setInverted(true);
        motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        mecanum = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

        this.robotGyro = robotGyro;
        this.telemetry = telemetry;
    }

    @Override
    public void init(boolean isAuto) {
        time.reset();
    }

    @Override
    public void loop() {
        // For FTC Dashboard
        turnController.setPID(turnPid.p, turnPid.i, turnPid.d);

        telemetry.addData("Heading mecanum", robotGyro.getRelativeHeading());
        telemetry.addData("Current heading mecanum", currentHeading);
    }

    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed) {
        robotCentric(forwardSpeed, strafeSpeed, turnSpeed, false);
    }

    public void robotCentric(double forwardSpeed, double strafeSpeed, double turnSpeed, boolean squareInput) {
        mecanum.driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed, squareInput);
    }

    private double lastTurningTime;

    public void fieldCentric(double forwardSpeed, double strafeSpeed, double turnSpeed) {
        double angle = Math.toRadians(robotGyro.getRelativeHeading());
        double localForward = Math.cos(angle) * forwardSpeed - Math.sin(angle) * strafeSpeed;
        double localStrafe = Math.sin(angle) * forwardSpeed + Math.cos(angle) * strafeSpeed;

        if (turnSpeed == 0 && time.time() - lastTurningTime > 500) {
            robotCentric(localForward, localStrafe, turnController.calculate(getDegreesToTarget(currentHeading, robotGyro.getRelativeHeading())));
        } else if (turnSpeed != 0) {
            robotCentric(localForward, localStrafe, turnSpeed);
            lastTurningTime = time.time();
            currentHeading = robotGyro.getRelativeHeading();
        } else {
            robotCentric(localForward, localStrafe, 0);
            currentHeading = robotGyro.getRelativeHeading();
        }
    }

    private double getDegreesToTarget(double target, double current) {
        double difference = target - current;

        if (Math.abs(difference) <= 180) {
            return difference;
        } else {
            return (360 - Math.abs(difference)) * -Math.signum(difference);
        }
    }

    public void stop() {
        mecanum.stop();
    }

    @Override
    public void onStop() {
        stop();
    }
}
