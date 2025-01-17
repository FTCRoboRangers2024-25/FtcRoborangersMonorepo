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

    private final double cameraXTarget = 160, cameraYTarget = 140;

    @Inject
    public MecanumCameraAligner(MecanumBase mecanumBase, Camera camera) {
        this.mecanumBase = mecanumBase;
        this.camera = camera;

        forwardController.setTolerance(22);
        strafeController.setTolerance(22);
    }

    @Override
    public void init(boolean isAuto) {
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

    public boolean align() {
        camera.loop();
        forwardController.setSetPoint(cameraYTarget);
        strafeController.setSetPoint(cameraXTarget);

        forwardController.calculate(camera.pipeline.getCenterY());
        strafeController.calculate(camera.pipeline.getCenterX());
        if (!forwardController.atSetPoint() || !strafeController.atSetPoint()) {
            if (camera.pipeline.isObjectDetected()) {
                mecanumBase.robotCentric(
                        forwardController.calculate(camera.pipeline.getCenterY()),
                        -strafeController.calculate(camera.pipeline.getCenterX()),
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
}
