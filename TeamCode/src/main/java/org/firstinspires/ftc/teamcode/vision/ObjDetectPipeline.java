package org.firstinspires.ftc.teamcode.vision;

import android.os.Bundle;

import org.corningrobotics.enderbots.endercv.OpenCVPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ObjDetectPipeline extends OpenCVPipeline {
    private Mat hsv = new Mat();
    private Mat thresholded = new Mat();
    private List<MatOfPoint> contours = new ArrayList<>();

    public synchronized List<MatOfPoint> getContours() {
        return contours;
    }

    @Override
    public Mat processFrame(Mat rgb, Mat gray) {
        Imgproc.cvtColor(rgb, hsv, Imgproc.COLOR_RGB2HSV, 3);
        Scalar YELLOW_MIN = new Scalar(10, 170, 60);
        Scalar YELLOW_MAX = new Scalar(60, 255, 255);
        Core.inRange(hsv, YELLOW_MIN, YELLOW_MAX, thresholded);
        Imgproc.blur(thresholded, thresholded, new Size(5, 5));
        contours = new ArrayList<>();
        Imgproc.findContours(thresholded, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(rgb, contours, -1, new Scalar(0, 255, 0), 2, 8);
        Imgproc.line(rgb, new Point(VisionConfig.X_THRESHOLD, 0), new Point(VisionConfig.X_THRESHOLD, rgb.height()), new Scalar(255, 0, 0), 2, 8);
        return rgb;
    }
}
