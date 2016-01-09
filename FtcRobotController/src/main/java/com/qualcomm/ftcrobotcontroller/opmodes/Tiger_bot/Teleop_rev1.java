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
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */


public class Teleop_rev1 extends OpMode {

  private String startDate;
  private ElapsedTime runtime = new ElapsedTime();

  @Override
  public void init() {
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

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void loop() {
    telemetry.addData("1 Start", "NullOp started at " + startDate);
    telemetry.addData("2 Status", "running for " + runtime.toString());
  }

  /**
   * TeleOp Mode
   * <p>
   *Enables control of the robot via the gamepad
   */
  public static class Main_Robot_Teleop extends OpMode {
    //private String startDate;
   // private ElapsedTime runtime = new ElapsedTime();

    //variables are intiated
    DcMotor drive_right; // Motor contorller one Hardware map: "DR" fucntion: Controlls right drive motor
    DcMotor drive_left; // Motor contorller one Hardware map: "DL" function: Contorlls left drive motor
    DcMotor scissor; // Motor contorller two Hardware map: "scissor" function: Controlls scissor lift lenght
    DcMotor table; // Motor controller two Hardware map: "table" function: Rotates turn table
    DcMotor tpitch; // Motor controller three Hardware map: "tpitch" function: Controlls the rotation of the turn table
    DcMotor intake; // Motor controller three Hardware map: "intake" fucntion: Controlls intake motors
    float rpower;
    float lpower;
    int pos;
    @Override
    public void init() {
    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    @Override
    public void init_loop() {
      //startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
      //runtime.reset();

      //get references from hardware map.
      drive_right = hardwareMap.dcMotor.get("DR");
      drive_left = hardwareMap.dcMotor.get("DL");
      scissor = hardwareMap.dcMotor.get("scissor");
      table = hardwareMap.dcMotor.get("table");
      tpitch = hardwareMap.dcMotor.get("tpitch");
      intake = hardwareMap.dcMotor.get("intake");
      //Reset encoder
      drive_right.setMode(DcMotorController.RunMode.RESET_ENCODERS);
      drive_left.setMode(DcMotorController.RunMode.RESET_ENCODERS);
      scissor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
      table.setMode(DcMotorController.RunMode.RESET_ENCODERS);
      tpitch.setMode(DcMotorController.RunMode.RESET_ENCODERS);
      intake.setMode(DcMotorController.RunMode.RESET_ENCODERS);
      // Allow motors to use encoders
      drive_left.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
      scissor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
      table.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
      tpitch.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
      intake.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */

  public void set_drive_train()
    {
      //aquire data from gamepad
      rpower = -gamepad1.right_stick_y;
      lpower = -gamepad1.left_stick_y;
      // Make sure the data is within -1 through 1
      rpower = Range.clip(rpower, -1, 1);
      lpower = Range.clip(lpower, -1, 1);
      //Set power for drive motors
      drive_right.setPower(rpower);
      drive_right.setPower(lpower);
    }
    public void runmotor(int max, int min, DcMotor motor1, boolean forward_condition, boolean back_condition, double fpower, double rpower, double stoppower)
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
  public void reset() {
    int tcenter = 360;
    int pitchmin = 0;
    int scissormin = 0;
    if (table.getCurrentPosition() > tcenter) {
      table.setPower(-.5);
    } else if (table.getCurrentPosition() < tcenter) {
      table.setPower(.5);
    } else {
      table.setPower(0);
    }
    if (scissor.getCurrentPosition() > scissormin) {
      scissor.setPower(-1);
    } else if (scissor.getCurrentPosition() < scissormin) {
      scissor.setPower(1);
    } else {
      scissor.setPower(0);
    }
    if (tpitch.getCurrentPosition() > pitchmin) {
      tpitch.setPower(-1);
    } else if (tpitch.getCurrentPosition() < pitchmin)
    {
      tpitch.setPower(1);
    }else{
      tpitch.setPower(0);
    }
    //reset encoders
    drive_right.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    drive_left.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    scissor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    table.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    tpitch.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    intake.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    // Allow motors to use encoders
    drive_left.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    scissor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    table.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    tpitch.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    intake.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
  }
    @Override
    public void loop() {
      set_drive_train(); //runs the drive train motors
      runmotor(0, 10000, scissor, gamepad2.dpad_up, gamepad2.dpad_down, .5, -.5, 0); // run scissor lift
      runmotor(0, 10000, table, gamepad2.right_bumper, gamepad2.left_bumper, .5, -.5, 0); // run turn table
      runmotor(0, 10000, tpitch, gamepad2.a, gamepad2.b, 1, -1, 0); //runs the pitch motor for the scissor lift
      intake.setPower(gamepad1.right_trigger); //sets intake power
      if(gamepad1.x)
      {
        reset(); //resets position of all motors
      }
    }
  }
}
