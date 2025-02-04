package org.firstinspires.ftc.teamcode.main.components;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.base.structure.Component;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Config
public class MecanumCameraAligner extends Component {
    public static PIDCoefficients forwardPid = new PIDCoefficients();
    public static PIDCoefficients strafePid = new PIDCoefficients();

    private final PIDController forwardController = new PIDController(0.002, 0.04, 0);
    private final PIDController strafeController = new PIDController(0.002, 0.04, 0);
    private final MecanumBase mecanumBase;
    private final Camera camera;

    private final double cameraXTarget = 160, cameraYTarget = 130;

    @Inject
    public MecanumCameraAligner(MecanumBase mecanumBase, Camera camera) {
        this.mecanumBase = mecanumBase;
        this.camera = camera;

        forwardController.setTolerance(0);
        strafeController.setTolerance(0);
    }

    @Override
    public void init() {
        // For FTC Dashboard
//        forwardController.setPID(forwardPid.p, forwardPid.i, forwardPid.d);
//        strafeController.setPID(strafePid.p, strafePid.i, strafePid.d);
    }

    @Override
    public void loop() {
        // For FTC Dashboard
//        forwardController.setPID(forwardPid.p, forwardPid.i, forwardPid.d);
//        strafeController.setPID(strafePid.p, strafePid.i, strafePid.d);
    }

    public boolean alignYellow() {
        camera.loop();
        forwardController.setSetPoint(cameraYTarget);
        strafeController.setSetPoint(cameraXTarget);

        forwardController.calculate(camera.pipeline.getYellowCenterY());
        strafeController.calculate(camera.pipeline.getYellowCenterX());
        if (!forwardController.atSetPoint() || !strafeController.atSetPoint()) {
            if (camera.pipeline.isYellowDetected()) {
                mecanumBase.robotCentric(
                        forwardController.calculate(camera.pipeline.getYellowCenterY()),
                        -strafeController.calculate(camera.pipeline.getYellowCenterX()),
                        0
                );
            }
            else {
                mecanumBase.stop();
            }
            return false;
        } else {
            mecanumBase.stop();
            return true;
        }
    }

    public boolean alignRed() {
        camera.loop();
        forwardController.setSetPoint(cameraYTarget);
        strafeController.setSetPoint(cameraXTarget);

        forwardController.calculate(camera.pipeline.getRedCenterY());
        strafeController.calculate(camera.pipeline.getRedCenterX());
        if (!forwardController.atSetPoint() || !strafeController.atSetPoint()) {
            if (camera.pipeline.isRedDetected()) {
                mecanumBase.robotCentric(
                        forwardController.calculate(camera.pipeline.getRedCenterY()),
                        -strafeController.calculate(camera.pipeline.getRedCenterX()),
                        0
                );
            }
            else {
                mecanumBase.stop();
            }
            return false;
        } else {
            mecanumBase.stop();
            return true;
        }
    }

    public boolean alignBlue() {
        camera.loop();
        forwardController.setSetPoint(cameraYTarget);
        strafeController.setSetPoint(cameraXTarget);

        forwardController.calculate(camera.pipeline.getBlueCenterY());
        strafeController.calculate(camera.pipeline.getBlueCenterX());
        if (!forwardController.atSetPoint() || !strafeController.atSetPoint()) {
            if (camera.pipeline.isBlueDetected()) {
                mecanumBase.robotCentric(
                        forwardController.calculate(camera.pipeline.getBlueCenterY()),
                        -strafeController.calculate(camera.pipeline.getBlueCenterX()),
                        0
                );
            }
            else {
                mecanumBase.stop();
            }
            return false;
        } else {
            mecanumBase.stop();
            return true;
        }
    }

    public void stopAligning() {
        mecanumBase.robotCentric(0, 0, 0);
    }
}
