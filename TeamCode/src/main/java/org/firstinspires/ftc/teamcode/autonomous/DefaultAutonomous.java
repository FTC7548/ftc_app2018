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

            // normal position
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);
            drive(17.5, 0.5, 3);
            sleep(500);

            // turn parallel to wall
            turnUntilHeading(132, 0.8, 1, 3);
            sleep(500);

            // drive parallel to wall
            drive(18, 0.5, 3);
            sleep(500);

            // turn to face the block
            turnUntilHeading(42, 0.6, 1, 3);
            sleep(250);

            // yEET it off
            drive(11, 0.5, 3);
            sleep(250);
            drive(-12, 0.5, 3);
            sleep(250);

            // go back parallel to wall
            turnUntilHeading(314, 0.8, 1, 3);
            sleep(250);

            // park
            drive(26, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);

        } else if(goldPos == 1) { // center

            drive(3.75, 0.4, 3);
            sleep(500);
            drive(-5, 0.4, 3);
            sleep(500);

            // normal position
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);
            drive(16.50, 0.5, 3);
            sleep(500);

            // align against the wall
            turnUntilHeading(132, 0.8, 1, 4); // 132 is old heading
            sleep(500);

            // drive parallel to the wall
            drive(13.5, 0.5, 3);
            sleep(500);

            // turn to face the block
            turnUntilHeading(48, 0.6, 1, 4);
            sleep(250);

            // hit the block off
            drive(-6.75, 0.5, 3);
            sleep(250);
            drive(6.75, 0.5, 3);
            sleep(250);

            // go parallel to the wall again
            turnUntilHeading(325, 0.6, 1, 4);
            sleep(250);

            // park
            drive(22, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);
            //drive(13.25, 0.5, 3);

        } else if(goldPos == 2) { // right

            drive(-1.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(-45, 0.6, 1, 3);
            sleep(500);
            drive(6.5, 0.4, 3);
            sleep(500);
            drive(-6.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);
            drive(17.0, 0.5, 3);
            sleep(500);

            // align against the wall
            turnUntilHeading(132, 0.8, 1, 3);
            sleep(500);

            // go west, young man
            drive(9, 0.5, 3);
            sleep(500);

            // this is where the thing will be flipped out.

            // turn, baby, turn
            turnUntilHeading(222, 0.8, 1, 3);
            sleep(250);

            // hit the block off
            drive(5, 0.5, 3);
            sleep(250);
            drive(-6, 0.5, 3);
            sleep(250);

            // go parallel to the wall again
            turnUntilHeading(324, 0.6, 1, 3);
            sleep(250);

            // park this shizzle, yo
            drive(17, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);
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
