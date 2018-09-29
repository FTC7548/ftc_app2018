package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.util.Robot;


public abstract class AutonomousOpMode extends LinearOpMode {

    private final double WHL_DIAM = 4;
    private final int PPR = 1890;
    private final double HDNG_THRESHOLD = 10;
    private final double PPI = PPR / (WHL_DIAM * Math.PI);

    public Robot r;

    private ElapsedTime runtime = new ElapsedTime();

    public abstract void startOpMode();

    public void runOpMode() {
        startOpMode();
    }

    public void drive(double inches, double pwr, double timeout) {
        if (!opModeIsActive()) return;
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int newLeftTicks = r.LEFT_FRONT.getCurrentPosition() + (int)(inches * PPI);
        int newRightTicks = r.LEFT_BACK.getCurrentPosition() + (int)(inches * PPI);
        runtime.reset();
        if (inches > 0) {
            setPwrNoAbs(pwr);
            while (r.LEFT_FRONT.getCurrentPosition() < newLeftTicks && r.RIGHT_BACK.getCurrentPosition()
                    < newRightTicks && runtime.seconds() < timeout) {
                telemetry.addData("Pos", "%05d | %05d", r.LEFT_FRONT.getCurrentPosition(),
                        r.RIGHT_BACK.getCurrentPosition());
                telemetry.addData("Tgt", "%05d | %05d", newLeftTicks, newRightTicks);
                telemetry.update();
                if (this.isStopRequested()) {
                    return;
                }
            }
        } else {
            setPwrNoAbs(-pwr);
            while (r.LEFT_FRONT.getCurrentPosition() > newLeftTicks && r.RIGHT_BACK.getCurrentPosition()
                    > newRightTicks && runtime.seconds() < timeout) {
                telemetry.addData("Pos", "%05d | %05d", r.LEFT_FRONT.getCurrentPosition(),
                        r.RIGHT_BACK.getCurrentPosition());
                telemetry.addData("Tgt", "%05d | %05d", newLeftTicks, newRightTicks);
                telemetry.update();
                if (this.isStopRequested()) {
                    return;
                }
            }
        }
        setPwr(0);
    }


    public void setMode(DcMotor.RunMode mode) {
        r.LEFT_FRONT.setMode(mode);
        r.LEFT_BACK.setMode(mode);
        r.RIGHT_FRONT.setMode(mode);
        r.RIGHT_BACK.setMode(mode);
    }

    public void setTarget(int l, int rt) {
        r.LEFT_BACK.setTargetPosition(l);
        r.LEFT_FRONT.setTargetPosition(l);
        r.RIGHT_FRONT.setTargetPosition(rt);
        r.RIGHT_BACK.setTargetPosition(rt);
    }

    public void setPwr(double pwr) {
        pwr = Math.abs(pwr);
        r.LEFT_BACK.setPower(pwr);
        r.LEFT_FRONT.setPower(pwr);
        r.RIGHT_FRONT.setPower(pwr);
        r.RIGHT_BACK.setPower(pwr);
    }

    public void setPwrNoAbs(double pwr) {
        r.LEFT_BACK.setPower(pwr);
        r.LEFT_FRONT.setPower(pwr);
        r.RIGHT_FRONT.setPower(pwr);
        r.RIGHT_BACK.setPower(pwr);
    }

    public void setLPwr(double pwr) {
        r.LEFT_BACK.setPower(pwr);
        r.LEFT_FRONT.setPower(pwr);
    }

    public void setRPwr(double pwr) {
        r.RIGHT_BACK.setPower(pwr);
        r.RIGHT_FRONT.setPower(pwr);
    }
    public void turnUntilHeading(double heading, double pwr, double dir, double timeout) {
        if (!opModeIsActive()) return;
        double yaw = yaw();
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runtime.reset();
        while (Math.abs(distance(yaw, heading)) > HDNG_THRESHOLD && opModeIsActive() && runtime.seconds() < timeout) {
            setLPwr(distance(yaw, heading) < 0? -pwr * dir:pwr * dir);
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

    public void dragLeftTurnHeading(double heading, double pwr, double dir, double timeout) {
        if (!opModeIsActive()) return;
        double yaw = yaw();
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

    public void dragRightTurnHeading(double heading, double pwr, double dir, double timeout) {
        if (!opModeIsActive()) return;
        double yaw = yaw();
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

    public boolean busy() {
        return r.RIGHT_BACK.isBusy() && r.LEFT_BACK.isBusy() && r.LEFT_FRONT.isBusy() && r.RIGHT_FRONT.isBusy();
    }

    public double pitch() {
        return r.IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).thirdAngle - 3;
    }

    public double yaw() {
        return r.IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

    public double distance(double angle1, double angle2) {
        return ((angle2 - angle1 + 180) % 360) - 180;
    }

    public void resetEnc() {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}