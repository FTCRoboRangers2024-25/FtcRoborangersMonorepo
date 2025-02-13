package org.firstinspires.ftc.teamcode.main;

import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.main.opmodes.CameraTesting;
import org.firstinspires.ftc.teamcode.main.opmodes.ClawDifferentialTesting;
import org.firstinspires.ftc.teamcode.main.opmodes.IntakeExtensionTesting;
import org.firstinspires.ftc.teamcode.main.opmodes.IntakeHorizontalTesting;
import org.firstinspires.ftc.teamcode.main.opmodes.MecanumTesting;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = MainModule.class)
@Singleton
public interface MainComponent {
    void inject(ClawDifferentialTesting clawDifferentialTesting);

    void inject(MecanumTesting mecanumTesting);

    void inject(IntakeExtensionTesting intakeExtensionTesting);

    void inject(IntakeHorizontalTesting intakeHorizontalTesting);

    void inject(CameraTesting cameraTesting);
}
