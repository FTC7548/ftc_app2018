package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
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

    public boolean filter_pressed = false;
    public boolean filter_toggled = false;

    public Thread upProcess = new Thread(new Runnable() {
        public void run() {

        }
    });

    public Thread downProcess = new Thread(new Runnable() {
        public void run() {

        }
    });

    public void init() {

        r = new Robot(hardwareMap);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        down_prevent = true;
        upProcess.run();
        downProcess.run();
    }

    public void start() {
        r.IMU.startAccelerationIntegration(new Position(), new Velocity(), 100);
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
        filter();

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
     * Update the power of the drive motors based on the input from the joysticks
     * Gamepad 1
     */
    public void drive() {
        float l_pwr = gamepad2.left_stick_y * Math.abs(gamepad2.left_stick_y);
        float r_pwr = gamepad2.right_stick_y * Math.abs(gamepad2.right_stick_y);
        if (gamepad2.right_bumper) {
            l_pwr *= 0.5;
            r_pwr *= 0.5;
        }

        telemetry.addData("drive pwr", "L: %s, R: %s", l_pwr, r_pwr);
        r.setDrivePwr(l_pwr, r_pwr);
    }

    boolean up_released = false;
    int cycle_count_for_release = 0;

    boolean servoEngaged = true;

    public void lift() {
        if (gamepad2.left_bumper) { // up
            if (servoEngaged) {
                if (!upProcess.isAlive()) {
                    upProcess = new Thread(new UnlockDelay());
                    upProcess.start();
                }
            } else if (!upProcess.isAlive()){
                if (gamepad2.right_bumper) {
                    r.setLiftPwr(0.5);
                } else {
                    r.setLiftPwr(1);
                }
            }

        } else if (gamepad2.left_trigger > 0.5) { // down
            if (servoEngaged) {
                if (!upProcess.isAlive()) {
                    downProcess = new Thread(new DownDelay());
                    downProcess.start();
                }
            } else if (!downProcess.isAlive()){
                if (gamepad2.right_bumper) {
                    r.setLiftPwr(-1);
                } else {
                    r.setLiftPwr(-0.25);
                }
            }

        } else { // stop
            if (gamepad2.a) {
                disengageRatchet();
            } else {
                engageRatchet();
            }
            r.setLiftPwr(0);
        }
    }

    public void engageRatchet() {
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);
        servoEngaged = true;
    }

    public void disengageRatchet() {
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        servoEngaged = false;
    }

    public class UnlockDelay implements Runnable {
        @Override
        public void run() {
            try {
                disengageRatchet();
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class DownDelay implements Runnable {
        @Override
        public void run() {
            try {
                disengageRatchet();
                Thread.sleep(100);
                r.setLiftPwr(0.5F);
                // i think 100ms was too much
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    /*
    public void lift() {
        double lift_pwr = 1;
        if (gamepad2.right_bumper)
            lift_pwr *= 0.5;

        if(gamepad2.left_bumper && !prevent_down_toggled) { // move up
            cycle_count_for_release = 0;
            if (down_prevent) {
                new Thread(new UnlockDelay()).start();
            } else {
                r.setLiftPwr(lift_pwr);
            }

        } else if (gamepad2.left_trigger > 0.5 && !prevent_up_toggled) { // move down
            up_released = false;

            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);

            if (cycle_count_for_release < 5) {
                r.setLiftPwr(-lift_pwr);
            } else {
                r.setLiftPwr(lift_pwr);
                cycle_count_for_release++;
            }

        } else {
            cycle_count_for_release = 0;
            up_released = false;
            r.setLiftPwr(0);
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);
            down_prevent = true;
        }
    }



    */

    public void ratchet() {
        /*if (gamepad2.a) {
            if (!x_pressed) {
                toggleRatchet();
                x_pressed = true;
            }
        } else {
            x_pressed = false;
        } */ // un comment this if we need the toggle back

        if (gamepad2.x) {
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);
        }
    }

    public void toggleRatchet() {
        if (prevent_down_toggled) {
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
        } else {
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);
        }
        prevent_down_toggled = !prevent_down_toggled;
    }

    public void extend() {
        if (gamepad1.dpad_up) {
            r.EXTEND_L.setPower(-0.7);
            r.EXTEND_R.setPower(0.7);
        } else if (gamepad1.dpad_down) {
            r.EXTEND_L.setPower(0.7);
            r.EXTEND_R.setPower(-0.7);
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
            r.INTAKE_L.setPower(0.7);
            r.INTAKE_R.setPower(-0.7);
        } else {
            r.INTAKE_L.setPower(0);
            r.INTAKE_R.setPower(0);
        }
    }

    public void pivot() {
        if (gamepad2.b) {
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
            r.FILTER.setPosition(.65);
            r.PIVOT_L.setPosition(1);
            r.PIVOT_R.setPosition(0);
        } else {
            r.FILTER.setPosition(.65);
            r.PIVOT_L.setPosition(0.15);
            r.PIVOT_R.setPosition(.85);
        }
        pivot_toggled = !pivot_toggled;
    }

    public void dump() {
        if (gamepad1.y) {
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
            r.DUMP.setPosition(0.53);
        } else{
            r.DUMP.setPosition(0.22);
        }
        dump_toggled = !dump_toggled;
    }

    public void filter() {
        if (gamepad1.left_bumper) {
            if (!filter_pressed) {
                toggleFilter();
                filter_pressed = true;
            }
        } else {
            filter_pressed = false;
        }
    }

    public void toggleFilter() {
        if (filter_toggled) {
            r.FILTER.setPosition(0.65);
        } else {
            r.FILTER.setPosition(0.95);
        }

        filter_toggled = !filter_toggled;
    }



    public void telemetry() {

        telemetry.addData("ratchets", "Up: %s, Down: %s", prevent_up_toggled, prevent_down_toggled);
        telemetry.addData("gyro", "(x: %s, y: %s, z: %s", r.IMU.getPosition().x, r.IMU.getPosition().y, r.IMU.getPosition().z);
        telemetry.update();
    }

}
