package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.TeleOpBase;
import org.firstinspires.ftc.teamcode.base.utils.Gamepads;
import org.firstinspires.ftc.teamcode.main.components.IntakeClaw;
import org.firstinspires.ftc.teamcode.main.components.IntakeExtension;
import org.firstinspires.ftc.teamcode.main.components.MecanumCameraAligner;

import javax.inject.Inject;

@TeleOp
@Disabled
public class CameraTest extends TeleOpBase {
    @Inject
    public MecanumCameraAligner camera;

    @Inject
    public IntakeClaw intakeClaw;

    @Inject
    public IntakeExtension intakeExtension;

    @Override
    protected void startup() {
        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2)))
                .build();

        main.inject(this);

        addComponent(camera);
        addComponent(intakeClaw);
        addComponent(intakeExtension);
    }

    @Override
    public void loop() {
        super.loop();
        camera.alignYellow();
    }
}
