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

        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        r.setLiftPwr(-.25);
        sleep(100);
        r.setLiftPwr(0.5);
        sleep(1000);
        r.setLiftPwr(0);
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);

        // grip onto glyph

        setCameraPosition(CameraPosition.LEFT);
        drivePID(5, 0.4, 3);
        cameraLook();
        int goldPos = bestGoldGuess();
        setCameraPosition(CameraPosition.DOWN);

        // ACCURACY TEST
        /*
        for (int i = 0; i < 3; i++) {
            drivePID(-10, .6, 3);
            sleep(500);
            drivePID(10, .6, 3);
            sleep(500);
        }
        */
        /*
        drivePID(-5, .6, 3);
        sleep(500);
        turnUntilHeadingPID(90, 1, 1, 5);
        sleep(1000);
        turnUntilHeadingPID(0, 1, 1, 5);
        */

        /*
        turnUntilHeadingPID(90, .8, 1, 5);
        sleep(1000);
        turnUntilHeadingPID(0, .8, 1, 5);
        */
        /*
        PhilSwift.start(hardwareMap.appContext, R.raw.philswift);
        sleep(2000);
        PhilSwift.stop();
        drivePID(5, .6, 3);
        */
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
        //int goldPos = 0;
        if(goldPos == 0) { // left
            // NEW WIP
            drivePID(-1.5, 0.4, 3);
            sleep(300);
            turnUntilHeading(44, 0.6, 1, 3);
            sleep(300);
            drivePID(8 , 0.6, 3);
            sleep(300);
            drivePID(-8, 0.6, 3);
            sleep(300);

            // normal position
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(300);
            drivePID(20, 0.7, 3);
            sleep(300);

            // turn parallel to wall
            turnUntilHeading(133, 0.6, 1, 3);
            sleep(300);

            // drive parallel to wall
            drivePID(16, 0.7, 3);
            sleep(300);

            // turn to face the block
            turnUntilHeadingPID(52, 0.6, 1, 3);
            sleep(300);

            // yEET it off
            drivePID(-11, 0.6, 3);
            sleep(300);
            drivePID(12, 0.6, 3);
            sleep(300);

            // go back parallel to wall
            turnUntilHeadingPID(-35, 0.8, 1, 3);
            sleep(300);

            // bomb has been planted
            plantTheBomb();

            // skrt skrrrt
            drive(26, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);


        } else if(goldPos == 1) { // center

            drivePID(3.75, 0.4, 3);
            sleep(500);
            drivePID(-4.5, 0.4, 3);
            sleep(500);

            // normal position
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(500);

            // drive towards the wall
            drivePID(18, 0.6, 3);
            sleep(500);

            // align against the wall
            turnUntilHeading(128, 0.6, 1, 4); // 132 is old heading
            sleep(500);

            // drive parallel to the wall
            drivePID(14.31, 0.6, 3);
            sleep(500);

            // turn to face the block
            turnUntilHeadingPID(60, 0.8, 1, 4);
            sleep(250);

            // hit the block off
            drivePID(-5.5, 0.6, 3);
            sleep(250);
            drivePID(7, 0.6, 3);
            sleep(250);

            // go parallel to the wall again
            turnUntilHeadingPID(-31, 0.8, 1, 3);
            sleep(250);

            // bomb has been planted
            plantTheBomb();
            drivePID(-2, 0.4, 3);

            // park
            drive(24, 1, 3);
            drive(6, 0.5, 3);
            driveTimeout(0.4, 3);

        } else if(goldPos == 2) { // right

            drivePID(-1.5, 0.4, 3);
            sleep(300);
            turnUntilHeading(-45, 0.6, 1, 3);
            sleep(300);
            drivePID(7.3, 0.6, 3);
            sleep(300);
            drivePID(-7.3, 0.6, 3);
            sleep(300);
            turnUntilHeading(75, 0.6, 1, 3);
            sleep(300);
            drivePID(18.5, 0.6, 3);
            sleep(300);

            // align against the wall
            turnUntilHeadingPID(134, 0.6, 1, 4);
            sleep(300);

            // go west, young man
            drivePID(9, 0.5, 3);
            sleep(300);

            // this is where the thing will be flipped out.

            // turn, baby, turn

            // hit the block off
            /*drive(-5, 0.5, 3);
            sleep(250);
            drive(6, 0.5, 3);
            sleep(250); */

            // go parallel to the wall again

            //encTurn(-4, 4, 0.8, 1);
            sleep(250);
            turnUntilHeadingPID(60, 0.8, 1, 3);
            sleep(250);
            drive(-2, 0.5, 2);
            sleep(250);
            drive(2.5, 0.5, 2);
            sleep(250);
            turnUntilHeadingPID(-28, 0.8, 1, 3);
            sleep(250);
            // bomb has been planted
            plantTheBomb();
            drivePID(-6, 0.4, 3);

            // park this shizzle, yo
            drive(23, 1, 3);
            drive(6, 0.5, 3);
            //PhilSwift.start(hardwareMap.appContext, R.raw.philswift);
            driveTimeout(0.4, 3);
            //PhilSwift.stop();
        }



    }


}