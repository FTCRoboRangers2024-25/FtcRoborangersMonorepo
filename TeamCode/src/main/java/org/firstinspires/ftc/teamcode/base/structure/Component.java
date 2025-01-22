package org.firstinspires.ftc.teamcode.base.structure;

public abstract class Component {
    public Component() {
        OpModeBase.components.add(this);
    }

    public void init() {}

    public void initLoop() {}

    public void start() {}

    public void loop() {}

    public void stop() {}
}
