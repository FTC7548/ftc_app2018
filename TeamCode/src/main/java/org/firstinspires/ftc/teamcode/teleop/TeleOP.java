package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Robot;
import org.firstinspires.ftc.teamcode.util.ToggleServo;

@TeleOp(name= "Drive", group = "TeleOP")
/**
 * Main drive TeleOP mode, for the driver-controlled period of the game
 */
public class TeleOP extends OpMode {

    /**
     * Instance of the robot class for all of the hardware
     */
    private Robot r;

    private ToggleServo pitch;
    private boolean moving_down, down_prevent;

    public void init() {
        r = new Robot(hardwareMap);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        down_prevent = false;

    }

    public void start() {
        // Stuff will go here later maybe? probably not idk
    }

    public void loop() {
        // graber.update();
        drive();
        lift();

        if (gamepad1.x)
            r.PHONE_YAW.setPosition(0);

        if (gamepad1.y)
            r.PHONE_PITCH.setPosition(0);
        else
            r.PHONE_PITCH.setPosition(1);

    }

    /**
     * Update the power of the drive motors based on the input from the joystics
     * Gamepad 1
     */
    public void drive() {
        float l_pwr = gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y);
        float r_pwr = gamepad1.right_stick_y * Math.abs(gamepad1.right_stick_y);
        telemetry.addData("drive pwr", "L: %s, R: %s", l_pwr, r_pwr);
        r.setDrivePwr(l_pwr, r_pwr);
    }

    public double lift_pwr;

    private Thread delay = new Thread();
    boolean processing = false;

    public void lift() {
        if (gamepad1.right_bumper)
            lift_pwr = 0.5;
        else
            lift_pwr = 1;

        if (gamepad1.left_bumper) {
            moving_down = false;
            if (down_prevent) {
                new Thread(new UnlockDelay()).start();
            } else {
                r.setLiftPwr(lift_pwr);
            }

        } else if (gamepad1.left_trigger > 0.5) { // if lift should be moved down
            if (!processing) {
                if (!moving_down) {
                    new Thread(new DownDelay()).start();

                } else {
                    r.setLiftPwr(-lift_pwr);
                }
            }
        } else { // fixed position
            r.setLiftPwr(0);
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
            down_prevent = true;
            moving_down = false;
        }
    }

    public class UnlockDelay implements Runnable {
        @Override
        public void run() {
            try {
                r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
                Thread.sleep(100);
                down_prevent = false;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class DownDelay implements Runnable {
        @Override
        public void run() {
            try {
                moving_down = true;
                processing = true;
                r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
                down_prevent = false;
                Thread.sleep(100);
                r.setLiftPwr(0.5F);
                // i think 100ms was too much
                Thread.sleep(20);
                r.setLiftPwr(-lift_pwr);
                processing = false;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
