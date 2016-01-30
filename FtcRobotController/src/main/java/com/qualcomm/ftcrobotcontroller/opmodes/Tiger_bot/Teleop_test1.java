/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes.Tiger_bot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class Teleop_test1 extends OpMode {

    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();
   double rightpower = 0;
    double leftpower = 0;
    double gear1 = 1;
    double gear2 = .75;
    double gear3 = .5;
    //add motors variables
    double driveratio = 1;
    DcMotor drive_left;
    DcMotor drive_right;
    DcMotor table;
    DcMotor scissor;
    DcMotor intake;
    DcMotor spitch;

    //add servos
    Servo boxflap;

    //@Override
    public void init() {
        //retrieve motors from hardware ma
        drive_left = hardwareMap.dcMotor.get("leftmotor");
        drive_right = hardwareMap.dcMotor.get("rightmotor");
        table = hardwareMap.dcMotor.get("turntable");
        scissor = hardwareMap.dcMotor.get("scissor");
        intake = hardwareMap.dcMotor.get("intake");
        spitch = hardwareMap.dcMotor.get("spitch");

        //get servos from hardware map
        boxflap = hardwareMap.servo.get("boxflap");

        //set motors channel mode
        drive_left.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        drive_right.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        table.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        scissor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        intake.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        spitch.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //reset all encoders
        drive_left.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        drive_right.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        table.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        scissor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        intake.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        spitch.setMode(DcMotorController.RunMode.RESET_ENCODERS);



    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    public void runmotorwithlimmit(int max, int min, DcMotor motor1, boolean forward_condition, boolean back_condition, double fpower, double rpower, double stoppower)
    {
        // This function runs a motor and makes sure that it's current position is within a specified range using encoders
        // Params:   max(type: integer, function: set max encoder value that motor can move to)
        // min(type: integer, function: set min encoder value that motor can move to)
        //  motor1(type: DcMotor, function: set the motor that will run)
        // forward_condition(type: boolean, function: if this condition is true the power of motor 1 will be set to fpower)
        // back_condition(type: boolean, function: if this condition is true the power of motor 1 will be set to rpower)
        // fpower(type: double, function: power to set motor one to when forward_condition is true)
        // rpower(type: double, function: power to set motor one to when back_condition is true)
        // stoppower(type: double, function: power to set motor one to when both forward_condition and back_condition are true)
        if (forward_condition) {
            if(scissor.getCurrentPosition() < max) { // Keep within range
                motor1.setPower(fpower);  // if the dpad up button is pressed move the scissor up
            }
        }
        if (back_condition) {
            if(scissor.getCurrentPosition() > min) { // Keep within range
                motor1.setPower(rpower);// if the dpad down button is pressed move the scissor down
            }
        }
        if (!forward_condition && !back_condition) {
            motor1.setPower(stoppower); // if no dpad buttons are pressed set turn off the scissor motor
        }

    }
    @Override
    public void init_loop() {
        startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        runtime.reset();
        telemetry.addData("Null Op Init Loop", runtime.toString());

    }

    /*class to run any motor
    Argument 1 is motor
    Argument 2 is motor drive power
    Argument 3 is coefficient to apply to motor power
    Everything is clipped to prevent out of range errors
     */
    public void motor_run(DcMotor motor, double motor_power, Double motor_coef) {
        motor_power = Range.clip(-1, 1, motor_power);
        motor_power = motor_power*motor_coef;
        motor.setPower(motor_power);

    }

    /*class to run any servo
    Argument 1 is servo
    Argument 2 is servo position in degrees
    The variable servo_deg is converted from degrees into -1 to 1 by a coefficient 1/180.
    This value is then clipped to prevent out of range errors
     */
    public void servo_set(Servo servo, Double servo_deg) {
        servo_deg = (1/180)*servo_deg;
        servo_deg = Range.clip(-1,1, servo_deg);
        servo.setPosition(servo_deg);
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        telemetry.addData("1 Start", "NullOp started at " + startDate);
        telemetry.addData("2 Status", "running for " + runtime.toString());
        rightpower = gamepad1.right_stick_y;
        leftpower = gamepad1.left_stick_y;
        motor_run(drive_right, rightpower, driveratio);
        motor_run(drive_left, leftpower, driveratio);
        //use dpad buttons  to controll motor speeds
        if(gamepad1.dpad_up){driveratio=gear1;}
        if(gamepad1.dpad_down){driveratio=gear1;}
        if(gamepad1.dpad_left){driveratio=gear2;}
        if(gamepad1.dpad_right){driveratio=gear3;}
        //right and left bumpers control turntable, gampad right stick controls the scissor lift length
        //and left stick controls scissor lift pitch A button controls intake
        if (gamepad2.right_bumper)
        {
            motor_run(table, 1, (double) 1);
        }
        if (gamepad2.left_bumper)
        {
            motor_run(table, -1, (double) 1);
        }
        if (gamepad2.right_stick_y > 0 || gamepad2.right_stick_y < 0)
        {
            motor_run(scissor, gamepad2.right_stick_y, (double) 1);
        }
        if (gamepad2.left_stick_y > 0 || gamepad2.left_stick_y < 0)
        {
            motor_run(spitch, gamepad1.right_stick_y, (double) 1);
        }
        if (gamepad2.a)
        {
            motor_run(intake, 1, (double) 1);
        }

    }
}
