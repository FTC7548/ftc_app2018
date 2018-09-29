package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Locale;

@TeleOp(name="Block detector", group="hehe xd")
public class VisionTestOpMode extends OpMode {

    private ObjDetectPipeline pipeline;

    @Override
    public void init() {
        pipeline = new ObjDetectPipeline();
        pipeline.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        pipeline.enable();

    }

    @Override
    public void loop() {
        List<MatOfPoint> contours = pipeline.getContours();
        for (int i = 0; i < contours.size(); i++) {
            Rect boundingRect = Imgproc.boundingRect(contours.get(i));
            telemetry.addData("contour" + Integer.toString(i),
                    String.format(Locale.getDefault(), "(%d, %d)",
                            (boundingRect.x + boundingRect.width) / 2,
                            (boundingRect.y + boundingRect.height) / 2));
        }
    }

    public void stop() {
        pipeline.disable();
    }

}
