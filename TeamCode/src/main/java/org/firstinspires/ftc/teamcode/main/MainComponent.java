package org.firstinspires.ftc.teamcode.main;

import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.main.opmodes.ClawDifferentialTesting;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = MainModule.class)
@Singleton
public interface MainComponent {
    void inject(ClawDifferentialTesting clawDifferentialTesting);
}
