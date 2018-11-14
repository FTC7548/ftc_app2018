package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.util.PhilSwift;
import org.firstinspires.ftc.teamcode.util.Robot;

@Autonomous(name="Default", group="lol")
public class DefaultAutonomous extends AutonomousOpMode {

    public void startOpMode() {



        // .31 and .69 for pivot left and right
        // .6 for filter bar when we're flipping through
        // .3 lock .4 release
        /*
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        r.setLiftPwr(-.25);
        sleep(100);
        r.setLiftPwr(0.5);
        sleep(1000);
        r.setLiftPwr(0);
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);
        */
        // grip onto glyph

        /*
        setCameraPosition(CameraPosition.LEFT);
        drive(4.5, 0.3, 3);
        cameraLook();
        int goldPos = bestGoldGuess();
        setCameraPosition(CameraPosition.DOWN);
        */

        // ACCURACY TEST
        for (int i = 0; i < 1; i++) {
            drivePID(-10, .6, 3);
            sleep(500);
            drivePID(10, .6, 3);
            sleep(500);
        }

        drivePID(-5, .6, 3);
        sleep(500);
        turnUntilHeadingPID(90, 0.8, 1, 5);
        sleep(1000);
        turnUntilHeadingPID(0, 0.8, 1, 5);
        /*
        turnUntilHeadingPID(90, .8, 1, 5);
        sleep(1000);
        turnUntilHeadingPID(0, .8, 1, 5);
        */
        PhilSwift.start(hardwareMap.appContext, R.raw.philswift);
        sleep(2000);
        PhilSwift.stop();
        drivePID(5, .6, 3);
        /*
        drivePID(-10, 0.6, 3);
        sleep(500);
        drivePID(10, 0.6, 3);
        sleep(500);
        drivePID(-10, 0.6, 3);
        sleep(500);
        drivePID(10, 0.6, 3);
        sleep(500);
        drivePID(-10, 0.6, 3);
        sleep(500);
        drivePID(10, 0.6, 3);
        sleep(500);
        */
        int goldPos = 0;
        if(goldPos == 0) { // left

            // NEW WIP
            /*
            drivePID(-1.5, 0.4, 3);
            sleep(500);
            turnUntilHeadingPID(45, 0.6, 1, 3);
            sleep(500);
            drivePID(8 , 0.4, 3);
            sleep(500);
            drivePID(-7.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(75, 0.6, 1, 3);
            */


            /*
            drive(-1.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(45, 0.6, 1, 3);
            sleep(500);
            drive(8 , 0.4, 3);
            sleep(500);
            drive(-7.5, 0.4, 3);
            sleep(500);

            // normal position
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);
            drive(17.5, 0.5, 3);
            sleep(500);

            // turn parallel to wall
            turnUntilHeading(128, 0.8, 1, 3);
            sleep(500);

            // drive parallel to wall
            drive(18, 0.5, 3);
            sleep(500);

            // turn to face the block
            turnUntilHeading(222, 0.8, 1, 3);
            sleep(250);

            // yEET it off
            drive(11, 0.5, 3);
            sleep(250);
            drive(-11, 0.5, 3);
            sleep(250);

            // go back parallel to wall
            turnUntilHeading(316, 0.8, 1, 3);
            sleep(250);

            // bomb has been planted
            plantTheBomb();

            // skrt skrrrt
            drive(26, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);
            */

        } else if(goldPos == 1) { // center

            drive(3.75, 0.4, 3);
            sleep(500);
            drive(-4.5, 0.4, 3);
            sleep(500);

            // normal position
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);
            drive(17, 0.5, 3);
            sleep(500);

            // align against the wall
            turnUntilHeading(128, 0.8, 1, 4); // 132 is old heading
            sleep(500);

            // drive parallel to the wall
            drive(14.31, 0.5, 3);
            sleep(500);

            // turn to face the block
            turnUntilHeading(48, 0.6, 1, 4);
            sleep(250);

            // hit the block off
            drive(-5.5, 0.5, 3);
            sleep(250);
            drive(6.5, 0.5, 3);
            sleep(250);

            // go parallel to the wall again
            turnUntilHeading(338, 0.8, 1, 4);
            sleep(250);

            // bomb has been planted
            plantTheBomb();
            drive(-2, 0.4, 3);

            // park
            drive(24, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);

        } else if(goldPos == 2) { // right

            drive(-1.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(-45, 0.6, 1, 3);
            sleep(500);
            drive(7.3, 0.4, 3);
            sleep(500);
            drive(-7.3, 0.4, 3);
            sleep(500);
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);
            drive(17.5, 0.5, 3);
            sleep(500);

            // align against the wall
            turnUntilHeading(130, 0.8, 1, 3);
            sleep(500);

            // go west, young man
            drive(9, 0.5, 3);
            sleep(500);

            // this is where the thing will be flipped out.

            // turn, baby, turn

            // hit the block off
            /*drive(-5, 0.5, 3);
            sleep(250);
            drive(6, 0.5, 3);
            sleep(250); */

            // go parallel to the wall again

            encTurn(-4, 4, 0.8, 1);
            sleep(250);
            turnUntilHeading(340, 0.8, 1, 3);
            sleep(250);

            // bomb has been planted
            plantTheBomb();
            drive(-6, 0.4, 3);

            // park this shizzle, yo
            drive(23, 1, 3);
            drive(6, 0.5, 3);
            //PhilSwift.start(hardwareMap.appContext, R.raw.philswift);
            driveTimeout(0.4, 3);
            //PhilSwift.stop();
        }



    }


}
