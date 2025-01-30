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
    public final ColorObjectPipeline pipeline;
    private final OpenCvWebcam webcam;
    private final Telemetry telemetry;

    @Inject
    public Camera(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new ColorObjectPipeline();
        webcam.setPipeline(pipeline);

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
    public void loop() {
        telemetry.addData("Yellow Object Detected", pipeline.isYellowDetected());
        telemetry.addData("Yellow Center X", pipeline.getYellowCenterX());
        telemetry.addData("Yellow Center Y", pipeline.getYellowCenterY());

        telemetry.addData("Blue Object Detected", pipeline.isBlueDetected());
        telemetry.addData("Blue Center X", pipeline.getBlueCenterX());
        telemetry.addData("Blue Center Y", pipeline.getBlueCenterY());

        telemetry.addData("Red Object Detected", pipeline.isRedDetected());
        telemetry.addData("Red Center X", pipeline.getRedCenterX());
        telemetry.addData("Red Center Y", pipeline.getRedCenterY());

        telemetry.update();
    }

    public class ColorObjectPipeline extends OpenCvPipeline {
        private final Scalar LOWER_YELLOW = new Scalar(20, 100, 100);
        private final Scalar UPPER_YELLOW = new Scalar(30, 255, 255);
        private final Scalar LOWER_BLUE = new Scalar(100, 150, 50);
        private final Scalar UPPER_BLUE = new Scalar(140, 255, 255);
        private final Scalar LOWER_RED = new Scalar(0, 120, 70);
        private final Scalar UPPER_RED = new Scalar(10, 255, 255);

        private Point yellowCenter = null;
        private boolean yellowDetected = false;
        private Point blueCenter = null;
        private boolean blueDetected = false;
        private Point redCenter = null;
        private boolean redDetected = false;

        @Override
        public Mat processFrame(Mat input) {
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            detectColor(hsv, LOWER_YELLOW, UPPER_YELLOW, input, "yellow");
            detectColor(hsv, LOWER_BLUE, UPPER_BLUE, input, "blue");
            detectColor(hsv, LOWER_RED, UPPER_RED, input, "red");

            hsv.release();
            return input;
        }

        private void detectColor(Mat hsv, Scalar lower, Scalar upper, Mat input, String color) {
            Mat mask = new Mat();
            Core.inRange(hsv, lower, upper, mask);

            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            double maxArea = 0;
            Rect largestRect = null;
            for (MatOfPoint contour : contours) {
                double area = Imgproc.contourArea(contour);
                if (area > maxArea) {
                    maxArea = area;
                    largestRect = Imgproc.boundingRect(contour);
                }
            }

            if (largestRect != null) {
                Imgproc.rectangle(input, largestRect, new Scalar(0, 255, 0), 2);
                Point center = new Point(largestRect.x + largestRect.width / 2.0, largestRect.y + largestRect.height / 2.0);

                switch (color) {
                    case "yellow": yellowCenter = center; yellowDetected = true; break;
                    case "blue": blueCenter = center; blueDetected = true; break;
                    case "red": redCenter = center; redDetected = true; break;
                }
            }

            mask.release();
            hierarchy.release();
        }

        public double getYellowCenterX() { return yellowCenter != null ? yellowCenter.x : -1; }
        public double getYellowCenterY() { return yellowCenter != null ? yellowCenter.y : -1; }
        public boolean isYellowDetected() { return yellowDetected; }

        public double getBlueCenterX() { return blueCenter != null ? blueCenter.x : -1; }
        public double getBlueCenterY() { return blueCenter != null ? blueCenter.y : -1; }
        public boolean isBlueDetected() { return blueDetected; }

        public double getRedCenterX() { return redCenter != null ? redCenter.x : -1; }
        public double getRedCenterY() { return redCenter != null ? redCenter.y : -1; }
        public boolean isRedDetected() { return redDetected; }
    }
}
