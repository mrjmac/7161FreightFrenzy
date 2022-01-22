package NightfallLinearOpMode.Red;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NightfallLinearOpMode.Intake;
import NightfallLinearOpMode.Drivetrain;
import NightfallLinearOpMode.Lift;
import NightfallLinearOpMode.Vision;

@Autonomous(name = "Red Duck", group = "red")
public class Duck extends LinearOpMode {

    private Drivetrain drivetrain;
    private Vision vision;
    private Lift lift;
    private Intake intake;
    private int pos;

    public static double kpTurn5 = 0.2386; //PID
    public static double kdTurn5 = 0.1; //PID
    public static double kpTurn45 = 0.405;
    public static double kdTurn45 = 0.05;
    public static double timeoutTurn = 1.5;
    public static double kpTurn90 = 0.176;
    public static double kdTurn90 = 0.07;
    public static double kpTurn180 = 0.129;
    public static double kdTurn180 = 0.00;

    private int liftHeight = 3;
    //state machine
    //start pose

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        vision = new Vision(this);
        lift = new Lift(this);
        intake = new Intake(this);

        while (!isStarted()) {
            pos = vision.getPosNewMethod();
            telemetry.addData("team marker pos: ", pos);
            telemetry.update();
        }

        //trajectories/pose go here

        idle();
        liftHeight = pos;

        // PICK UP CAP AND SCORE PRE LOADED
        drivetrain.gyroEncoderInch(1, 18, 2, 0);
        lift.capDown();
        //intake.intakeDown();
        sleep(300);
        lift.capUp();
        drivetrain.turnPD(50, kpTurn45, kdTurn45, 1.5);
        drivetrain.gyroEncoderInch(1, 16, 2, 50);
        lift.setLift(liftHeight, 1);
        // CAROUSEL, PICK UP DUCK AND SCORE
        drivetrain.gyroEncoderInch(-.5, 45.5, 3, 60);
        drivetrain.duckStart(-1);
        sleep(5000);
        drivetrain.duckStop();
        drivetrain.gyroEncoderInch(.5, 60, 3, 77);
        drivetrain.turnPD(90, kpTurn5, kdTurn5, 2);
        drivetrain.gyroEncoderInch(.85, 53, 3, 90);
        intake.intake.setPower(.2);
        sleep(500);
        intake.intake.setPower(0);
        /*
        //intake.intakePow(1);
        drivetrain.gyroEncoderInch(0.8, 12, 1.2, 60);
        drivetrain.turnPD(-30, kpTurn90, kdTurn90, 1.5);
        drivetrain.gyroEncoderInch(-1, 8, 3, -22);
        //intake.intakeStop();
        drivetrain.turnPD(50, kpTurn90+.03, 0.004, 1.5);
        drivetrain.gyroEncoderInch(1, 30, 3, 50);
        sleep(500);
        lift.setLift(3, 1);
        // ARCTURN TOWARDS ALLIANCE PRELOAD AND SCORE
     /*   drivetrain.gyroEncoderInch(-.5, 8, 1.5, 45);
        drivetrain.turnPD(0, kpTurn45, kdTurn45, 1.5);
        intake.intakePow(1);
        drivetrain.arcTurnPD(-90, kpTurn90, kdTurn90, 3);
        sleep(500);
        drivetrain.turnPD(-25, kpTurn90, kdTurn90, 1.5);
        intake.intakeStop();
        drivetrain.gyroEncoderInch(1, 35, 3, -25);
        lift.setLift(3, 1);
        //comment goes here
        // PARK INSIDE CRATER
        drivetrain.gyroEncoderInch(-.5, 12, 1, 50);
        drivetrain.turnPD(-60, kpTurn180, kdTurn180, 1.5);
        drivetrain.gyroEncoderInch(1, 33, 2.5, -90);

         */







    }
}
