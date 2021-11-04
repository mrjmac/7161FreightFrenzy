package NightfallOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TankTeleOp", group = "opMode")
public class TankTeleOp extends NightfallOpMode {

    double speedControl;
    double macroHeight = 3;
    boolean manual = true;



    public void loop() {
        //================================= DRIVE ==================================================
        //speed constant allows driver 1 to scale the speed of the robot
        //servo on lift side goes from 0 to 1; esrvo on non lift side goes from 1 to 0
        if (gamepad1.right_trigger > 0.1) {
            speedControl = .4;
        }
        else {
            speedControl = 1;
        }

        double left = 0;
        double right = 0;
        double max;

        double forward = deadstick ((gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y)));
        double side= deadstick (gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x));

        left = forward - side;
        right = forward + side;

        max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0) {
            left /= max;
            right /= max;
        }

        startMotors(left * speedControl, right * speedControl);

        telemetry.addData("gamepad2 left stick:", gamepad2.left_stick_y);
        telemetry.addData("manual:", manual);
        telemetry.addData("macroHeight:", macroHeight);
        telemetry.addData("lift height", getLiftEncoder());
        telemetry.addData("ducks on?", gamepad2.right_bumper);
        telemetry.addData("ducks reverse?", gamepad2.left_bumper);
        telemetry.addData("hatch open", gamepad2.y);
        telemetry.addData("hatch closed", gamepad2.x);
        telemetry.addData("leftmotor:", FL.getCurrentPosition());
        telemetry.addData("right motor:", FR.getCurrentPosition());
        telemetry.update();

        //================================= INTAKE =================================================

        if (gamepad1.right_bumper) {
            pivotCross();
            intake.setPower(-1);
        } else if (gamepad1.left_bumper) {
            intake.setPower(0);
        } else if (gamepad1.left_bumper && gamepad1.right_bumper) {
            intake.setPower(1);
        }



        //================================= DUCKS ==================================================
        if (gamepad2.right_bumper) {
            duckR.setPower(.8);
            duckL.setPower(.8);
        } else if (gamepad2.left_bumper) {
            duckR.setPower(-.8);
            duckL.setPower(-.8 );
        } else {
            duckR.setPower(0);
            duckL.setPower(0);

        }


        //================================= LIFT ===================================================
    /*    if (gamepad2.x && !manual) {
            while (gamepad2.x);
                manual = true;
        } else if (gamepad2.x && manual) {
            while (gamepad2.x);
                manual = false;
        }
*/
        if (!manual) {
            if (gamepad2.dpad_up && macroHeight < 4) {
                while (gamepad2.dpad_up);
                    macroHeight += 1;
            } else if (gamepad2.dpad_down && macroHeight > 0) {
                while (gamepad2.dpad_down);
                    macroHeight -= 1;
            }

            if (gamepad2.a) {
                macro(macroHeight);
            }
            if (gamepad2.b) {
                zero();
            }
        } else {
            lift.setPower(deadstick(gamepad2.left_stick_y) + .03);
        }

        if (gamepad2.y) {
            hatchUp();
        }
        else if (gamepad2.x) {
            hatchDown();
        }
    }
}
