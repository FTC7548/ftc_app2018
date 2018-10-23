package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.autonomous.AutonomousOpMode;

@TeleOp(name = "Servo Test", group = "yeet")
public class ServoTest extends AutonomousOpMode {

    public void startOpMode() {

        r.PREVENT_UP.setPosition(1);
        r.PREVENT_DOWN.setPosition(0.5);

        while (opModeIsActive()) {

            double new_pitch = r.PREVENT_UP.getPosition();
            double new_yaw = r.PREVENT_DOWN.getPosition();

            if (gamepad1.x) {
                new_pitch += 0.05;
            }

            if (gamepad1.y) {
                new_pitch -= 0.05;
            }

            if (gamepad1.a) {
                new_yaw += 0.05;
            }

            if (gamepad1.b) {
                new_yaw -= 0.05;
            }

            r.PREVENT_UP.setPosition(Range.clip(new_pitch, 0, 1));
            r.PREVENT_DOWN.setPosition(Range.clip(new_yaw, 0, 1));

            telemetry.addData("servo pos", "up: %s, down: %s",
                    r.PREVENT_UP.getPosition(), r.PREVENT_DOWN.getPosition());
            telemetry.update();
            sleep(250);
            idle();
        }



    }

}
