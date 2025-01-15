package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.base.structure.OpModeBase;
import org.firstinspires.ftc.teamcode.main.components.Camera;
import org.firstinspires.ftc.teamcode.main.components.MecanumBase;
import org.firstinspires.ftc.teamcode.main.components.MecanumCameraAligner;

import javax.inject.Inject;

@TeleOp(name = "Camera testing")
public class TestingTeleOp extends OpModeBase {
    @Inject
    public Camera camera;

    @Inject
    public MecanumBase mecanumBase;

    @Inject
    public MecanumCameraAligner mecanumCameraAligner;

    @Override
    protected void startup() {
        MainComponent main = DaggerMainComponent.builder()
                .mainModule(new MainModule(telemetry, hardwareMap, new Gamepads(gamepad1, gamepad2)))
                .build();

        main.inject(this);

        addComponent(camera);
        addComponent(mecanumBase);
        addComponent(mecanumCameraAligner);
    }
}
