package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Robot;

@TeleOp(name="TeleOP")
public class TeleOP extends OpMode {

    private Robot r;

    public void init() {
        r = new Robot(hardwareMap);
    }

    public void start() {

    }

    public void loop() {

        r.setDrivePwr(gamepad1.left_stick_y, gamepad1.right_stick_y);

        if (gamepad1.dpad_up) {
            r.extender.setPower(1F);

        } else if (gamepad1.dpad_down) {
            r.extender.setPower(-1F);
        } else {
            r.extender.setPower(0F);
        }

        if (gamepad1.right_trigger > 0.4) {
            r.lift.setPwr(1);
        } else if (gamepad1.right_bumper) {
            r.lift.setPwr(-1);
        } else {
            r.lift.setPwr(0);

        }
    }
}
