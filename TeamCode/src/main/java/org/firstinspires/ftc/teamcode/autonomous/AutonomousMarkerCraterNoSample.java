package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "for millilani")
public class AutonomousMarkerCraterNoSample extends AutonomousOpMode {

    @Override
    public void startOpMode() {
        unlatch();
        sleep(500);
        drivePID(1, 1, 5, 0);
        moveLift(0.5, 0);

        if (BLOCK_POS == -1) {
            turnPID(45, 0.8, 5);
            drivePID(0.8, 10, 5, 0);
            drivePID(0.8, -10, 5, 0);
        } else if (BLOCK_POS == 0) {
            drivePID(0.8, 3, 5, 0);
            drivePID(0.8, -3, 5, 0);
        } else {
            turnPID(-45, 0.7, 5);
            drivePID(0.8, 10, 5, 0);
            drivePID(0.8, -10, 5, 0);
        }

        turnPID(75, 0.8, 0);
        drivePID(1, 16, 5, 0);
        turnPID(135, 0.8, 0);
        drivePID(1, 15, 5, 0);
        r.extender.extendOut();
        sleep(300);
        r.extender.extendStore();
        turnNoPID(90, 0.8, 0);
        turnPID(315, 0.8, 5);
        drivePID(1, 16, 4, 0);








        /*
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
        sleep(10000); */
    }
}
