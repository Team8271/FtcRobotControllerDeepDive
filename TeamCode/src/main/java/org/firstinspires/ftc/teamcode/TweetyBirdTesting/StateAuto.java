package org.firstinspires.ftc.teamcode.TweetyBirdTesting;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="State Auto, Just Wheels")
public class StateAuto extends LinearOpMode {
    public ElapsedTime runTime;
    TestConfiguration robot;
    @Override
    public void runOpMode(){
        robot = new TestConfiguration(this);
        robot.init();
        robot.initTweetyBird();

        telemetry.addLine("Initialized");
        telemetry.update();

        runTime = new ElapsedTime();

        waitForStart(); //WAIT FOR USER TO PRESS START

        runTime.reset();

        ///Clip preload
        robot.tweetyBird.engage();
        moveTo(-6,29,0);
        waitForMove();
        robot.tweetyBird.clearWaypoints();
        robot.tweetyBird.disengage();

        moveAtSpeed(0.2); //Move into submersible

        sleep(1000);


        ///Pushing samples into observation
        robot.tweetyBird.engage();
        waitForMove();
        moveTo(-6,18,0); //Move back from submersible
        moveTo(28,18,-180); //Move to left/below sample 1
        waitForMove();
        //TweetyBird was consistently skipping to the line below after engage
        //First two auto run after push did this 3-4 didn't bug
        //Robot power cycled: robot bugged 2 Auto runs worked on 3rd and 4th
        //Robot restarted via DS: worked both time (Battery at 12.28V)
        //Robot power cycled: Good 1st, 2nd Fail, 3rd Good, 4th Good, 5th Good
        //Robot restarted via DS: 1st Good, 2nd Fail, 3rd Fail, 4th Fail, 5th Fail
        //Robot power cycled: 1st Fail, 2nd Fail, 3rd Good, 4th Good
        moveTo(29,42,-180); //Move to left/above sample 1
        waitForMove();
        moveTo(40,42,-180); //Move to above sample 1
        waitForMove();
        moveTo(37,11,-180); //Push sample 1 into observation
        waitForMove();
        robot.tweetyBird.clearWaypoints();
        robot.tweetyBird.disengage();
        moveAtSpeed(0.4); //Top touch isn't touching

        //flies backward
        clipCycle(0); //2nd
        //clipCycle(-2); //3rd
        //clipCycle(-4); //4th
        //clipCycle(-6); //5th

        //Sitting in observation and grabbed specimen
        robot.tweetyBird.engage();
        telemetry.addLine(robot.odometer.getX() + ", " + robot.odometer.getY() + ", " + robot.odometer.getZ());
        telemetry.update();

        moveTo(34,15,-180); //Back out of observation
        waitForMove(); //Added to prevent backing into other team observation?? Might work

        moveTo(-10,15,0); //Rotate and move to submersible
        waitForMove();
        robot.tweetyBird.clearWaypoints();
        robot.tweetyBird.disengage();
        moveAtSpeed(0.4);
        sleep(1000);


        //Robot is sitting against submersible with specimen clipped and claw open
        robot.tweetyBird.engage();
        moveTo(-10,25,0); //Back off of submersible
        waitForMove();

        moveTo(33,0,0); //Move to observation with no rotation
        waitForMove();
        robot.tweetyBird.clearWaypoints();
        robot.tweetyBird.disengage();



        robot.tweetyBird.close();
    }

    //Start in position after grabbing clip
    public void clipCycle(double offset){
        //Sitting in observation and grabbed specimen
        robot.tweetyBird.engage();
        telemetry.addLine(robot.odometer.getX() + ", " + robot.odometer.getY() + ", " + robot.odometer.getZ());
        telemetry.update();

        moveTo(34,15,-180); //Back out of observation
        waitForMove(); //Added to prevent backing into other team observation?? Might work

        moveTo(-8+offset,15,0); //Rotate and move to submersible
        waitForMove();
        robot.tweetyBird.clearWaypoints();
        robot.tweetyBird.disengage();
        moveAtSpeed(0.4);
        sleep(1000);


        //Robot is sitting against submersible with specimen clipped and claw open
        robot.tweetyBird.engage();
        moveTo(-8,25,0); //Back off of submersible
        waitForMove();

        moveTo(33,0,-180); //Move to observation with rotation
        waitForMove();
        robot.tweetyBird.clearWaypoints();
        robot.tweetyBird.disengage();
        moveAtSpeed(0.4);

        sleep(500);
    }

    public void waitForMove(){
        robot.tweetyBird.waitWhileBusy();
    }

    public void moveTo(double x, double y, double z){
        robot.tweetyBird.addWaypoint(x,y,z);
    }

    public void moveAtSpeed(double speed){
        setAllWheelPower(speed);
        sleep(700);
        setAllWheelPower(0);
    }

    public void setAllWheelPower(double power){
        robot.fl.setPower(power);
        robot.fr.setPower(power);
        robot.bl.setPower(power);
        robot.br.setPower(power);
    }

}