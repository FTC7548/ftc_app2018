package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Robot;

@Autonomous(name="Default", group="lol")
public class DefaultAutonomous extends AutonomousOpMode {

    public void startOpMode() {


        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        r.setLiftPwr(-.25);
        sleep(100);
        r.setLiftPwr(0.5);
        sleep(1000);
        r.setLiftPwr(0);
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);

        setCameraPosition(CameraPosition.LEFT);
        drive(4.5, 0.3, 3);
        cameraLook();
        int goldPos = bestGoldGuess();
        setCameraPosition(CameraPosition.DOWN);
        if(goldPos == 0) { // left

            drive(-1.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(42, 0.6, 1, 3);
            sleep(500);
            drive(8 , 0.4, 3);
            sleep(500);
            drive(-8, 0.4, 3);
            sleep(500);
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);
            drive(17.5, 0.5, 3);
            sleep(500);
            turnUntilHeading(132, 0.8, 1, 3);
            sleep(500);
            drive(18, 0.5, 3);
            sleep(500);
            turnUntilHeading(222, 0.8, 1, 3);
            sleep(250);
            drive(11, 0.5, 3);
            sleep(250);
            drive(-12, 0.5, 3);
            sleep(250);
            turnUntilHeading(314, 0.8, 1, 3);
            sleep(250);
            drive(26, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);

        } else if(goldPos == 1) { // center

            drive(3.5, 0.4, 3);
            sleep(500);
            drive(-4.75, 0.4, 3);
            sleep(500);

            // normal position
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);
            drive(17, 0.5, 3);
            sleep(500);
            turnUntilHeading(132, 0.8, 1, 3);
            sleep(500);
            drive(14, 0.5, 3);
            sleep(500);
            turnUntilHeading(222, 0.8, 1, 3);
            sleep(250);
            drive(7, 0.5, 3);
            sleep(250);
            drive(-8, 0.5, 3);
            sleep(250);
            turnUntilHeading(314, 0.8, 1, 3);
            sleep(250);
            drive(22, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);
            //drive(13.25, 0.5, 3);

        } else if(goldPos == 2) { // right

            drive(-1.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(-45, 0.6, 1, 3);
            sleep(500);
            drive(7.5, 0.4, 3);
            sleep(500);
            drive(-7.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(75, 0.6, 1, 3);
            //drive(13.0, 0.5, 3);
        }



    }

    public int bestGoldGuess() {
        // counts, avg_sizes, max_sizes
        // 0 is left
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            if (max_sizes[i][0] > max_sizes[pos][0]) {
                pos = i;
            }
        }
        return pos;
    }
}
