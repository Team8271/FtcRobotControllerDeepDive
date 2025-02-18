package org.firstinspires.ftc.teamcode.SwerveDev;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="SwerveTeleOp")
public class SwerveTeleOp extends LinearOpMode {
  @Override
  public void runOpMode(){
    SwerveConfig robot = new SwerveConfig(this);
    robot.init();

    waitForStart();

    while(opModeIsActive()){
      ///Driver One Controls
      double axialControl = -gamepad1.left_stick_y;  //Up down
      double lateralControl = gamepad1.left_stick_x; //Left Right
      double yawControl = gamepad1.right_stick_x;
      double mainThrottle = .2+(gamepad1.right_trigger*0.8);
      boolean resetFCD = gamepad1.dpad_up;

      //Need to set servo position based on joysticks
      //Power also needs to be set through joysticks / throttle

      double gamepadRadians = Math.atan2(lateralControl, axialControl);
      double gamepadHypot = Range.clip(Math.hypot(lateralControl, axialControl), 0, 1);
      double robotRadians = -robot.odometer.getZ();
      double targetRadians = gamepadRadians + robotRadians;
      double lateral = Math.sin(targetRadians)*gamepadHypot;
      double axial = Math.cos(targetRadians)*gamepadHypot;

      telemetry.addLine("Lateral: " + lateral);
      telemetry.addLine("Axial: " + axial); //up down
      telemetry.update();

      /* Servo position is going to be:
       * more left/right based on lateral
       * more forward/back based on axial
       * add rotation with yaw control
       */

      






    }
  }

  //Method to set Servo position to desired position with minimal rotation
  public void setServoPosition(Servo servo, int position){
    //If the distance to position is best achieved by 0-180 range (rather than 180-360)
    if(Math.abs(servo.getPosition()-position) <= Math.abs(servo.getPosition()-***)){

    }
  }

  //Method to get opposite rotation of servo so that the wheel still faces same direction (90 - 270)
  public int degreeOpposite(int degree){
    
  }

}
