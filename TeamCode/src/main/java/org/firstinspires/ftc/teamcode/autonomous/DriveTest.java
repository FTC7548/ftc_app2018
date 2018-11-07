package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "drive test", group = "hey")
public class DriveTest extends AutonomousOpMode {
    public void startOpMode() {
        driveNew(18, 0.4, 5);
        encTurn(10, -10, 0.4, 3);
        encTurn(10, 0, 0.4, 3);
        encTurn(0, 10, 0.4, 3);
    }
}
