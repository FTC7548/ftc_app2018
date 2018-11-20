package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "drive test", group = "hey")
public class DriveTest extends AutonomousOpMode {
    public void startOpMode() {
        for (int i = 0; i < 3; i++) {
            drivePID(-10, .8, 7);
            sleep(500);
            drivePID(10, .8, 7);
            sleep(500);
        }
    }
}
