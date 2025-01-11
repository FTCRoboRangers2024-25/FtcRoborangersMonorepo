package org.firstinspires.ftc.teamcode.base.di;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.Gamepads;
import org.firstinspires.ftc.teamcode.base.messaging.IMessageBroadcaster;
import org.firstinspires.ftc.teamcode.base.messaging.StandardMessageBroadcaster;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private final Telemetry telemetry;
    private final HardwareMap hardwareMap;
    private final Gamepads gamepads;

    public MainModule(Telemetry telemetry, HardwareMap hardwareMap, Gamepads gamepads) {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
        this.gamepads = gamepads;
    }

    @Provides
    @Singleton
    public Telemetry provideTelemetry() {
        return telemetry;
    }

    @Provides
    @Singleton
    public HardwareMap provideHardwareMap() {
        return hardwareMap;
    }

    @Provides
    @Singleton
    public IMessageBroadcaster provideMessageBroadcaster(StandardMessageBroadcaster broadcaster) {
        return broadcaster;
    }

    @Provides
    @Singleton
    public Gamepads provideGamepads() {
        return gamepads;
    }
}
