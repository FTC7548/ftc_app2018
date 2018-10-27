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

    public boolean x_pressed = false;
    public boolean prevent_down_toggled = false;

    public boolean a_pressed = false;
    public boolean prevent_up_toggled = false;

    public boolean b_pressed = false;
    public boolean pivot_toggled = false;

    public boolean y_pressed = false;
    public boolean dump_toggled = false;


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
        ratchet();
        extend();
        intake();
        pivot();
        telemetry();
        dump();
        /*
        if (gamepad1.x)
            r.PHONE_YAW.setPosition(0);

        if (gamepad1.y)
            r.PHONE_PITCH.setPosition(0);
        else
            r.PHONE_PITCH.setPosition(1);
            */
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
        if(gamepad1.left_bumper && !prevent_up_toggled) {
            r.setLiftPwr(-1);
        } else if(gamepad1.left_trigger > 0.5 && !prevent_down_toggled) {
            r.setLiftPwr(1);
        } else {
            r.setLiftPwr(0);
        }
    }

    public void ratchet() {
        if (gamepad1.x) {
            if (!x_pressed) {
                togglePreventDown();
                x_pressed = true;
            }
        } else {
            x_pressed = false;
        }
        if (gamepad1.a) {
            if (!a_pressed) {
                togglePreventUp();
                a_pressed = true;
            }
        } else {
            a_pressed = false;
        }
    }

    public void togglePreventDown() {
        if (prevent_down_toggled) {
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
        } else {
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
        }
        prevent_down_toggled = !prevent_down_toggled;
    }

    public void togglePreventUp() {
        if (prevent_up_toggled) {
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        } else {
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);
        }
        prevent_up_toggled = !prevent_up_toggled;
    }

    /*
    public void lift() {
        if (gamepad1.y)
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
    */
    public void extend() {
        if (gamepad1.dpad_up) {
            r.EXTEND_L.setPower(-0.7);
            r.EXTEND_R.setPower(-0.7);
        } else if (gamepad1.dpad_down) {
            r.EXTEND_L.setPower(0.7);
            r.EXTEND_R.setPower(0.7);
        } else {
            r.EXTEND_L.setPower(0);
            r.EXTEND_R.setPower(0);
        }
    }

    public void intake() {
        if (gamepad1.right_bumper) {
            r.INTAKE_L.setPower(-0.7);
            r.INTAKE_R.setPower(0.7);
        } else if (gamepad1.right_trigger > 0.5) {
            r.INTAKE_L.setPower(-0.7);
            r.INTAKE_R.setPower(0.7);
        } else {
            r.INTAKE_L.setPower(0);
            r.INTAKE_R.setPower(0);
        }
    }

    public void pivot() {
        if (gamepad1.b) {
            if (!b_pressed) {
                togglePivot();
                b_pressed = true;
            }
        } else {
            b_pressed = false;
        }
    }

    public void togglePivot() {
        if (pivot_toggled) {
            r.PIVOT_L.setPosition(1);
            r.PIVOT_R.setPosition(0);
        } else {
            r.PIVOT_L.setPosition(0);
            r.PIVOT_R.setPosition(1);
        }
        pivot_toggled = !pivot_toggled;
    }

    public void dump() {
        if (gamepad1.left_stick_y > 0.5) {
            if (!y_pressed) {
                toggleDump();
                y_pressed = true;
            }
        } else {
            y_pressed = false;
        }
    }

    public void toggleDump() {
        if (dump_toggled) {
            r.DUMP.setPosition(0.2);
        } else {
            r.DUMP.setPosition(.55);
        }
        dump_toggled = !dump_toggled;
    }

    public void telemetry() {
        telemetry.addData("ratchets", "Up: %s, Down: %s", prevent_up_toggled, prevent_down_toggled);
        telemetry.update();
    }

}
