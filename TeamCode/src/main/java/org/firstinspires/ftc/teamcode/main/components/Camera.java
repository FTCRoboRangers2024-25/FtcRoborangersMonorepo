package org.firstinspires.ftc.teamcode.main.components;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.base.structure.Component;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Camera extends Component {
    public final YellowObjectPipeline pipeline;
    private final OpenCvWebcam webcam;
    private final Telemetry telemetry;

    @Inject
    public Camera(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new YellowObjectPipeline();
        webcam.setPipeline(pipeline);

        // Open the webcam asynchronously
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
            }
        });

        FtcDashboard.getInstance().startCameraStream(webcam, 0);
    }

    @Override
    public void init(boolean isAuto) {
    }

    @Override
    public void loop() {
        telemetry.addData("Center X", pipeline.getCenterX());
        telemetry.addData("Center Y", pipeline.getCenterY());
        telemetry.addData("Object Detected", pipeline.isObjectDetected());
        telemetry.update();
    }

    public class YellowObjectPipeline extends OpenCvPipeline {
        private final Scalar LOWER_YELLOW = new Scalar(20, 100, 100); // HSV lower bound for yellow
        private final Scalar UPPER_YELLOW = new Scalar(30, 255, 255); // HSV upper bound for yellow
        private Point center = null;
        private boolean objectDetected = false;

        @Override
        public Mat processFrame(Mat input) {
            // Convert to HSV color space
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            // Threshold the image to isolate yellow objects
            Mat mask = new Mat();
            Core.inRange(hsv, LOWER_YELLOW, UPPER_YELLOW, mask);

            // Find contours
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            // Find the largest contour
            double maxArea = 0;
            Rect largestRect = null;
            for (MatOfPoint contour : contours) {
                double area = Imgproc.contourArea(contour);
                if (area > maxArea) {
                    maxArea = area;
                    largestRect = Imgproc.boundingRect(contour);
                }
            }

            // Draw the rectangle and calculate the center if an object is found
            if (largestRect != null) {
                Imgproc.rectangle(input, largestRect, new Scalar(0, 255, 0), 2);
                center = new Point(
                        largestRect.x + largestRect.width / 2.0,
                        largestRect.y + largestRect.height / 2.0
                );
                objectDetected = true;
            } else {
                center = null;
                objectDetected = false;
            }

            // Release resources
            hsv.release();
            mask.release();
            hierarchy.release();

            return input; // Return the annotated frame
        }

        public double getCenterX() {
            return center != null ? center.x : -1;
        }

        public double getCenterY() {
            return center != null ? center.y : -1;
        }

        public boolean isObjectDetected() {
            return objectDetected;
        }
    }
}
