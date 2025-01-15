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

    private final PIDController forwardController = new PIDController(0, 0, 0);
    private final PIDController strafeController = new PIDController(0, 0, 0);
    private final MecanumBase mecanumBase;
    private final Camera camera;

    private final double cameraXTarget = 160, cameraYTarget = 120;

    @Inject
    public MecanumCameraAligner(MecanumBase mecanumBase, Camera camera) {
        this.mecanumBase = mecanumBase;
        this.camera = camera;

        forwardController.setTolerance(20);
        strafeController.setTolerance(20);
    }

    @Override
    public void init(boolean isAuto) {

    }

    @Override
    public void loop() {
        // For FTC Dashboard
        forwardController.setPID(forwardPid.p, forwardPid.i, forwardPid.d);
        strafeController.setPID(strafePid.p, strafePid.i, strafePid.d);

        if (camera.pipeline.isObjectDetected()) {
            mecanumBase.robotCentric(
                    forwardController.calculate(camera.pipeline.getCenterX(), cameraXTarget),
                    strafeController.calculate(camera.pipeline.getCenterY(), cameraYTarget),
                    0
            );
        }
        else {
            mecanumBase.stop();
        }
    }
}
