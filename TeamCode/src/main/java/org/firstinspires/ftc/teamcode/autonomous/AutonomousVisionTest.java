package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "Vision Test", group="auto")
public class AutonomousVisionTest extends AutonomousOpMode {

    double[] counts = new double[3];
    double[] avg_sizes = new double[3];

    public void startOpMode() {

        setCameraPosition(CameraPosition.LEFT);
        sleep(3000);
        setCounts(1);
        sleep(500);
        setCameraPosition(CameraPosition.CENTER);
        sleep(3000);
        setCounts(0);
        sleep(500);
        setCameraPosition(CameraPosition.RIGHT);
        sleep(3000);
        setCounts(2);
        sleep(500);
        setCameraPosition(CameraPosition.DOWN);


        telemetry.clear();
        telemetry.clearAll();
        for (int i = 0; i < 3; i++) {
            telemetry.addData("POS" + i, "size: %s, count: %s", avg_sizes[i], counts[i]);
        }
        telemetry.update();
        sleep(15000);

    }

    public void setCounts(int index) {
        counts[index] = contourCount();
        avg_sizes[index] = avgContourSize();
    }
}
