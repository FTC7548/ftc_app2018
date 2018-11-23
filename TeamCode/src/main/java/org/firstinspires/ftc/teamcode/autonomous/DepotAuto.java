package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Robot;

@Autonomous(name="depot side")
public class DepotAuto extends AutonomousOpMode{

    @Override
    public void startOpMode() {

        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        r.setLiftPwr(-.25);
        sleep(100);
        r.setLiftPwr(0.5);
        sleep(1000);
        r.setLiftPwr(0);
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);


        setCameraPosition(CameraPosition.LEFT);
        drivePID(5, 0.4, 3);
        cameraLook();
        int goldPos = bestGoldGuess();
        setCameraPosition(CameraPosition.DOWN);

        if (goldPos == 0) {

            drivePID(-1.5, 0.4, 3);
            sleep(300);
            turnUntilHeadingPID(40, 0.8, 1, 3);
            sleep(300);
            drivePID(17, 0.6, 3);
            sleep(300);
            turnUntilHeadingPID(135, 0.8, 1, 3);
            sleep(300);
            drivePID(-10, 0.8, 3);
            plantTheBomb();
            drivePID(-4, 0.6, 3);



        } else if (goldPos == 1) {

            drivePID(12, 0.6, 3);
            sleep(250);
            turnUntilHeadingPID(180, 0.8, 1, 3);
            sleep(250);
            plantTheBomb();
            drivePID(-2, 0.6, 3);

        } else if (goldPos == 2) {

            drivePID(-1.5, 0.4, 3);
            sleep(300);
            turnUntilHeadingPID(-40, 0.8, 1, 3);
            sleep(300);
            drivePID(17, 0.6, 3);
            sleep(300);
            turnUntilHeadingPID(-122, 0.8, 1, 3);
            sleep(300);
            drivePID(-12, 0.8, 3);
            plantTheBomb();
            drivePID(24, 1, 4);
            driveTimeout(0.4, 3);


        }

    }
}
