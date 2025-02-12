

@TeleOp(name="SwerveTeleOp")
public class SwerveTeleOp extends LinearOpMode {
  @Override
  public void runOpMode(){
    SwerveConfig robot = new SwerveConfig(this);
    robot.init();

    waitForStart();

    while(opModeIsActive()){
      ///Driver One Controls
      double axialControl = -gamepad1.left_stick_y;
      double lateralControl = gamepad1.left_stick_x;
      double yawControl = gamepad1.right_stick_x;
      double mainThrottle = .2+(gamepad1.right_trigger*0.8);
      boolean resetFCD = gamepad1.dpad_up;

      //Do some cool stuff
    }
  }
}
