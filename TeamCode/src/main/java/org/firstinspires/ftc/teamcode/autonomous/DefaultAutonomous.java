package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Robot;

@Autonomous(name="Default", group="lol")
public class DefaultAutonomous extends AutonomousOpMode {

    public void startOpMode() {

        /*r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        r.setLiftPwr(-0.5);
        sleep(100);
        r.setLiftPwr(0.5);
        sleep(1000);
        r.setLiftPwr(0);
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
        driveTimeout(0.25, 4);
        //turnUntilHeading(1, 0.5, 1, 3);
        r.FILTER.setPosition(.65); //  other one is .95*/

        drive(4.5, 0.3, 3);


        cameraLook();
        int goldPos = bestGoldGuess();
        if(goldPos == 0) { // left

            drive(-1.5, 0.4, 3);
            sleep(250);
            turnUntilHeading(45, 0.6, 1, 3);
            sleep(250);
            drive(8 , 0.4, 3);
            sleep(250);
            drive(-8, 0.4, 3);
            turnUntilHeading(75, 0.6, 1, 3);
            /*
            sleep(250);
            drive(14, 0.5, 3);
            sleep(250);*/

        } else if(goldPos == 1) { // center

            drive(3.5, 0.4, 3);
            drive(-4.75, 0.4, 3);
            turnUntilHeading(75, 0.6, 1, 3);
            //drive(13.25, 0.5, 3);

        } else if(goldPos == 2) { // right

            drive(-1.5, 0.4, 3);
            sleep(250);
            turnUntilHeading(-45, 0.6, 1, 3);
            sleep(250);
            drive(7.5, 0.4, 3);
            sleep(250);
            drive(-7.5, 0.4, 3);
            turnUntilHeading(75, 0.6, 1, 3);
            //drive(13.0, 0.5, 3);
        }

        /*turnUntilHeading(138, 0.6, 1, 3);
        sleep(250);
        drive(18, 0.5, 4);
        sleep(250);
        driveTimeout(0.5, 1);
        sleep(250);
        drive(-2.5, 0.3, 2);
        sleep(250);
        turnUntilHeading(275, 0.6, 1, 4);
        sleep(250);
        drive(4.5,0.5, 4);*/
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
