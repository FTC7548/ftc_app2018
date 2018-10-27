package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Robot is the wrapper class to keep track of all of the hardware components in the same place.
 * It must be instantiated with an instance of HardwareMap to reference. Each OpMode creates an
 * instance of Robot to reference.
 *
 * @author chotzen
 * @version 0
 */
public class Robot {

    /**
     * Drive motors
     */
    public DcMotor      DRIVE_LF,
                        DRIVE_LB,
                        DRIVE_RF,
                        DRIVE_RB;

    /**
     * Lift motors
     */
    public DcMotor      LIFT_LF,
                        LIFT_LB,
                        LIFT_RF,
                        LIFT_RB;

    /**
     * Controls the positioning of the phone mount for auto
     */
    public Servo        PHONE_YAW,
                        PHONE_PITCH;

    /**
     * Does what it says on the can
     */
    public Servo        PREVENT_UP;
    public Servo        PREVENT_DOWN;

    /**
     * Internal gyro in the REV module
     */
    public BNO055IMU IMU;

    /**
     * Constructor to initialize all class fields
     * @param hm    Instance of HardwareMap from OpMode
     */
    public Robot(HardwareMap hm) {

        DRIVE_LF = hm.dcMotor.get("drive_lf");
        DRIVE_RF = hm.dcMotor.get("drive_rf");
        DRIVE_LB = hm.dcMotor.get("drive_lb");
        DRIVE_RB = hm.dcMotor.get("drive_rb");

        DRIVE_RF.setDirection(DcMotor.Direction.REVERSE);
        DRIVE_RB.setDirection(DcMotor.Direction.REVERSE);

        LIFT_LF = hm.dcMotor.get("lift_lf");
        LIFT_RF = hm.dcMotor.get("lift_rf");
        LIFT_LB = hm.dcMotor.get("lift_lb");
        LIFT_RB = hm.dcMotor.get("lift_rb");

        PHONE_YAW = hm.servo.get("yaw");
        PHONE_PITCH = hm.servo.get("pitch");

        PREVENT_UP = hm.servo.get("upratchet");
        PREVENT_DOWN = hm.servo.get("downratchet");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        IMU = hm.get(BNO055IMU.class, "imu");
        IMU.initialize(parameters);
    }

    /**
     * Sets the power of the drive motors
     * @param l_pwr Power of left drive motors
     * @param r_pwr Power of the right drive motors
     */
    public void setDrivePwr(double l_pwr, double r_pwr) {
        DRIVE_LF.setPower(l_pwr);
        DRIVE_LB.setPower(l_pwr);
        DRIVE_RF.setPower(r_pwr);
        DRIVE_RB.setPower(r_pwr);
    }

    public void setLiftPwr(double pwr) {
        LIFT_LF.setPower(-pwr);
        LIFT_LB.setPower(-pwr);
        LIFT_RF.setPower(pwr);
        LIFT_RB.setPower(pwr);
    }

    /**
     * Sets the motor mode for the four drive motors
     * @param mode Mode for drive motors
     */
    public void setMode(DcMotor.RunMode mode) {
        DRIVE_LF.setMode(mode);
        DRIVE_LB.setMode(mode);
        DRIVE_RF.setMode(mode);
        DRIVE_RB.setMode(mode);
    }

    public enum RatchetPosition {
        PREVUP_UP (.1),
        PREVUP_DOWN (.45),
        PREVDOWN_UP (.85),
        PREVDOWN_DOWN (1);

        public final double position;

        RatchetPosition(double position) {
            this.position = position;
        }
    }

}