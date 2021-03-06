package NightfallLinearOpMode.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NightfallLinearOpMode.Intake;
import NightfallLinearOpMode.Drivetrain;
import NightfallLinearOpMode.Lift;
import NightfallLinearOpMode.Vision;

@Autonomous(name = "Blue Duck", group = "blue")
public class Duck extends LinearOpMode {

    private Drivetrain drivetrain;
    private Vision vision;
    private Lift lift;
    private Intake intake;
    private int pos;

    public static double kpTurn5 = .670; //PID
    public static double kdTurn5 = 0.03; //PID
    public static double kpTurn45 = 0.119;
    public static double kdTurn45 = 0.49;
    public static double timeoutTurn = 1.5;
    public static double kpTurn90 = 0.09;
    public static double kdTurn90 = 0.31;
    public static double kpTurn180 = 0.06;
    public static double kdTurn180 = 0.25;

    private int liftHeight = 3;

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
        telemetry.addData("team marker pos: ", pos);
        telemetry.update();

        //trajectories/pose go here

        idle();
        liftHeight = pos;

        // PICK UP CAP AND SCORE PRE LOADED(red)
        drivetrain.gyroEncoderInch(.5, 38, 2, 0);
        drivetrain.gyroEncoderInch(-1, 8, 2,0);
        lift.capDown();
        //intake.intakeDown();
        sleep(300);
        lift.capUp();
        drivetrain.turnPD(-50, kpTurn45, kdTurn45, 1.5);
        drivetrain.gyroEncoderInch(1, 8, 2, -50);
        lift.setLift(liftHeight, 1);
        // CAROUSEL, PICK UP DUCK AND SCORE
        drivetrain.gyroEncoderInch(-.5, 39, 2.7, -65);
        drivetrain.duckStart(-1);
        sleep(3750);
        //  drivetrain.duckStart(-.2);
        //sleep(800);
        intake.goatIntake(.5);
        drivetrain.duckStop();
        drivetrain.gyroEncoderInch(1, 12, 1.3, -60);
        //       drivetrain.turnPD(-15, kpTurn45, kdTurn45, 4);
        //   intake.goatIntake(.5);
        //     drivetrain.gyroEncoderInch(-1, 12, 1.3, -15);

        //   intake.goatIntake(.5);
//        drivetrain.gyroEncoderInch(1, 12, 1.3, -15);
        drivetrain.turnPD(25, kpTurn45, kdTurn45, 3);
        drivetrain.gyroEncoderInch(-1, 12, 1.3, 25);
        //   intake.goatIntake(0);
        //      intake.goatIntake(.5);
        sleep(250);
        drivetrain.gyroEncoderInch(1, 12, 1.3, 25);
        intake.goatIntake(0);
        drivetrain.turnPD(-60, kpTurn90, kdTurn90, 2.3);
        drivetrain.gyroEncoderInch(1, 24, 1.6, -60);
        lift.setLift(3, 1);
        drivetrain.gyroEncoderInch(-1, 5, 1.1, -60);
        drivetrain.turnPD(-90, kpTurn45, kdTurn45,2);
        drivetrain.gyroEncoderInch(-1, 30, 1.4, -90);

     /*   drivetrain.turnPD(0, kpTurn45, kdTurn45, 1.5);
        drivetrain.gyroEncoderInch(.5, 26, 3, 0);
*/



    }
}
