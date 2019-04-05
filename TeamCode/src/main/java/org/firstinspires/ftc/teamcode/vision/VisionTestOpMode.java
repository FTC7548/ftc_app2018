package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        ArrayList<Rect> inBounds = new ArrayList<Rect>();
        for (int i = 0; i < contours.size(); i++) {
            Rect boundingRect = Imgproc.boundingRect(contours.get(i));
            double x = boundingRect.x + boundingRect.width / 2;
            //double y = boundingRect.y + boundingRect.height / 2;

            if (x > VisionConfig.X_THRESHOLD) {
                inBounds.add(boundingRect);
                //telemetry.addData("CONTOUR", "(%s, %s) %s", boundingRect.x + boundingRect.width / 2,
                        //boundingRect.y + boundingRect.height / 2, boundingRect.area());
            }

        }

        Collections.sort(inBounds, new SortContourBySize());
        for (int i = 0; i < inBounds.size(); i++) {
            telemetry.addData("CONTOUR", "(%s, %s) %s", inBounds.get(i).x + inBounds.get(i).width / 2,
                    inBounds.get(i).y + inBounds.get(i).height / 2, inBounds.get(i).area());
        }

        if (inBounds.size() > 0) {
            Rect biggest = inBounds.get(0);
            double y = biggest.y + biggest.height / 2;
            if (y < 200) {
                telemetry.addData("BEST GUESS", "RIGHT");
            } else if (y < 400) {
                telemetry.addData("BEST GUESS", "CENTER");
            } else {
                telemetry.addData("BEST GUESS", "LEFT");
            }
        } else {
            telemetry.addData("NO CONTOURS FOUND IN RANGE", "");
        }


        telemetry.update();
    }

    public void stop() {
        pipeline.disable();
    }

}

class SortContourBySize implements Comparator<Rect> {
    public int compare(Rect a, Rect b) {
        return (int)Math.round(Math.ceil(b.area() - a.area()));
    }
}
