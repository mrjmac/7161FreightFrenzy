package NightfallOpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class NightfallOpMode extends OpMode {

    DcMotor BL; //back left - [3 e2]
    DcMotor ML; //middle left - [3 c]
    DcMotor FL; //front left - [2 c]
    DcMotor BR; //back right - [2 e2]
    DcMotor MR; //middle right - [1 e2]
    DcMotor FR; //front right - [0 e2]
    DcMotor intake; //intake - [0 c]
    DcMotor lift; //lift - [1 c]

    //Servo pivot1; //pivot servo - [3 c]
    //Servo pivot2; //pivot servo - [2 e2]

    Servo hatch; //output servo - [5 c]
    Servo cap; //cap servo - [4 c]

    CRServo duckL; //left duck - [0 e2]
    CRServo duckR; //right duck - [1 e2]


    public void init() {

        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        ML = hardwareMap.dcMotor.get("ML");
        MR = hardwareMap.dcMotor.get("MR");

        intake = hardwareMap.dcMotor.get("intake");
        lift = hardwareMap.dcMotor.get("lift");

       // pivot1 = hardwareMap.servo.get("in1");
        //pivot2 = hardwareMap.servo.get("in2");
        hatch = hardwareMap.servo.get("hatch");

        cap = hardwareMap.servo.get("cap");
        hatch = hardwareMap.servo.get("hatch");

        duckL = hardwareMap.crservo.get("duckL");
        duckR = hardwareMap.crservo.get("duckR");

        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        MR.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        ML.setDirection(DcMotorSimple.Direction.REVERSE);

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ML.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ML.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        MR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("init ", "completed");
        telemetry.update();
    }

    //============================= Drivetrain =====================================================

    public void startMotors(double l, double r){
        FR.setPower(r);
        MR.setPower(r);
        BR.setPower(r);
        FL.setPower(l);
        ML.setPower(l);
        BL.setPower(l);
    }

    public void stopMotors(){
        FR.setPower(0);
        MR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        ML.setPower(0);
        BL.setPower(0);
    }



    public float deadstick (float value){

        if (value > -0.1 && value < 0.1)
            return 0 ;
        else
            return value;
    }

    //============================= Intake =========================================================

    public void pivotCross() {
        //pivot1.setPosition(.45);
        //pivot2.setPosition(.55);
    }

    public void pivotDown() {
        //pivot1.setPosition(.4);
        //pivot2.setPosition(.6);
    }

    public void pivotUp() {
        //pivot1.setPosition(0);
        //pivot2.setPosition(1);
    }



    //============================= Lift ===========================================================

    public void setLift(double power){
        lift.setPower(power);
    }

    public int getLiftEncoder(){
        return (Math.abs(lift.getCurrentPosition()));
    }

    public void resetLiftEncoder(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void zero() {
        while (getLiftEncoder() > 100) {
            lift.setPower(-.5);
        }
        resetLiftEncoder();
    }

    public void macro(double macroHeight) {
        if (macroHeight == 1) {
            //lift.setTargetPosition(2);
        }
        else if (macroHeight == 2) {
            //lift.setTargetPosition(3);
        }
        else {
            //lift.setTargetPosition(4);x
        }
    }

    public void hatchUp() {
        hatch.setPosition(.82);
    }

    public void hatchDown() {
        hatch.setPosition(.55);
    }


}
