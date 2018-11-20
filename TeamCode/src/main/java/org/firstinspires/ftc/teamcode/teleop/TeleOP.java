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


    /*
    CONTROLS

    ## Gamepad 1:
    dpad up/down: extendo
    left bumper: toggle filter bar
    right bumper/trigger: intake
    y: toggle bucket
    b: force filter bar to go in




    ## Gamepad 2:
    Joysticks: Drive
    Right Bumper: Half speed Drive
    Left Bumper/Trigger: Lift
    a: release ratchet while hanging
    x: manual ratchet lock


     */

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

    boolean ratchet_lock = false;

    public Thread upProcess = new Thread(new Runnable() {
        public void run() {

        }
    });

    public Thread downProcess = new Thread(new Runnable() {
        public void run() {

        }
    });

    public Thread hangProcess = new Thread(new Runnable() {
        public void run() {

        }
    });

    public void init() {

        r = new Robot(hardwareMap);

        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        down_prevent = true;
        upProcess.run();
        downProcess.run();
        hangProcess.run();
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

        if (gamepad1.b)  {
            r.FILTER.setPosition(0.6);
        }

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
                    r.setLiftPwr(-0.5);
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
        if (!ratchet_lock) {
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
            servoEngaged = false;
        } else {
            engageRatchet();
        }
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

    public void autoHang() {
        // change false to a gamepad thing when we want to try this
        if (false && !hangProcess.isAlive()) {
            hangProcess = new Thread(new AutoHang());
            hangProcess.start();
        }
    }

    public class AutoHang implements Runnable {
        @Override
        public void run() {
            try {
                r.setLiftPwr(-1);
                Thread.sleep(500);
                engageRatchet();
                Thread.sleep(250);
                r.setLiftPwr(0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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
            //ratchet_lock = true;
            r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
            r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);
        }

        if (gamepad2.y && gamepad2.a) {
            ratchet_lock = false;
        }
    }

    public void toggleRatchet() {
        if (!ratchet_lock) {
            if (prevent_down_toggled) {
                r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_UP.position);
                r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_UP.position);
            } else {
                r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
                r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);
            }
            prevent_down_toggled = !prevent_down_toggled;
        }
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
            new Thread(new PivotFrontDelay()).start();
        } else {
            new Thread(new PivotBackDelay()).start();
        }
        pivot_toggled = !pivot_toggled;
    }

    public class PivotBackDelay implements Runnable {
        public void run() {
            try {
                r.DUMP.setPosition(.4);
                r.FILTER.setPosition(.6);
                Thread.sleep(300);
                r.PIVOT_L.setPosition(.1);
                r.PIVOT_R.setPosition(.9);
            } catch (Exception e) {}
        }
    }

    public class PivotFrontDelay implements Runnable {
        public void run() {
            try {
                r.DUMP.setPosition(.4);
                r.FILTER.setPosition(.6);
                Thread.sleep(300);
                r.PIVOT_L.setPosition(1);
                r.PIVOT_R.setPosition(0);
            } catch (Exception e) {}
        }
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
            r.DUMP.setPosition(0.65);
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
            r.FILTER.setPosition(0.85);
        } else {
            r.FILTER.setPosition(1);
        }

        filter_toggled = !filter_toggled;
    }

    public void telemetry() {

        telemetry.addData("ratchets", "Up: %s, Down: %s", prevent_up_toggled, prevent_down_toggled);
        telemetry.addData("gyro", "(x: %s, y: %s, z: %s", r.IMU.getPosition().x, r.IMU.getPosition().y, r.IMU.getPosition().z);
        telemetry.update();
    }

}
