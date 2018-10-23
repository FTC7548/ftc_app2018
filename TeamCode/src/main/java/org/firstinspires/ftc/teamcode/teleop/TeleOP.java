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

    public void init() {
        r = new Robot(hardwareMap);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

    public void lift() {
        if(gamepad1.left_bumper) {
            r.setLiftPwr(1);
        } else if (gamepad1.left_trigger > 0.5) {
            r.setLiftPwr(-1);
        } else {
            r.setLiftPwr(0);
        }
    }
}
