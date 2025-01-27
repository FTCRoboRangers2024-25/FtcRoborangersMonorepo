package org.firstinspires.ftc.teamcode.base.utils;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.INCH;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PinPointLocalizer implements Localizer {
    private final GoBildaPinpointDriver pinpointDriver;
    private final Telemetry telemetry;

    @Inject
    public PinPointLocalizer(HardwareMap hardwareMap, Telemetry telemetry) {
        pinpointDriver = hardwareMap.get(GoBildaPinpointDriver.class, RobotPorts.ODO);
        this.telemetry = telemetry;

        pinpointDriver.setOffsets(0, 0);
        pinpointDriver.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpointDriver.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpointDriver.resetPosAndIMU();
    }

    @NonNull
    @Override
    public Pose2d getPoseEstimate() {
        Pose2D odoPose = pinpointDriver.getPosition();
        return new Pose2d(odoPose.getX(INCH), odoPose.getY(INCH), odoPose.getHeading(AngleUnit.RADIANS));
    }

    @Override
    public void setPoseEstimate(@NonNull Pose2d pose2d) {
        pinpointDriver.setPosition(new Pose2D(INCH, pose2d.getX(), pose2d.getY(), AngleUnit.RADIANS, pose2d.getHeading()));
    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        Pose2D odoVelocity = pinpointDriver.getVelocity();
        return new Pose2d(odoVelocity.getX(INCH), odoVelocity.getY(INCH), odoVelocity.getHeading(AngleUnit.RADIANS));
    }

    @Override
    public void update() {
        Pose2d pos = getPoseEstimate();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(), pos.getY(), pos.getHeading());
        telemetry.addData("Position", data);

        Pose2d vel = getPoseVelocity();
        String velocity = String.format(Locale.US,"{XVel: %.3f, YVel: %.3f, HVel: %.3f}", vel.getX(), vel.getY(), vel.getHeading());
        telemetry.addData("Velocity", velocity);

        telemetry.addData("Status", pinpointDriver.getDeviceStatus());

        telemetry.addData("Pinpoint Frequency", pinpointDriver.getFrequency());
    }
}
