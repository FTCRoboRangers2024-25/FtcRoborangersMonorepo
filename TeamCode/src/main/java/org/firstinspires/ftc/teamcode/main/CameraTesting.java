package org.firstinspires.ftc.teamcode.main;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.OpModeBase;
import org.firstinspires.ftc.teamcode.main.components.test;

import javax.inject.Inject;

@TeleOp(name = "Test")
public class CameraTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Motor left = new Motor(hardwareMap, "test");

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("dk", left.getCurrentPosition());
            telemetry.update();
        }
    }
}
