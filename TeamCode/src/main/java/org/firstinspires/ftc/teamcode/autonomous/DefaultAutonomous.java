package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Robot;

@Autonomous(name="Default", group="lol")
public class DefaultAutonomous extends AutonomousOpMode {

    public void startOpMode() {
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        r.setLiftPwr(-0.5);
        sleep(100);
        r.setLiftPwr(0.5);
        sleep(1500);
        r.setLiftPwr(0);
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
        drive(-5, 0.5, 3);
        cameraLook();
        switch (bestGoldGuess()) {
            case 0: // do stuff for left
                dragRightTurnHeading(90, 0.4, 1, 3);
                dragLeftTurnHeading(0, 0.4, 1, 3);
            case 1: // do stuff for center

                drive(10, 0.3, 3);

            case 2: // do stuff for right
                dragLeftTurnHeading(-90, 0.4, 1, 3);
                dragRightTurnHeading(0, 0.4, 1, 3);

        }

    }

    public int bestGoldGuess() {
        // counts, avg_sizes, max_sizes
        // 0 is left
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            if (max_sizes[i] > max_sizes[pos]) {
                pos = i;
            }
        }
        return pos;
    }
}
