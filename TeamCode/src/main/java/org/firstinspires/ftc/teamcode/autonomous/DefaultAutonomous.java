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
        if(goldPos == 0) {
            drive(-1.5, 0.3, 3);
            encTurn(10, -10, 0.7, 5);
        } else if(goldPos == 1) {
            drive(3, 0.5, 3);
        } else if(goldPos == 2) {
            drive(-1.5, 0.3, 3);
            encTurn(-10, 10, 0.7, 5);

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
