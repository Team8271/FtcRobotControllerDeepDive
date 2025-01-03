package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.HashMap;

@TeleOp(name="Jax TeleOp")
public class teleOp extends LinearOpMode {
    private Configuration robot;


    //Main OpMode
    @Override
    public void runOpMode() {
        robot = new Configuration(this);
        robot.init(false);
        PIDControl thread1 = new PIDControl(this);


        telemetry.addLine("Initialized");

        waitForStart();

        telemetry.clearAll();

        //
        // thread1.setVerticalTargetPosition(0);
        //thread1.start();
        while (opModeIsActive()){
                //Driver 1
                double axialControl = -gamepad1.left_stick_y;
                double lateralControl = gamepad1.left_stick_x;
                double yawControl = gamepad1.right_stick_x;
                double mainThrottle = .2+(gamepad1.right_trigger*0.8);
                boolean resetFCD = gamepad1.dpad_up;

                //Driver 2
                double horzControl = gamepad2.right_stick_x;
                double vertControl = -gamepad2.left_stick_y;
                boolean boxControl = gamepad2.left_trigger >.25;
                boolean reverseIntake = gamepad2.right_trigger>.25;


                if(gamepad2.a){
                    robot.closeClaw();
                }
                else if(gamepad2.b){
                    robot.openClaw();
                }



                //Drivetrain
                double axial = axialControl;
                double lateral = lateralControl;

                if (resetFCD){
                    robot.odometer.resetTo(0,0,0);
                }


                double gamepadRadians = Math.atan2(lateralControl, axialControl);
                double gamepadHypot = Range.clip(Math.hypot(lateralControl, axialControl), 0, 1);
                double robotRadians = robot.odometer.getZ();
                double targetRadians = gamepadRadians + robotRadians;
                lateral = Math.sin(targetRadians)*gamepadHypot;
                axial = Math.cos(targetRadians)*gamepadHypot;

                telemetry.addData("getX", robot.odometer.getX());
                telemetry.addData("getY", robot.odometer.getY());
                telemetry.addData("getZ", robot.odometer.getZ());


                telemetry.addData("gamepadRadians",String.valueOf(gamepadRadians));
                telemetry.addData("gamepadHypot",String.valueOf(gamepadHypot));
                telemetry.addData("robotRadians",String.valueOf(robotRadians));
                telemetry.addData("targetRadians",String.valueOf(targetRadians));



                double leftFrontPower = axial + lateral + yawControl;
                double rightFrontPower = axial - lateral - yawControl;
                double leftBackPower = axial - lateral + yawControl;
                double rightBackPower = axial + lateral - yawControl;


                robot.fl.setPower(leftFrontPower * mainThrottle);
                robot.fr.setPower(rightFrontPower * mainThrottle);
                robot.bl.setPower(leftBackPower * mainThrottle);
                robot.br.setPower(rightBackPower * mainThrottle);

                telemetry.addLine();





                //Horizontal Slide
                telemetry.addData("Horizontal Slide Pos",robot.horizontalMotor.getCurrentPosition());
                telemetry.addData("Horizontal Slide Limiter",robot.horizontalLimiter.isPressed());

                if(robot.horizontalLimiter.isPressed()) { // Slide bottomed out
                    if (horzControl<0) {
                        horzControl = 0;
                    }
                    robot.horizontalMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    telemetry.addLine("Horizontal slide bottomed out!");
                }
                else if(robot.horizontalMotor.getCurrentPosition()>=robot.horzMax) { // Slide topped out
                    if(horzControl>0){
                        horzControl = 0;
                    }
                }


                if(horzControl != 0){ //Moving
                    if(robot.horizontalMotor.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER){
                        robot.horizontalMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    }
                    if(horzControl < 0){
                        robot.horizontalMotor.setPower(horzControl*.4);
                    }
                    else{
                        robot.horizontalMotor.setPower(horzControl);
                    }
                    telemetry.addLine("Horizontal Slide moving..");
                    telemetry.addData("horzControl:", horzControl);

                }
                else { //stop and hold
                    if(robot.horizontalMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION){
                        int targetPosToHold = robot.horizontalMotor.getCurrentPosition();
                        if (targetPosToHold>robot.horzMax){
                            targetPosToHold = 0;
                        }
                        robot.horizontalMotor.setTargetPosition(targetPosToHold);
                        robot.horizontalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        robot.horizontalMotor.setPower(0.5);
                    }
                    telemetry.addLine("Horizontal Slide holding..");
                }

                telemetry.addLine();

                // Intake
                if (robot.verticalMotor.getCurrentPosition()<=15 && //Retracted
                        robot.horizontalMotor.getCurrentPosition()<=15) {
                    robot.flipServo.setPosition(.9);
                    robot.intakeMotor.setPower(0);
                    if(reverseIntake){//reverseIntake?-1:1 was here
                        robot.intakeMotor.setPower(-0.8);

                    }
                    telemetry.addLine("Intake retracted");
                }
                else if(robot.horizontalMotor.getCurrentPosition()>=robot.intakeOnDistance){ // Extended
                    robot.flipServo.setPosition(0.07);
                    robot.intakeMotor.setPower(1);//reverseIntake?-1:1 was here
                    if(reverseIntake){//reverseIntake?-1:1 was here
                        robot.intakeMotor.setPower(-0.8);
                    }
                    telemetry.addLine("Intake Extended");
                }
                else { //Gray zone
                    robot.flipServo.setPosition(.6);
                    robot.intakeMotor.setPower(0); //reverseIntake?-1:.5   //aslo 0.5 power
                    if(reverseIntake){//reverseIntake?-1:1 was here
                        robot.intakeMotor.setPower(-0.8);
                    }
                    telemetry.addLine("Intake waiting");
                }
                telemetry.addLine();

                //Vertical Slide
                telemetry.addData("Vertical Slide Position",robot.verticalMotor.getCurrentPosition());
                telemetry.addData("Vertical Slide Limiter",robot.verticalLimiter.isPressed());

                if(robot.verticalLimiter.isPressed()){ //slide bottomed out
                    if(vertControl<0){
                        vertControl = 0;
                    }
                    robot.verticalMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    telemetry.addLine("Vertical slide bottomed out");
                }
                else if(robot.verticalMotor.getCurrentPosition()>=robot.vertMax){ // Slide topped out
                    if(vertControl>0){
                        vertControl = 0;
                    }
                    telemetry.addLine("Vertical slide topped out");
                }

                if(vertControl != 0) { //Moving
                    if(robot.verticalMotor.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER){
                        robot.verticalMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    }
                    robot.verticalMotor.setPower(vertControl);
                    telemetry.addLine("Vertical slide moving");
                }
                else { //Stop and hold
                    if(robot.verticalMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION){
                        int targetPosToHold = robot.verticalMotor.getCurrentPosition();
                        if(targetPosToHold>robot.vertMax){
                            targetPosToHold = robot.vertMax;
                        }
                        robot.verticalMotor.setTargetPosition(targetPosToHold);
                        robot.verticalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        robot.verticalMotor.setPower(.5);
                    }
                    telemetry.addLine("Vertical slide holding...");

                    if(gamepad2.dpad_down && !robot.verticalLimiter.isPressed()){
                        robot.verticalMotor.setTargetPosition(0);
                        robot.verticalMotor.setPower(0.6);
                    }

                    if(gamepad2.dpad_right){
                        robot.verticalMotor.setTargetPosition(robot.vertWall);
                        robot.verticalMotor.setPower(0.6);
                    }

                    if(gamepad2.dpad_up){
                        robot.verticalMotor.setTargetPosition(robot.vertAboveChamber);
                        robot.verticalMotor.setPower(0.6);
                    }
                }

                telemetry.addLine();


                //Box
                if(boxControl && robot.verticalMotor.getCurrentPosition()>=100){
                    robot.boxServo.setPosition(-.99);
                    telemetry.addLine("Box flipped");
                }
                else{
                    robot.boxServo.setPosition(.6);
                    telemetry.addLine("Box retracted");
                }

                //Telemetry
                telemetry.update();
        }
    }
}
