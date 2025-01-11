package org.firstinspires.ftc.teamcode.main;

import org.firstinspires.ftc.teamcode.base.di.MainModule;
import org.firstinspires.ftc.teamcode.main.components.ComponentsModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {MainModule.class, ComponentsModule.class})
@Singleton
public interface MainComponent {
    // write void inject(T object) for all types that will use this class
    void inject(TestTeleOp t);
}
