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

    //add motors variables
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
        drive_left = hardwareMap.dcMotor.get("drive_left");
        drive_right = hardwareMap.dcMotor.get("drive_right");
        table = hardwareMap.dcMotor.get("table");
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
    public void motor_run(DcMotor motor, Double motor_power, Double motor_coef) {
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



    }
}
