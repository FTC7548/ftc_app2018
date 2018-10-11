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
    public DcMotor      LEFT_FRONT,
                        LEFT_BACK,
                        RIGHT_FRONT,
                        RIGHT_BACK;

    /**
     * Lift motors
     */
    public DcMotor      LIFT_1,
                        LIFT_2,
                        LIFT_3,
                        LIFT_4;

    /**
     * Controls the positioning of the phone mount for auto
     */
    public Servo        PHONE_YAW,
                        PHONE_PITCH;

    /**
     * Internal gyro in the REV module
     */
    public BNO055IMU IMU;

    /**
     * Constructor to initialize all class fields
     * @param hm    Instance of HardwareMap from OpMode
     */
    public Robot(HardwareMap hm) {

        LEFT_FRONT = hm.dcMotor.get("lf");
        RIGHT_FRONT = hm.dcMotor.get("rf");
        LEFT_BACK = hm.dcMotor.get("lb");
        RIGHT_BACK = hm.dcMotor.get("rb");

        LEFT_FRONT.setDirection(DcMotor.Direction.REVERSE);
        LEFT_BACK.setDirection(DcMotor.Direction.REVERSE);

        PHONE_YAW = hm.servo.get("yaw");
        PHONE_PITCH = hm.servo.get("pitch");


        /*
        LIFT_1 = hm.dcMotor.get("lift_1");
        LIFT_2 = hm.dcMotor.get("lift_2");
        LIFT_3 = hm.dcMotor.get("lift_3");
        LIFT_4 = hm.dcMotor.get("lift_4");

        */

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
    public void setDrivePwr(float l_pwr, float r_pwr) {
        LEFT_FRONT.setPower(l_pwr);
        LEFT_BACK.setPower(l_pwr);
        RIGHT_FRONT.setPower(r_pwr);
        RIGHT_BACK.setPower(r_pwr);
    }

    /**
     * Sets the motor mode for the four drive motors
     * @param mode Mode for drive motors
     */
    public void setMode(DcMotor.RunMode mode) {
        LEFT_FRONT.setMode(mode);
        LEFT_BACK.setMode(mode);
        RIGHT_FRONT.setMode(mode);
        RIGHT_BACK.setMode(mode);
    }
}
