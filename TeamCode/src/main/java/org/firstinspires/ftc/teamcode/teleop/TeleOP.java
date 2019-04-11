package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Macro;
import org.firstinspires.ftc.teamcode.util.Robot;
import org.firstinspires.ftc.teamcode.util.ToggleServo;

@TeleOp(name="TeleOP")
public class TeleOP extends OpMode {

    private Robot r;
    private ToggleServo     bucketArm,
                            bucketPivot,
                            bucketGate,
                            liftLock,
                            intakePivot,
                            intakeBasketPivot,
                            intakeBasketGate;

    private Macro           macroMgr;

    public void init() {
        r = new Robot(hardwareMap);

        r.lift.unlock();
        r.lift.back();

        bucketArm = new ToggleServo() {

            @Override
            public void toggleTrue() {
                r.lift.forward();
            }

            @Override
            public void toggleFalse() {
                r.lift.back();
            }

            @Override
            public boolean toggleCondition() {
                return gamepad1.a;
            }
        };


        liftLock = new ToggleServo() {
            @Override
            public void toggleTrue() {
                r.lift.lock();
            }

            @Override
            public void toggleFalse() {
                r.lift.unlock();
            }

            @Override
            public boolean toggleCondition() {
                return gamepad1.b;
            }
        };

        intakePivot = new ToggleServo() {
            @Override
            public void toggleTrue() {
                r.extender.extendOut();
                r.extender.pivotDown();
                //r.extender.intake(1);
            }

            @Override
            public void toggleFalse() {
                r.extender.extendIn();
                r.extender.pivotUp();
                /*
                if (!(gamepad1.right_trigger > 0.4)) {
                    r.extender.intake(-1);
                } else {
                    r.extender.intake(1);
                }
                */

            }

            @Override
            public boolean toggleCondition() {
                return gamepad2.y;
            }
        };

        intakeBasketPivot = new ToggleServo() {
            @Override
            public void toggleTrue() {
                r.extender.pivotDown();
            }

            @Override
            public void toggleFalse() {
                r.extender.pivotUp();
            }

            @Override
            public boolean toggleCondition() {
                return gamepad2.x;
            }
        };

        /*
        Runnable[] runnables = {
                new Runnable() {
                    @Override
                    public void run() {
                        r.eo soseztdfuonWKsef
                    }

                },
                new Runnable() {
                    @Override
                    public void run() {

                    }
                }};
        macroMgr = new Macro(runnables)
        {
            @Override
            public int condition() {
                if (gamepad1.a) return 0;
                else return -1;
            }
        };
        */

    }

    public void start() {

    }

    public void loop() {
        //macroMgr.update();
        bucketArm.update();
        //bucketGate.update();
        liftLock.update();
        intakePivot.update();
        intakeBasketPivot.update();
        //intakeBasketGate.update();

        if (gamepad1.right_bumper) {
            r.setDrivePwr(gamepad1.left_stick_y * 0.5, gamepad1.right_stick_y * 0.5);
        } else {
            r.setDrivePwr(gamepad1.left_stick_y, gamepad1.right_stick_y);
        }

        if (gamepad2.dpad_up) {
            r.extender.setPower(1F);

        } else if (gamepad2.dpad_down) {
            r.extender.setPower(-1F);
        } else {
            r.extender.setPower(0F);
        }

        if (gamepad1.left_trigger > 0.4) {
            r.lift.setPwr(-1);
        } else if (gamepad1.left_bumper) {
            r.lift.setPwr(1);
        } else {
            r.lift.setPwr(0);
        }

        if (gamepad2.right_trigger > 0.4) {
            r.extender.intake(1);
        } else if (gamepad2.right_bumper) {
            r.extender.intake(-1);
        } else {
            r.extender.intake(0);
        }

        if (gamepad1.x) {
            r.lift.openGate();
        } else {
            r.lift.closeGate();
        }

        if (gamepad2.dpad_right) {
            r.extender.gateDown();
            r.INTAKE_PIVOT.setPosition(0.9);
        } else {
            r.extender.gateUp();
        }

        telemetry.addData("servo #1 position", r.INTAKE_EXT_R.getPosition());
        telemetry.update();

    }

}
