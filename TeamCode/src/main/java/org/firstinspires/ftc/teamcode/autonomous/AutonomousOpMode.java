package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.util.Robot;
import org.firstinspires.ftc.teamcode.vision.ObjDetectPipeline;
import org.firstinspires.ftc.teamcode.vision.VisionConfig;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AutonomousOpMode extends LinearOpMode {

    protected Robot r;

    private double DRIVE_KP = 0.001;
    private double DRIVE_KD = 0;

    private final double DRIVE_PID_A = 3;
    private final double DRIVE_PID_B = 0.1;
    private final double TURN_PID_A = 1;
    private final double TURN_PID_B = 0.3;

    private final double WHL_DIAM = 4;
    private final int PPR = 1890;
    private final double PPI = PPR / (WHL_DIAM * Math.PI);

    private final int COMPLETE_THRESHOLD = 50;

    private ElapsedTime runtime = new ElapsedTime();

    private Telemetry dashboardTelemetry;
    private ObjDetectPipeline pipeline;

    public abstract void startOpMode();

    int BLOCK_POS = -2;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry());
        r = new Robot(hardwareMap);
        //r.lift.lock();

        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();

        pipeline = new ObjDetectPipeline();
        pipeline.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 1);
        pipeline.enable();
        r.lift.lock();

        /*while (!isStarted() && opModeIsActive() && !isStopRequested()) {
            if (senseIterate() == -1) {
                telemetry.addData("pos", "LEFT");
            } else if (senseIterate() == 0) {
                telemetry.addData("pos", "CENTER");
            } else {
                telemetry.addData("pos", "RIGHT");
            }
            idle();
        }*/

        waitForStart();

        BLOCK_POS = sense();
        telemetry.addData("pos", BLOCK_POS);
        telemetry.update();

        startOpMode();

    }

    public void drivePID(double speed, double inches, double timeout, double heading) {
        if (opModeIsActive()) {


            r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            setTargets((int)(inches * PPI));

            double errorAve, integral = 0, previousError;

            double      K_P = AutoConfig.P,
                        K_I = AutoConfig.I,
                        K_D = AutoConfig.D;


            errorAve = (getError(r.DRIVE_LB) + getError(r.DRIVE_RB) +
                    getError(r.DRIVE_LF) + getError(r.DRIVE_RF))/4;

            previousError = errorAve;
            runtime.reset();

            while (opModeIsActive() && !isComplete()) {
                errorAve = (getError(r.DRIVE_LB) + getError(r.DRIVE_RB) +
                        getError(r.DRIVE_LF) + getError(r.DRIVE_RF))/4;
                integral += errorAve;

                double ramp = 0;
                double initialPwr = 0.2;
                if (runtime.seconds() < AutoConfig.T_F && Math.abs(inches) > 3) {
                    double b = speed - initialPwr;
                    ramp = (b * (1 - runtime.seconds() / AutoConfig.T_F)) * (errorAve/Math.abs(errorAve));
                }

                double pwr = Range.clip(K_P * errorAve + K_I * integral + K_D * (errorAve - previousError), -speed, speed) - ramp;

                double offset = AutoConfig.H_P * (yaw() - heading);

                double l_pwr = Range.clip(pwr - offset, -1, 1);
                double r_pwr = Range.clip(pwr + offset, -1, 1);

                r.setDrivePwr(l_pwr, r_pwr);

                dashboardTelemetry.addData("power", pwr);
                dashboardTelemetry.addData("ramp", ramp);
                dashboardTelemetry.addData("error", errorAve);
                dashboardTelemetry.addData("three", 3);
                dashboardTelemetry.update();

                previousError = errorAve;

            }

            r.setDrivePwr(0, 0);
        }
    }

    public void setTargets(int increase) {
        r.DRIVE_LB.setTargetPosition(r.DRIVE_LB.getCurrentPosition() + increase);
        r.DRIVE_LF.setTargetPosition(r.DRIVE_LF.getCurrentPosition() + increase);
        r.DRIVE_RF.setTargetPosition(r.DRIVE_RF.getCurrentPosition() + increase);
        r.DRIVE_RB.setTargetPosition(r.DRIVE_RB.getCurrentPosition() + increase);

    }

    public double yaw() {
        return r.IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

    public int getError(DcMotorEx motor) {
        return motor.getTargetPosition() - motor.getCurrentPosition();
    }

    public boolean isComplete() {
        return  Math.abs(getError(r.DRIVE_LB)) < COMPLETE_THRESHOLD &&
                Math.abs(getError(r.DRIVE_RB)) < COMPLETE_THRESHOLD &&
                Math.abs(getError(r.DRIVE_LF)) < COMPLETE_THRESHOLD &&
                Math.abs(getError(r.DRIVE_RF)) < COMPLETE_THRESHOLD;
    }

    public void turnPID(double tgt_heading, double max_pwr, double timeout) {
        if (opModeIsActive()) {

            r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            double error, initialError, integral;

            error = getError(tgt_heading);
            initialError = error;


            runtime.reset();

            while (opModeIsActive() && (Math.abs(error) > 3)) {

                error = getError(tgt_heading);


                double ramp = 0;
                double pwr = Range.clip(AutoConfig.TURN_P * error, -max_pwr, max_pwr) - ramp;
                r.setDrivePwr(pwr, -pwr);

                dashboardTelemetry.addData("power", pwr);
                dashboardTelemetry.addData("ramp", ramp);
                dashboardTelemetry.addData("error", error);
                dashboardTelemetry.addData("heading", yaw());
                dashboardTelemetry.update();


            }

            r.setDrivePwr(0, 0);
        }
    }


    public void turnNoPID(double tgt_heading, double max_pwr, double timeout) {
        if (opModeIsActive()) {

            r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            double error, initialError, integral;

            error = getError(tgt_heading);
            initialError = error;


            runtime.reset();

            while (opModeIsActive() && (Math.abs(error) > 10)) {

                error = getError(tgt_heading);
                double errorSign = Math.abs(error) / error;



                //double ramp = 0;
                //double pwr = Range.clip(AutoConfig.TURN_P * error, -max_pwr, max_pwr) - ramp;
                r.setDrivePwr(errorSign * max_pwr, -errorSign * max_pwr);

                dashboardTelemetry.addData("power", max_pwr);
                dashboardTelemetry.addData("ramp", 0);
                dashboardTelemetry.addData("error", error);
                dashboardTelemetry.addData("heading", yaw());
                dashboardTelemetry.update();


            }

            r.setDrivePwr(0, 0);
        }
    }

    public double getError(double tgt) {
        double error = tgt - yaw();
        while (error > 180 || error < -180) {
            if (error > 180) error -= 360;
            if (error < -180) error += 360;
        }
        return error;
    }

    public void runTurn() {

    }

    public void unlatch() {
        r.BASKET_PIVOT.setPosition(1);
        r.extender.extendStore();
        r.extender.pivotUp();
        sleep(250);
        r.lift.midNoPivot();
        sleep(250);
        r.lift.setPwr(-1);
        sleep(500);
        r.lift.unlock();
        sleep(200);
        r.lift.setPwr(1);
        sleep(100);
        r.lift.backNoPivot();
        sleep(800);
        r.lift.setPwr(0);
        r.lift.backNoPivot();


    }

    public void liftDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                r.lift.setPwr(-0.2);
                sleep(750);
                r.lift.setPwr(0);

            }
        }).start();
    }

    /**
     * Senses position of block
     * @return -1 if left, 0 if center, 1 if right
     */
    public int sense() {
        runtime.reset();
        int pos = -2;
        while (pos == -2 && runtime.seconds() < 3) {
            List<MatOfPoint> contours = pipeline.getContours();
            if (contours.size() == 0) {
                telemetry.addData("average x/y", "NO POINTS FOUND");
                telemetry.update();
                continue;
            }

            ArrayList<Rect> inBounds = new ArrayList<Rect>();
            for (int i = 0; i < contours.size(); i++) {
                Rect boundingRect = Imgproc.boundingRect(contours.get(i));
                double x = boundingRect.x + boundingRect.width / 2;
                //double y = boundingRect.y + boundingRect.height / 2;

                if (x > VisionConfig.X_THRESHOLD) {
                    inBounds.add(boundingRect);
                    //telemetry.addData("CONTOUR", "(%s, %s) %s", boundingRect.x + boundingRect.width / 2,
                    //boundingRect.y + boundingRect.height / 2, boundingRect.area());
                }

            }

            Collections.sort(inBounds, new SortContourBySize());
            if (inBounds.size() > 0) {
                Rect biggest = inBounds.get(0);
                double y = biggest.y + biggest.height / 2;
                if (y < 200) {
                    telemetry.addData("BEST GUESS", "RIGHT");
                    telemetry.update();
                    sleep(500);
                    return 1;
                } else if (y < 400) {
                    telemetry.addData("BEST GUESS", "CENTER");
                    telemetry.update();
                    sleep(500);
                    return 0;
                } else {
                    telemetry.addData("BEST GUESS", "LEFT");
                    telemetry.update();
                    sleep(500);
                    return -1;
                }
            } else {
                telemetry.addData("NO CONTOURS FOUND IN RANGE", "");
            }
            telemetry.update();
            idle();
        }
        sleep(500);
        return -2;
    }


    public int senseIterate() {
        List<MatOfPoint> contours = pipeline.getContours();
        if (contours.size() == 0) {
            return -2;
        }

        ArrayList<Rect> inBounds = new ArrayList<Rect>();
        for (int i = 0; i < contours.size(); i++) {
            Rect boundingRect = Imgproc.boundingRect(contours.get(i));
            double x = boundingRect.x + boundingRect.width / 2;
            //double y = boundingRect.y + boundingRect.height / 2;

            if (x > VisionConfig.X_THRESHOLD) {
                inBounds.add(boundingRect);
                //telemetry.addData("CONTOUR", "(%s, %s) %s", boundingRect.x + boundingRect.width / 2,
                //boundingRect.y + boundingRect.height / 2, boundingRect.area());
            }

        }

        Collections.sort(inBounds, new SortContourBySize());
        if (inBounds.size() > 0) {
            Rect biggest = inBounds.get(0);
            double y = biggest.y + biggest.height / 2;
            if (y < 200) {
                return 1;
            } else if (y < 400) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }

    public void moveLift(double pwr, int ticks) {
        new Thread(new LiftMover(ticks, pwr)).start();
    }

    public void liftEncoder(double pwr, int target) {
        r.LIFT_L.setTargetPosition(target);
        r.LIFT_R.setTargetPosition(target);

        r.LIFT_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        r.LIFT_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        r.LIFT_L.setPower(pwr);
        r.LIFT_R.setPower(pwr);

        while (opModeIsActive() && r.LIFT_L.isBusy() && r.LIFT_R.isBusy()) {

            sleep(100);
        }

        r.LIFT_L.setPower(0);
        r.LIFT_R.setPower(0);

        r.LIFT_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.LIFT_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    class LiftMover implements Runnable {

        private int target;
        private double pwr;

        public LiftMover(int target, double pwr) {
            this.target = target;
            this.pwr = pwr;
        }

        @Override
        public void run() {
            liftEncoder(pwr, target);
        }
    }


    class SortContourBySize implements Comparator<Rect> {
        public int compare(Rect a, Rect b) {
            return (int)Math.round(Math.ceil(b.area() - a.area()));
        }
    }



}
