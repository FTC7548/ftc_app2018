package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "drive test", group = "hey")
public class DriveTest extends AutonomousOpMode {
    public void startOpMode() {
        for (int i = 0; i < 3; i++) {
            turnUntilHeadingPID(90, 0.6, 1, 5);
            sleep(1000);
            turnUntilHeadingPID(0, 0.6, 1, 3);
            sleep(1000);
        }
    }
}
