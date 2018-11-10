package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "drive test", group = "hey")
public class DriveTest extends AutonomousOpMode {
    public void startOpMode() {
        turnUntilHeading(90, 0.7, 1, 5);
        sleep(1000);
        turnUntilHeading(135, 0.7, 1, 5);
    }
}
