package org.firstinspires.ftc.teamcode.main;

import org.firstinspires.ftc.teamcode.base.di.MainModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = MainModule.class)
@Singleton
public interface MainComponent {
    // write void inject(T object) for all types that will use this class
    void inject(MainTeleOpRed t);
    void inject(MainTeleOpBlue t);
    void inject(AutoYellow t);
    void inject(OldYellowAuto t);

    void inject(CameraTest cameraTest);
}
