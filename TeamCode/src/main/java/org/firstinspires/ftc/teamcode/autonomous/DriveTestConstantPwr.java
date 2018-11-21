package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="drive test constant")
public class DriveTestConstantPwr extends AutonomousOpMode {

    @Override
    public void startOpMode() {
        for (int i = 0; i < 3; i++) {
            drive(-10, .4, 7);
            sleep(500);
            drive(10, .4, 7);
            sleep(500);
        }
    }

}
