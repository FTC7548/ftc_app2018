package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.util.Robot;
import org.firstinspires.ftc.teamcode.vision.ObjDetectPipeline;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.List;

/**
 * Abstract base class for all autonomous OpModes. This class contains useful methods
 * and components to share between all autonomous modes. This class should be extended
 * and implement `abstract void startOpMode()`
 */
public abstract class AutonomousOpMode extends LinearOpMode {

    private final double WHL_DIAM = 4;
    private final int PPR = 1890;
    private final double HDNG_THRESHOLD = 4;
    private final double PPI = PPR / (WHL_DIAM * Math.PI);

    public Robot r;

    private ElapsedTime runtime = new ElapsedTime();
    public ObjDetectPipeline pipeline;

    /**
     * This method runs after the play button is pressed.  Should be implemented
     * with whatever the OpMode should do after it runs.
     */
    public abstract void startOpMode();

    public void runOpMode() {
        r = new Robot(hardwareMap);
        r.PREVENT_DOWN.setPosition(Robot.RatchetPosition.PREVDOWN_DOWN.position);
        r.PREVENT_UP.setPosition(Robot.RatchetPosition.PREVUP_DOWN.position);

        pipeline = new ObjDetectPipeline();

        pipeline.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 1);


        waitForStart();
        pipeline.enable();
        setCameraPosition(CameraPosition.DOWN);
        sleep(1000);

