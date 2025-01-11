package org.firstinspires.ftc.teamcode.base.di;

import org.firstinspires.ftc.teamcode.base.structure.OpModeBase;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = MainModule.class)
@Singleton
public interface MainDefaultComponent {
    // write void inject(T object) for all types that will use this class
    void inject(OpModeBase t);
}