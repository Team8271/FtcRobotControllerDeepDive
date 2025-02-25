package org.firstinspires.ftc.teamcode.TweetyBirdTesting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.*;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous")
public class Autonomous extends LinearOpMode {
    TestConfiguration robot;
    int currentSelection = 0;
    ElapsedTime runtime;

    //Make a hashmap and grab value for key 1-2
    //noinspection MismatchedQueryAndUpdateOfCollection
    HashMap<Integer, String> autoPrograms = new HashMap<Integer, String>();


    @Override
    public void runOpMode(){
        initialize();
        autoSelector();

        waitForStart();
        telemetry.setAutoClear(true);

        //Run auto based on selection
        if(currentSelection == 0){
            telemetry.addLine("Running selection 0");
            telemetry.update();
            aroundTheFieldAuto();
        }
        if(currentSelection == 1){
            telemetry.addLine("Running selection 0");
            telemetry.update();
            aroundTheFieldEngageAndDisengage();
        }


        robot.tweetyBird.close();
    }

    /**
     * Runs Selector for autonomous programs
     */
    public void autoSelector(){
        //Add Programs to Selector
        autoPrograms.put(0, "Around the Field");
        autoPrograms.put(1, "Around the Field Engage/Disengage");

        boolean debounce = false;
        while(opModeInInit()) {
            telemetry.addLine("Use A(Cross) to select next option\n" +
                    "Use Y(Triangle) to lock in option\n" + autoPrograms.get(currentSelection) +
                    "\ndebug: currentSelection = " + currentSelection);

            if (gamepad1.a || gamepad2.a && !debounce) {
                nextSelection();
                debounce = true;
            }
            if (!gamepad1.a && !gamepad2.a && debounce) {
                debounce = false;
            }
            if (gamepad1.y || gamepad2.y) {
                break;
            }
            telemetry.addLine("\n\n\n" + runtime.toString());
            telemetry.update();
        }
        telemetry.addLine("Running " + autoPrograms.get(currentSelection));
        telemetry.update();
    }

    /**
     * All of the Initializes steps put into a method
     * Initializes robot, Tweetybird and runs Auto Selector
     */
    public void initialize(){
        //Allows init telemetry to pile on DS
        telemetry.setAutoClear(false);
        boolean debounce = true; //For Selection

        robot = new TestConfiguration(this);
        robot.init();
        robot.initTweetyBird();
        runtime = new ElapsedTime();

        telemetry.setAutoClear(true);

        telemetry.addLine("Initialization Successful!");
        telemetry.update();
    }


    /**
     * Uses shared int currentSelection and Hashmap autoPrograms
     * to determine which way to increment currentSelection.
     */
    public void nextSelection(){
        //Increment
        currentSelection++;
        //If at last selection in list
        if(currentSelection == autoPrograms.size()){
            currentSelection = 0;
        }
    }

    /**
     * Return String 'Traveling to: X,Y,Z'
     */
    public String getWaypoint(){
        return robot.tweetyBird.getCurrentWaypoint().getX() + "," +
                robot.tweetyBird.getCurrentWaypoint().getY() + "," +
                robot.tweetyBird.getCurrentWaypoint().getZ();
    }

    /**
     * An Autonomous method that goes around the field.
     * Starting Position is in the corner of Observation.
     */
    public void aroundTheFieldAuto(){
        if(!robot.tweetyBird.isEngaged()){
            robot.tweetyBird.engage();
        }
        robot.tweetyBird.setMaxSpeed(robot.maxSpeed);
        robot.tweetyBird.setMinSpeed(robot.minSpeed);

        robot.tweetyBird.addWaypoint(-24,24,0); //To bottom right corner of submersible
        telemetry.addLine("Traveling to: " + getWaypoint());
        telemetry.update();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-24,96+6,0); //To upper right corner of submersible
        telemetry.addLine("Traveling to: " + getWaypoint());
        telemetry.update();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-96-6,96+6,0); //To upper left corner of submersible
        telemetry.addLine("Traveling to: " + getWaypoint());
        telemetry.update();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-96-6,24,0); //To bottom left corner of submersible
        telemetry.addLine("Traveling to: " + getWaypoint());
        telemetry.update();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-24,24,0); //To bottom right corner of submersible
        telemetry.addLine("Traveling to: " + getWaypoint());
        telemetry.update();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-12,12,0); //Go near Observation corner
        telemetry.addLine("Traveling to: " + getWaypoint());
        telemetry.update();
        robot.tweetyBird.waitWhileBusy();

        telemetry.addLine("aroundTheFieldAuto has finished");
        telemetry.update();

    }

    public void aroundTheFieldEngageAndDisengage(){
        if(!robot.tweetyBird.isEngaged()){
            robot.tweetyBird.engage();
        }
        robot.tweetyBird.setMaxSpeed(robot.maxSpeed);
        robot.tweetyBird.setMinSpeed(robot.minSpeed);

        robot.tweetyBird.addWaypoint(-24,24,0); //To bottom right corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.disengage();
        setWheelPower(-.5,.5,.5,-.5);
        sleep(500);
        setWheelPower(0,0,0,0);

        robot.tweetyBird.engage();
        robot.tweetyBird.addWaypoint(-24,96+6,0); //To upper right corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.disengage();

        setWheelPower(-.5,.5,.5,-.5);
        sleep(500);
        setWheelPower(0,0,0,0);

        robot.tweetyBird.engage();
        robot.tweetyBird.addWaypoint(-96-6,96+6,0); //To upper left corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.disengage();

        setWheelPower(.5,-.5,-.5,.5);
        sleep(500);
        setWheelPower(0,0,0,0);

        robot.tweetyBird.engage();
        robot.tweetyBird.addWaypoint(-96-6,24,0); //To bottom left corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.disengage();

        setWheelPower(.5,-.5,-.5,.5);
        sleep(500);
        setWheelPower(0,0,0,0);

        robot.tweetyBird.engage();
        robot.tweetyBird.addWaypoint(-24,24,0); //To bottom right corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.disengage();

        setWheelPower(.5,-.5,-.5,.5);
        sleep(500);
        setWheelPower(0,0,0,0);

        robot.tweetyBird.engage();
        robot.tweetyBird.addWaypoint(-12,12,0); //Go near Observation corner
        robot.tweetyBird.waitWhileBusy();

        telemetry.addLine("aroundTheFieldAuto has finished");
        telemetry.update();

    }

    /**
     * Quick method allows easy setting of wheel power.
     * Left: -1,1,1,-1
     * Right: 1,-1,-1,1
     */
    public void setWheelPower(double fl, double fr, double bl, double br){
        robot.fl.setPower(fl);
        robot.fr.setPower(fr);
        robot.bl.setPower(bl);
        robot.br.setPower(br);
    }
}