        startOpMode();
    }

    /**
     * Drive around or something
     * @param inches        Amount to drive, in inches
     * @param pwr           Motor power at which to run
     * @param timeout       Time limit for running so it stops eventually
     */
    public void drive(double inches, double pwr, double timeout) {
        if (!opModeIsActive()) return;
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int newLeftTicks = r.DRIVE_LB.getCurrentPosition() + (int)(inches * PPI);
        int newRightTicks = r.DRIVE_RB.getCurrentPosition() + (int)(inches * PPI);
        runtime.reset();
        if (inches > 0) {
            setPwrNoAbs(pwr);
            while (r.DRIVE_LB.getCurrentPosition() < newLeftTicks && r.DRIVE_RB.getCurrentPosition()
                    < newRightTicks && runtime.seconds() < timeout) {
                telemetry.addData("Pos", "%05d | %05d", r.DRIVE_LB.getCurrentPosition(),
                        r.DRIVE_RB.getCurrentPosition());
                telemetry.addData("Tgt", "%05d | %05d", newLeftTicks, newRightTicks);
                telemetry.update();
                if (this.isStopRequested()) {
                    return;
                }
            }
        } else {
            setPwrNoAbs(-pwr);
            while (r.DRIVE_LB.getCurrentPosition() > newLeftTicks && r.DRIVE_RB.getCurrentPosition()
                    > newRightTicks && runtime.seconds() < timeout) {
                telemetry.addData("Pos", "%05d | %05d", r.DRIVE_LB.getCurrentPosition(),
                        r.DRIVE_RB.getCurrentPosition());
                telemetry.addData("Tgt", "%05d | %05d", newLeftTicks, newRightTicks);
                telemetry.update();
                if (this.isStopRequested()) {
                    return;
                }
            }
        }
        setPwr(0);
    }

    public void driveNew(double inches, double pwr, double timeout) {
        if (!opModeIsActive()) return;
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int newLeftTicks = r.DRIVE_LF.getCurrentPosition() + (int)(inches * PPI);
        int newRightTicks = r.DRIVE_RB.getCurrentPosition() + (int)(inches * PPI);
        runtime.reset();
        if (inches > 0) {
            setPwrNoAbs(pwr);
            while (r.DRIVE_LF.getCurrentPosition() < newLeftTicks && r.DRIVE_RB.getCurrentPosition() < newRightTicks && runtime.seconds() < timeout) {
                telemetry.addData("Pos", "%05d | %05d", r.DRIVE_LF.getCurrentPosition(), r.DRIVE_RB.getCurrentPosition());
                telemetry.addData("Tgt", "%05d | %05d", newLeftTicks, newRightTicks);
                telemetry.update();
                if (this.isStopRequested()) {
                    return;
                }
            }
        } else {
            setPwrNoAbs(-pwr);
            while (r.DRIVE_LF.getCurrentPosition() > newLeftTicks && r.DRIVE_RB.getCurrentPosition() > newRightTicks && runtime.seconds() < timeout) {
                telemetry.addData("Pos", "%05d | %05d", r.DRIVE_LF.getCurrentPosition(), r.DRIVE_RB.getCurrentPosition());
                telemetry.addData("Tgt", "%05d | %05d", newLeftTicks, newRightTicks);
                telemetry.update();
                if (this.isStopRequested()) {
                    return;
                }
            }
        }

        setPwr(0);
    }

    public void encTurn(double linches, double rinches, double pwr, double timeout) {
        if (!opModeIsActive()) return;
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int newLeftTicks = r.DRIVE_LF.getCurrentPosition() + (int)(linches * PPI);
        int newRightTicks = r.DRIVE_RB.getCurrentPosition() + (int)(rinches * PPI);
        runtime.reset();
        double lpwr, rpwr;
        lpwr = (linches > 0? pwr:-pwr);
        rpwr = (rinches > 0? pwr:-pwr);
        r.setDrivePwr(lpwr, rpwr);

        while (!(Math.abs(r.DRIVE_LF.getCurrentPosition() - newLeftTicks) < 100 && Math.abs(r.DRIVE_RB.getCurrentPosition() - newRightTicks) < 100)
                && runtime.seconds() < timeout && opModeIsActive()) {

            if ((Math.abs(r.DRIVE_LF.getCurrentPosition() - newLeftTicks) < 100)) {
                r.DRIVE_LB.setPower(0);
                r.DRIVE_LF.setPower(0);
            }

            if ((Math.abs(r.DRIVE_RB.getCurrentPosition() - newRightTicks) < 100)) {
                r.DRIVE_RB.setPower(0);
                r.DRIVE_RF.setPower(0);
            }

            telemetry.addData("Turning", "%s | %s", linches, rinches);
            telemetry.addData("Pwr", "%s | %s", r.DRIVE_LF.getPower(), r.DRIVE_RB.getPower());
            telemetry.addData("Pos", "%05d | %05d", r.DRIVE_LF.getCurrentPosition(), r.DRIVE_RB.getCurrentPosition());
            telemetry.addData("Tgt", "%05d | %05d", newLeftTicks, newRightTicks);
            telemetry.addData("Diff", "%05d | %05d", r.DRIVE_LF.getCurrentPosition() - newLeftTicks,
                    r.DRIVE_RB.getCurrentPosition() - newRightTicks);
            telemetry.update();
            if (this.isStopRequested()) {
                return;
            }
        }


        setPwr(0);
    }

    /**
     * Set encoder target position
     * @param lf    Left's target position, in ticks
     * @param rt    Right's target position, in ticks
     */
    private void setTarget(int lf, int rt) {
        r.DRIVE_LB.setTargetPosition(lf);
        r.DRIVE_RB.setTargetPosition(rt);
    }

    /**
     * Set power of all motors to a certain value
     * @param pwr   Power of motors
     */
    public void setPwr(double pwr) {
        pwr = Math.abs(pwr);
        r.DRIVE_LB.setPower(pwr);
        r.DRIVE_LF.setPower(pwr);
        r.DRIVE_RF.setPower(pwr);
        r.DRIVE_RB.setPower(pwr);
    }

    /**
     * Set all motors to the same power, regardless of direction
     * @param pwr   Motor power
     */
    public void setPwrNoAbs(double pwr) {
        r.DRIVE_LB.setPower(pwr);
        r.DRIVE_LF.setPower(pwr);
        r.DRIVE_RF.setPower(pwr);
        r.DRIVE_RB.setPower(pwr);
    }

    /**
     * Set the power of the Left drive motors
     * @param pwr   Motor power
     */
    public void setLPwr(double pwr) {
        r.DRIVE_LB.setPower(pwr);
        r.DRIVE_LF.setPower(pwr);
    }

    /**
     * Set the power of the Right drive motors
     * @param pwr   Motor power
     */
    public void setRPwr(double pwr) {
        r.DRIVE_RB.setPower(pwr);
        r.DRIVE_RF.setPower(pwr);
    }

    /**
     * Turns the robot until a certain heading, from the gyro sensor, running L & R motors in opposite directions
     * @param heading   Heading, in degrees
     * @param pwr       Motor power
     * @param dir       Direction, either -1 or 1
     * @param timeout   Time limit for operation
     */
    public void turnUntilHeading(double heading, double pwr, double dir, double timeout) {
        if (!opModeIsActive()) return;
        double yaw = yaw();
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runtime.reset();
        while (Math.abs(distance(yaw(), heading)) > HDNG_THRESHOLD && opModeIsActive() && runtime.seconds() < timeout) {
            yaw = yaw();
            setLPwr(distance(yaw, heading) < 0? -pwr * dir:pwr * dir);
            setRPwr(distance(yaw, heading) > 0? -pwr * dir:pwr * dir);
            telemetry.addData("Turning", distance(yaw, heading) > 0? "Right":"Left");
            telemetry.addData("Power", pwr);
            telemetry.addData("", "Heading Target: %s | Actual: %s", heading, yaw);
            telemetry.addData("", "Distance: %s", distance(yaw,heading));
            telemetry.update();

            if (this.isStopRequested()) {
                return;
            }
        }
        resetEnc();
        setPwr(0);
    }

    /**
     * Drag the left wheels while turning
     * @param heading   Heading, in degrees
     * @param pwr       Motor power
     * @param dir       Direction, either 1 or -1
     * @param timeout   Time limit for operation
     */
    public void dragLeftTurnHeading(double heading, double pwr, double dir, double timeout) {
        if (!opModeIsActive()) return;
        double yaw = yaw();
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runtime.reset();
        while (Math.abs(distance(yaw, heading)) > HDNG_THRESHOLD && opModeIsActive() && runtime.seconds() < timeout) {
            setLPwr(distance(yaw, heading) < 0 ? -pwr * dir : pwr * dir);
            //setRPwr(distance(yaw, heading) > 0? -pwr * dir:pwr * dir);
            telemetry.addData("Turning", distance(yaw, heading) > 0 ? "Right" : "Left");
            telemetry.addData("Power", pwr);
            telemetry.addData("", "Heading Target: %s | Actual: %s", heading, yaw);
            telemetry.addData("", "Distance: %s", distance(yaw, heading));
            telemetry.update();
            yaw = yaw();
            if (this.isStopRequested()) {
                return;
            }
        }
        resetEnc();
        setPwr(0);
    }

    /**
     * Drag the right wheels while turning
     * @param heading   Heading, in degrees
     * @param pwr       Motor power
     * @param dir       Direction, either 1 or -1
     * @param timeout   Time limit for operation
     */
    public void dragRightTurnHeading(double heading, double pwr, double dir, double timeout) {
        if (!opModeIsActive()) return;
        double yaw = yaw();
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runtime.reset();
        while (Math.abs(distance(yaw, heading)) > HDNG_THRESHOLD && opModeIsActive() && runtime.seconds() < timeout) {
            //setLPwr(distance(yaw, heading) < 0? -pwr * dir:pwr * dir);
            setRPwr(distance(yaw, heading) > 0? -pwr * dir:pwr * dir);
            telemetry.addData("Turning", distance(yaw, heading) > 0? "Right":"Left");
            telemetry.addData("Power", pwr);
            telemetry.addData("", "Heading Target: %s | Actual: %s", heading, yaw);
            telemetry.addData("", "Distance: %s", distance(yaw,heading));
            telemetry.update();
            yaw = yaw();
            if (this.isStopRequested()) {
                return;
            }
        }
        resetEnc();
        setPwr(0);
    }

    public void driveTimeout(double pwr, double timeout) {
        if (!opModeIsActive()) return;
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runtime.reset();
        r.setDrivePwr(pwr, pwr);
        while (runtime.seconds() < timeout && opModeIsActive()) {
            telemetry.addData("Time", "%s s / %s s", runtime.seconds(), timeout);
            telemetry.update();
            if (this.isStopRequested()) {
                return;
            }
        }
        r.setDrivePwr(0, 0);
    }

    /**
     * @return Motors busy?
     */
    public boolean busy() {
        return r.DRIVE_RB.isBusy() && r.DRIVE_LB.isBusy() && r.DRIVE_LF.isBusy() && r.DRIVE_RF.isBusy();
    }

    /**
     * @return Get pitch from IMU
     */
    public double pitch() {
        return r.IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).thirdAngle - 3;
    }

    /**
     * @return Get yaw from IMU
     */
    public double yaw() {
        return r.IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

    /**
     * @return Find distance between two angles, in degrees
     */
    public double distance(double angle1, double angle2) {
        return ((angle2 - angle1 + 180) % 360) - 180;
    }

    /**
     * Reset all motor encoders
     */
    public void resetEnc() {
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int contourCount() {
        List<MatOfPoint> contours = pipeline.getContours();
        return contours.size();
    }

    public double avgContourSize() {
        double cumulative_size = 0;
        int num = 0;
        for (MatOfPoint c : pipeline.getContours()) {
            cumulative_size += c.size().area();
            num++;
        }
        return cumulative_size / num;
    }

    public double[] maxContourSize() {
        double max = 0;
        double x = 0, y = 0;

        for (MatOfPoint c : pipeline.getContours()) {
            if (c.size().area() > max) {
                Rect boundingRect = Imgproc.boundingRect(c);
                double xi = (boundingRect.x + boundingRect.width) / 2;
                double yi = (boundingRect.x + boundingRect.width) / 2;
                if (xi > 130 && xi < 200 && yi > 130 && yi < 200) {
                    max = c.size().area();
                    x=xi; y=yi;
                }
            }

        }
        double[] pos = {max, x, y};
        return pos;
    }

    public enum CameraPosition {
        // pitch, yaw
        CENTER (0.75, 0.55),
        LEFT (0.75, 0.25),
        RIGHT (0.75, 0.8),
        DOWN (0.85, 0.45);

        private final double pitch, yaw;

        private CameraPosition(double pitch, double yaw) {
            this.pitch = pitch;
            this.yaw = yaw;
        }
    }

    public void setCameraPosition(CameraPosition pos) {
        r.PHONE_PITCH.setPosition(pos.pitch);
        r.PHONE_YAW.setPosition(pos.yaw);

    }

    double[] counts = new double[3];
    double[] avg_sizes = new double[3];
    double[][] max_sizes = new double[3][3];

    public void cameraLook() {

        sleep(500);
        setCounts(0);
        sleep(250);
        setCameraPosition(CameraPosition.CENTER);
        sleep(500);
        setCounts(1);
        sleep(100);
        setCameraPosition(CameraPosition.RIGHT);
        sleep(500);
        setCounts(2);
        sleep(100);

        telemetry.clear();
        telemetry.clearAll();
        for (int i = 0; i < 3; i++) {
            telemetry.addData("POS" + i, "size: %s (%s, %s)", max_sizes[i][0], max_sizes[i][1], max_sizes[i][2]);

        }
        telemetry.update();
        //sleep(1000);

    }

    public void setCounts(int index) {
        counts[index] = contourCount();
        avg_sizes[index] = avgContourSize();
        max_sizes[index] = maxContourSize();
    }


}