package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.autonomous.AutonomousOpMode;

@TeleOp(name = "Servo Test", group = "yeet")
public class ServoTest extends AutonomousOpMode {

    public void startOpMode() {

        r.PHONE_PITCH.setPosition(1);
        r.PHONE_YAW.setPosition(0.5);

        while (opModeIsActive()) {

            double new_pitch = r.PHONE_PITCH.getPosition();
            double new_yaw = r.PHONE_YAW.getPosition();

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

            r.PHONE_PITCH.setPosition(Range.clip(new_pitch, 0, 1));
            r.PHONE_YAW.setPosition(Range.clip(new_yaw, 0, 1));

            telemetry.addData("servo pos", "Pitch: %s, Yaw: %s",
                    r.PHONE_PITCH.getPosition(), r.PHONE_YAW.getPosition());
            telemetry.update();
            sleep(250);
            idle();
        }



    }

}
