package org.firstinspires.ftc.teamcode.TweetyBirdTesting;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous")
public class Autonomous extends LinearOpMode {

    private static final Logger log = LoggerFactory.getLogger(Autonomous.class);
    // Log file writer
    protected BufferedWriter logWriter = null;

    public LinearOpMode autoOpMode;

    @Override
    public void runOpMode(){
        TestConfiguration robot = new TestConfiguration(this);
        robot.init();
        robot.initTweetyBird();

        autoOpMode = this;

        waitForStart();


        while(opModeIsActive()) {

            //Have TweetyBird run a simple path
            //Have a button to disengage and reengage tweetybird
            //Output when path started and when it ended ie:
            //"(PathNum) X,Y,Z Start: runtime"
            //"(PathNum) X,Y,Z End:   runtime"
            //"(PathNum) Time to complete: start-end"
            //"(DisengageNum) Disengaged!"
            //"(EngageNum) Engaged!"
            robot.tweetyBird.addWaypoint(10,0,0);
            log("Moving to: 10,0,0");
            while(robot.tweetyBird.isBusy() || !robot.tweetyBird.isEngaged()){
                if(gamepad1.a){
                    if(!robot.tweetyBird.isEngaged()){
                        robot.tweetyBird.engage();
                    }

                }
            }

            robot.tweetyBird.addWaypoint(-10,0,0);
            while(robot.tweetyBird.isBusy() || !robot.tweetyBird.isEngaged()){
                if(gamepad1.a){
                    if(!robot.tweetyBird.isEngaged()){
                        robot.tweetyBird.engage();
                    }

                }
            }

        }




    }

    // Setting up log file
    public void setUpLog(){
        // Setting up log file
        String logFileName = "myLogFile.txt";
        File logFile;
        if (autoOpMode != null) {
            File logDirectory = Environment.getExternalStorageDirectory();
            logFile = new File(logDirectory, logFileName);
        } else {
            logFile = new File(logFileName);
        }
        try {
            logWriter = new BufferedWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            log("Failed to initialize to logWriter "+e);
        }

        // Done
        log("Initial setup complete!\n");
    }

    /**
     * Internal method used to send debug messages
     * @param message message to be sent
     */
    protected void log(String message) {
        // Getting current time
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss.SSS");
        String date = sdf.format(now);

        // Processing string
        String outputString = "["+date+" MyLog]: "+message;

        // Logfile
        if (logWriter != null) {
            try {
                logWriter.write(outputString);
                logWriter.newLine();
            } catch (IOException e) {}
        }

    }

    public void close() {
        log("close called. Shutting down logWriter.");
        try {
            if (logWriter != null) {
                logWriter.flush();
                logWriter.close();
            }
        } catch (IOException e) {
            log("Failed to shutdown logWriter");
        }
    }
}
