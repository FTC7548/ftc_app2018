package org.firstinspires.ftc.teamcode.util;

public class Extender {

    private Robot r;

    public Extender(Robot r) {
        this.r = r;
    }

    public void extendOut() {
        r.INTAKE_EXT_L.setPosition(ServoPos.INTAKE_EXT_L_FORWARD.pos);
        r.INTAKE_EXT_R.setPosition(ServoPos.INTAKE_EXT_R_FORWARD.pos);
    }

    public void extendIn() {
        r.INTAKE_EXT_L.setPosition(ServoPos.INTAKE_EXT_L_BACK.pos);
        r.INTAKE_EXT_R.setPosition(ServoPos.INTAKE_EXT_R_BACK.pos);
    }

    public void setPower(float f) {
        r.EXTEND_L.setPower(f);
        r.EXTEND_R.setPower(-f);
    }

    public void intake(float f) {
        r.INTAKE_1.setPower(f * .7);
        r.INTAKE_1.setPower(f * .7);
    }

    public void gateUp() {
        r.INTAKE_GATE.setPosition(ServoPos.GATE_UP.pos);
    }

    public void gateDown() {
        r.INTAKE_GATE.setPosition(ServoPos.GATE_DOWN.pos);
    }

    public void pivotUp() {
        r.INTAKE_PIVOT.setPosition(ServoPos.BASKET_PIVOT_UP.pos);
    }

    public void pivotDown() {
        r.INTAKE_PIVOT.setPosition(ServoPos.BASKET_PIVOT_DOWN.pos);
    }

    // TODO: Find values for all these servos
    private enum ServoPos {
        INTAKE_EXT_L_FORWARD (1),
        INTAKE_EXT_L_BACK (.88),

        INTAKE_EXT_R_FORWARD (0),
        INTAKE_EXT_R_BACK (.12),

        GATE_UP (0),
        GATE_DOWN (0),

        BASKET_PIVOT_DOWN (0),
        BASKET_PIVOT_UP (0);

        public final double pos;

        ServoPos(double pos) {
            this.pos = pos;
        }
    }



}
