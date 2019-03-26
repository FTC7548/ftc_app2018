package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Robot;

@Autonomous(name = "for millilani")
public class AutonomousMarkerCraterNoSample extends AutonomousOpMode {

    @Override
    public void startOpMode() {
        r.lift.unlock();
        r.extender.extendIn();
        sleep(100);
        r.lift.setPwr(1);
        sleep(1300);
        r.lift.setPwr(0);
        drivePID(8, 0.4, 3);
        sleep(100);
        r.lift.setPwr(-1);
        r.lift.back();
        sleep(700);
        r.lift.setPwr(0);
        sleep(100);
        r.extender.extendOut();
        r.extender.pivotDown();
        sleep(10000);
    }
}
