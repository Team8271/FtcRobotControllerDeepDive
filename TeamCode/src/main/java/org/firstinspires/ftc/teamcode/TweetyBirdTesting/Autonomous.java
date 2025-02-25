package org.firstinspires.ftc.teamcode.TweetyBirdTesting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.*;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous")
public class Autonomous extends LinearOpMode {
    TestConfiguration robot;
    int currentSelection = 0;

    //Make a hashmap and grab value for key 0-1
    //noinspection MismatchedQueryAndUpdateOfCollection
    HashMap<Integer, String> autoPrograms = new HashMap<Integer, String>();


    @Override
    public void runOpMode(){
        //Allow telemetry from initializations to pile up
        telemetry.setAutoClear(false);
        boolean debounce = true;

        autoPrograms.put(0, "Around the Field");
        autoPrograms.put(1, "Around the Field Engage/Disengage");

        robot = new TestConfiguration(this);
        robot.init();
        robot.initTweetyBird();

        sleep(500); //Allow time to read init telemetry

        while(opModeInInit()){
            telemetry.addLine("Use X to select next option\n" +
                    "Use A to lock in option\n" + autoPrograms.get(currentSelection) +
                    "debug: currentSelection = " + currentSelection);
            telemetry.update();

            if(gamepad1.x || gamepad2.x && !debounce){
                debounce = true;
                nextSelection();
            }
            if(!gamepad1.x && !gamepad2.x && debounce){
                debounce = false;
            }
            if(gamepad1.a){
                break;
            }
        }

        waitForStart();


        //Clear and set auto clear for telemetry
        telemetry.clearAll();
        telemetry.setAutoClear(true);


        if(currentSelection == 0){
            aroundTheFieldAuto();
        }
        if(currentSelection == 1){
            aroundTheFieldEngageAndDisengage();
        }

        robot.tweetyBird.close();
    }

    /**
     * Uses shared int currentSelection and Hashmap autoPrograms
     * to determine which way to increment currentSelection.
     */
    public void nextSelection(){
        if(currentSelection < autoPrograms.size()){
            currentSelection++;
        }
        else{
            currentSelection--;
        }
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
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-24,96+6,0); //To upper right corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-96-6,96+6,0); //To upper left corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-96-6,24,0); //To bottom left corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-24,24,0); //To bottom right corner of submersible
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.addWaypoint(-12,12,0); //Go near Observation corner
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
