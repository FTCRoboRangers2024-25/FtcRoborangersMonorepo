package org.firstinspires.ftc.teamcode.main.components;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.structure.Component;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Camera extends Component {
    private final Limelight3A limelight;
    private final Telemetry telemetry;

    private final double groundHeight = 10, sampleHeight = 3, cameraAngle = 50;

    @Inject
    public Camera(HardwareMap hardwareMap, Telemetry telemetry) {
        limelight = hardwareMap.get(Limelight3A.class, "Webcam1");
        this.telemetry = telemetry;
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(0);
    }

    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            double[] distances = getDistances(tx, ty);

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);

            telemetry.addData("Distance X", distances[0]);
            telemetry.addData("Distance Y", distances[1]);
        } else {
            telemetry.addData("Limelight", "No Targets");
        }
    }

    private double[] getDistances(double tx, double ty) {
        double yDist = (groundHeight - sampleHeight) * Math.tan(Math.toRadians(cameraAngle + ty));

        double angleRad = Math.toRadians(tx);
        double xNew = -yDist * Math.sin(angleRad);
        double yNew = yDist * Math.cos(angleRad);

        return new double[]{xNew, yNew};
    }
}
