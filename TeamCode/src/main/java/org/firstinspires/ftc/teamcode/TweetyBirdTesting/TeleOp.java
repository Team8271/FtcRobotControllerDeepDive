package org.firstinspires.ftc.teamcode.TweetyBirdTesting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * A TeleOp program that utilises separate threads for each driver.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends LinearOpMode {
    TestConfiguration robot;

    @Override
    public void runOpMode(){
        //Initialization Start
        robot = new TestConfiguration(this);
        robot.init();
        robot.initTweetyBird();
        ElapsedTime runTime = new ElapsedTime();

        waitForStart(); //Wait for Start

        //Driver1 driverOne = new Driver1(this,robot);

        //Starting Threads
        //driverOne.start();


        while(opModeIsActive()){
            ///Driver Controls
            double axialControl = -gamepad1.left_stick_y;
            double lateralControl = gamepad1.left_stick_x;
            double yawControl = gamepad1.right_stick_x;
            double mainThrottle = .2+(gamepad1.right_trigger*0.8);
            boolean resetFCD = gamepad1.dpad_up;

            //FCD Reset
            if(resetFCD){
                robot.odometer.resetTo(0,0,0);
            }

            ///Mecanum DriveTrain Start
            double gamepadRadians = Math.atan2(lateralControl, axialControl);
            double gamepadHypot = Range.clip(Math.hypot(lateralControl, axialControl), 0, 1);
            double robotRadians = -robot.odometer.getZ();
            double targetRadians = gamepadRadians + robotRadians;
            double lateral = Math.sin(targetRadians)*gamepadHypot;
            double axial = Math.cos(targetRadians)*gamepadHypot;

            //Calculate Power
            double leftFrontPower = axial + lateral + yawControl;
            double rightFrontPower = axial - lateral - yawControl;
            double leftBackPower = axial - lateral + yawControl;
            double rightBackPower = axial + lateral - yawControl;

            //Send Power
            robot.fl.setPower(leftFrontPower * mainThrottle);
            robot.fr.setPower(rightFrontPower * mainThrottle);
            robot.bl.setPower(leftBackPower * mainThrottle);
            robot.br.setPower(rightBackPower * mainThrottle);







            ///Telemetry
            telemetry.addLine("RunTime: " + runTime.toString());
            telemetry.addLine("Robot Position: " + Math.round(robot.odometer.getX()) + ", " + Math.round(robot.odometer.getY()) + ", " + Math.round(robot.odometer.getZ()));
            telemetry.update();

        }
    }
}
