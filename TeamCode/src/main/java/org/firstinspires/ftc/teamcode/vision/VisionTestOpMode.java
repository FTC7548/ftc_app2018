package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Locale;

@TeleOp(name="Front Camera Test", group="hehe xd")
public class VisionTestOpMode extends OpMode {

    private ObjDetectPipeline pipeline;

    @Override
    public void init() {
        pipeline = new ObjDetectPipeline();

        pipeline.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 1);
        pipeline.enable();

    }

    @Override
    public void loop() {
        List<MatOfPoint> contours = pipeline.getContours();
        if (contours.size() == 0) {
            telemetry.addData("average x/y", "NO POINTS FOUND");
            return;
        }

        double x_sum = 0;
        double y_sum = 0;
        for (int i = 0; i < contours.size(); i++) {
            Rect boundingRect = Imgproc.boundingRect(contours.get(i));

            double x = (boundingRect.x + boundingRect.width) / 2;
            x_sum += x;
            double y = (boundingRect.x + boundingRect.width) / 2;
            y_sum += y;
            double size = x*y;
        }
        x_sum /= contours.size();
        y_sum /= contours.size();

        telemetry.addData("average x/y", "pos: (%2s, %2s)", x_sum, y_sum);
    }

    public void stop() {
        pipeline.disable();
    }

}
