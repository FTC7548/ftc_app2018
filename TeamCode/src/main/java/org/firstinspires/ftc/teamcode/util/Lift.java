package org.firstinspires.ftc.teamcode.util;

public class Lift {

    private Robot r;

    public Lift(Robot r) {
        this.r = r;
    }


    // Lift actions

    public void forward() {
        r.BASKET_EXT_L.setPosition(ServoPos.BASKET_EXT_L_FORWARD.pos);
        r.BASKET_EXT_R.setPosition(ServoPos.BASKET_EXT_R_FORWARD.pos);
    }

    public void back() {
        r.BASKET_EXT_L.setPosition(ServoPos.BASKET_EXT_L_BACK.pos);
        r.BASKET_EXT_R.setPosition(ServoPos.BASKET_EXT_R_BACK.pos);
    }

    public void openGate() {
        r.HOLDER_GATE.setPosition(ServoPos.GATE_UP.pos);
    }

    public void closeGate() {
        r.HOLDER_GATE.setPosition(ServoPos.GATE_DOWN.pos);
    }

    public void bucketIntake() {
        r.BASKET_PIVOT.setPosition(ServoPos.BASKET_PIVOT_INTAKE.pos);
    }

    public void bucketDump() {
        r.BASKET_PIVOT.setPosition(ServoPos.BASKET_PIVOT_DUMP.pos);
    }

    public void lock() {
        r.HOOK_L.setPosition(ServoPos.HOOK_L_LOCKED.pos);
        r.HOOK_R.setPosition(ServoPos.HOOK_R_LOCKED.pos);
    }

    public void unlock() {
        r.HOOK_L.setPosition(ServoPos.HOOK_L_UNLOCKED.pos);
        r.HOOK_R.setPosition(ServoPos.HOOK_R_UNLOCKED.pos);
    }

    public void setPwr(double pwr) {
        r.LIFT_L.setPower(-pwr);
        r.LIFT_R.setPower(pwr);
    }

    // TODO: Find values for all these servos
    private enum ServoPos {
        BASKET_EXT_L_FORWARD (.86),
        BASKET_EXT_L_BACK (.07), // the one that is not servo 5

        BASKET_EXT_R_FORWARD (.93),
        BASKET_EXT_R_BACK (.12), // servo 5

        HOOK_L_LOCKED (.99), // 3
        HOOK_L_UNLOCKED (.87),

        HOOK_R_LOCKED (.01), // 6
        HOOK_R_UNLOCKED (.13),

        GATE_UP (0),
        GATE_DOWN (0),

        BASKET_PIVOT_INTAKE (0.61),
        BASKET_PIVOT_DUMP (0.44);

        public final double pos;

        ServoPos(double pos) {
            this.pos = pos;
        }
    }

}
