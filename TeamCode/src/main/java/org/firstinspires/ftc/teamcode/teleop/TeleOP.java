package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Robot;
import org.firstinspires.ftc.teamcode.util.ToggleServo;

@TeleOp(name="TeleOP")
public class TeleOP extends OpMode {

    private Robot r;
    private ToggleServo     bucketArm,
                            bucketPivot,
                            bucketGate,
                            liftLock;

    public void init() {
        r = new Robot(hardwareMap);


        bucketArm = new ToggleServo() {

            @Override
            public void toggleTrue() {
                r.lift.forward();
            }

            @Override
            public void toggleFalse() {
                r.lift.back();
            }

            @Override
            public boolean toggleCondition() {
                return gamepad1.a;
            }
        };


        liftLock = new ToggleServo() {
            @Override
            public void toggleTrue() {
                r.lift.lock();
            }

            @Override
            public void toggleFalse() {
                r.lift.unlock();
            }

            @Override
            public boolean toggleCondition() {
                return gamepad1.b;
            }
        };


    }

    public void start() {

    }

    public void loop() {
        bucketArm.update();
        liftLock.update();

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
