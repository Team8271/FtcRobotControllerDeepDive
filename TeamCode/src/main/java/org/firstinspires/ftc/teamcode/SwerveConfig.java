package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SwerveConfig {
    private final LinearOpMode opMode;
    
    public DcMotor flMotor,frMotor,blMotor,brMotor;
    public Servo flServo,frServo,blServo,brServo;

    public SwerveConfig(LinearOpMode opMode){this.opMode = opMode;}

    public void init(){
        //Used to define everything
        HardwareMap hwMap = opMode.hardwareMap;
        ///Define Motors
        //Front Left
        flMotor = hwMap.get(DcMotor.class, "FL");
        flMotor.setDirection(DcMotor.Direction.FORWARD);
        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Front Right
        frMotor = hwMap.get(DcMotor.class, "FR");
        flMotor.setDirection(DcMotor.Direction.FORWARD);
        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Back Left
        blMotor = hwMap.get(DcMotor.class, "FL");
        blMotor.setDirection(DcMotor.Direction.FORWARD);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Back Right
        brMotor = hwMap.get(DcMotor.class, "FL");
        brMotor.setDirection(DcMotor.Direction.FORWARD);
        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ///Define Servos
        flServo = hwMap.get(Servo.class, "FLServo");
        frServo = hwMap.get(Servo.class, "FRServo");
        blServo = hwMap.get(Servo.class, "BLServo");
        brServo = hwMap.get(Servo.class, "BRServo");
    }
}
