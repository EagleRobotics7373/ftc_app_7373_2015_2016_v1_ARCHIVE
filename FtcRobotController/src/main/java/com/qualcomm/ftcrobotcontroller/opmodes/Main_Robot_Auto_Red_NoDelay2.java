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

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class Main_Robot_Auto_Red_NoDelay2 extends OpMode {

    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();
    //initiate variables
    DcMotor mright1;
    DcMotor mleft1;
    DcMotor mright2;
    DcMotor mleft2;
    DcMotor arcreactor;
    DcMotor intake;
    DcMotor convayer;
    DcMotor pullup;
    Servo servor;
    Servo servol;
    int mode = 1;
    int desired;
int x;
    public  void delay(long ms)
    {
     long start = System.currentTimeMillis();
        long desired = System.currentTimeMillis() + ms;
        while(ms != desired)
        {

        }
    }
    public void movetics(int ticks, float power_right, float power_left)
    {
        x = mright1.getCurrentPosition();
        while(x<desired) {
            mleft1.setPower(power_left);
            mleft2.setPower(power_left);
            mright1.setPower(power_right);
            mright2.setPower(power_right);

        }

    }
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

        //get references from hardware map
        mleft1 = hardwareMap.dcMotor.get("leftf");
        mleft2 = hardwareMap.dcMotor.get("leftr");
        intake = hardwareMap.dcMotor.get("intake");
        mright1 = hardwareMap.dcMotor.get("rightf");
        mright2 = hardwareMap.dcMotor.get("rightr");
        convayer = hardwareMap.dcMotor.get("conveyer");
        arcreactor = hardwareMap.dcMotor.get("arch");
        servol = hardwareMap.servo.get("bridger");
        servor = hardwareMap.servo.get("bridgel");
        pullup = hardwareMap.dcMotor.get("pullup");
        //set dc motor modes to run with encoders and reset the encoders
        mleft1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        mleft2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        mright1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        mright2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        mleft1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mleft2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mright1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mright2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
      //  x = mleft2.getCurrentPosition();

    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override

    public void loop() {
        telemetry.addData("Start delay", 1);

        telemetry.addData("Delayed 2 seconds", 1);

        try {
            Thread.sleep(3000);
        }catch(Exception e)
        {
            telemetry.addData("Error in thread.sleep()", 1);
        }

        desired = 0;
        movetics(desired,0, 1);
        desired = 0;
        movetics(desired,0, 1);
        desired = 0;
        movetics(desired,1, 1);
        desired = 0;
        movetics(desired, 0, 1);
        desired = 0;
        movetics(desired, 1,1);
        stop();
    }
}
