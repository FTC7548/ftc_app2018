package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

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

    public BNO055IMU IMU;

    /**
     * Constructor to initialize all class fields
     * @param hm    Instance of HardwareMap from OpMode
     */
    public Robot(HardwareMap hm) {

        LEFT_FRONT = hm.dcMotor.get("left_front");
        RIGHT_FRONT = hm.dcMotor.get("right_front");
        LEFT_BACK = hm.dcMotor.get("left_back");
        RIGHT_BACK = hm.dcMotor.get("right_back");

        LIFT_1 = hm.dcMotor.get("lift_1");
        LIFT_2 = hm.dcMotor.get("lift_2");
        LIFT_3 = hm.dcMotor.get("lift_3");
        LIFT_4 = hm.dcMotor.get("lift_4");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
    }
}
