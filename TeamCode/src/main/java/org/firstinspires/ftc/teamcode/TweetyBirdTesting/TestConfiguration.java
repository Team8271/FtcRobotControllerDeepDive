package org.firstinspires.ftc.teamcode.TweetyBirdTesting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.narlyx.tweetybird.Drivers.Mecanum;
import dev.narlyx.tweetybird.Odometers.ThreeWheeled;
import dev.narlyx.tweetybird.TweetyBird;

public class TestConfiguration {
    private final LinearOpMode opMode;
    public TestConfiguration(LinearOpMode opMode){this.opMode = opMode;}

    ///Defining parts of robot
    //Define DcMotors
    public DcMotor fl,fr,bl,br;

    ///Defining TweetyBird things
    public ThreeWheeled odometer;
    public Mecanum mecanum;
    public TweetyBird tweetyBird;

    ///Quick Change Values
    public double maxSpeed = 0.4,
                  minSpeed = 0.25;


    ///Robot Initialization Sequence
    public void init(){
        //Define the hardware map for accessing devices
        HardwareMap hwMap = opMode.hardwareMap;

        ///Define Motors
        //Front Left Motor
        fl = hwMap.get(DcMotor.class, "FL");            //Name in DS
        fl.setDirection(DcMotor.Direction.REVERSE);                //Motor Direction
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);        //Reset the Encoder
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);  //Set Brake Mode

        //Front Right Motor
        fr = hwMap.get(DcMotor.class, "FR");            //Name in DS
        fr.setDirection(DcMotorSimple.Direction.FORWARD);          //Motor Direction
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);        //Reset the Encoder
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);  //Set Brake Mode

        //Back Left Motor
        bl = hwMap.get(DcMotor.class, "BL");            //Name in DS
        bl.setDirection(DcMotor.Direction.REVERSE);                //Motor Direction
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);        //Reset the Encoder
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);  //Set Brake Mode

        //Back Right Motor
        br = hwMap.get(DcMotor.class, "BR");            //Name in DS
        br.setDirection(DcMotor.Direction.FORWARD);                //Motor Direction
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);        //Reset the Encoder
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);  //Set Brake Mode


        mecanum = new Mecanum.Builder()
                .setFrontLeftMotor(fl)
                .setFrontRightMotor(fr)
                .setBackLeftMotor(bl)
                .setBackRightMotor(br)
                .build();

        odometer = new ThreeWheeled.Builder()
                .setLeftEncoder(bl)
                .setRightEncoder(fr)
                .setMiddleEncoder(br)

                .setEncoderTicksPerRotation(2000)
                .setEncoderWheelRadius(0.944882)

                //Change the true/false values to correct directions
                .setFlipLeftEncoder(true)
                .setFlipRightEncoder(true)
                .setFlipMiddleEncoder(true)

                .setSideEncoderDistance(12)
                .setMiddleEncoderOffset(9.75)
                .build();
        odometer.resetTo(0,0,0);

        opMode.telemetry.addLine("Main Robot Initialization Completed!");
        opMode.telemetry.update();
    }

    ///Initialization Sequence for TweetyBird
    public void initTweetyBird(){
        odometer.resetTo(0,0,0);
        tweetyBird = new TweetyBird.Builder()
                // Your configuration options here
                .setDistanceBuffer(1) //inches
                .setDriver(mecanum)
                .setLinearOpMode(opMode)
                .setMaximumSpeed(maxSpeed)
                .setMinimumSpeed(minSpeed)
                .setOdometer(odometer)
                .setRotationBuffer(3)
                .setLoggingEnabled(true)
                .build();

        opMode.telemetry.addLine("TweetyBird Initialization Completed!");
        opMode.telemetry.update();
    }

}
