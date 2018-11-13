package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Robot;

@Autonomous(name="Just the basics", group="lol")
public class MiniAutonomous extends AutonomousOpMode {

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
            turnUntilHeading(35, 0.6, 1, 3);
            sleep(500);
            driveTimeout(0.4, 3);

        } else if(goldPos == 1) { // center

            driveTimeout(0.4, 3);

        } else if(goldPos == 2) { // right

            drive(-1.5, 0.4, 3);
            sleep(500);
            turnUntilHeading(-35, 0.6, 1, 3);
            sleep(500);
            driveTimeout(0.4, 3);

        }
    }
}
