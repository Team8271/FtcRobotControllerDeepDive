package org.firstinspires.ftc.teamcode.MovementSystem.Odometer;

import com.qualcomm.robotcore.hardware.DcMotor;
/// The class that defines odometers and linked coordinate system.
public class odometer {
    DcMotor leftOdometer;
    DcMotor rightOdometer;
    DcMotor middleOdometer;
    int ticksPerRotation;
    double odometerDiameter;
    double leftRightEncoderOffset;
    double middleEncoderOffset;
    
    // Setter Methods for User Interface

    /**
     * Must be Defined. Defines the left odometer.
     * @param leftOdometer DcMotor left odometer encoder is plugged into.
     */
    public void setLeftOdometer(DcMotor leftOdometer){
        this.leftOdometer = leftOdometer;
    }
    /**
     * Must be Defined. Defines the right odometer.
     * @param rightOdometer DcMotor right odometer encoder is plugged into.
     */
    public void setRightOdometer(DcMotor rightOdometer){
        this.rightOdometer = rightOdometer;
    }
    /**
     * Must be Defined. Defines the middle odometer.
     * @param middleOdometer DcMotor middle odometer encoder is plugged into.
     */
    public void setMiddleOdometer(DcMotor middleOdometer){
        this.middleOdometer = middleOdometer;
    }
    /**
     * Must be Defined. Defines ticks per rotation.
     * @param ticksPerRotation The amount of ticks for a full rotation of the odometer wheel.
     */
    public void setTicksPerRotation(int ticksPerRotation){
        this.ticksPerRotation = ticksPerRotation;
    }
    /**
     * Must be Defined. Defines diameter of odometer wheel.
     * @param odometerDiameter Diameter of odometer wheels.
     */
    public void setOdometerDiameter(double odometerDiameter){
        this.odometerDiameter = odometerDiameter;
    }
    /**
     * Must be Defined.
     * @param leftRightEncoderOffset The distance between the left and right odometers.
     */
    public void setLeftRightEncoderOffset(double leftRightEncoderOffset){
        this.leftRightEncoderOffset = leftRightEncoderOffset;
    }
    /**
     * Must be Defined.
     * @param middleEncoderOffset Distance from robot center of rotation and middle odometer.
     */
    public void setMiddleEncoderOffset(double middleEncoderOffset){
        this.middleEncoderOffset = middleEncoderOffset;
    }
}
