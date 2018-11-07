package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.autonomous.AutonomousOpMode;

@TeleOp(name = "Servo Test", group = "yeet")
public class ServoTest extends AutonomousOpMode {

    public void startOpMode() {

        Servo servo1 = r.PHONE_PITCH;
        Servo servo2 = r.PHONE_YAW;

        servo1.setPosition(1);
        servo2.setPosition(0.5);

        while (opModeIsActive()) {

            double new_pitch = servo1.getPosition();
            double new_yaw = servo2.getPosition();

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

            servo1.setPosition(Range.clip(new_pitch, 0, 1));
            servo2.setPosition(Range.clip(new_yaw, 0, 1));

            telemetry.addData("servo pos", "up: %s, down: %s",
                    servo1.getPosition(), servo2.getPosition());

            telemetry.update();
            sleep(250);
            idle();
        }



    }

}
