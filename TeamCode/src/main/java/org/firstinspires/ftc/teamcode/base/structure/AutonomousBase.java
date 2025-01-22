package org.firstinspires.ftc.teamcode.base.structure;

import org.firstinspires.ftc.teamcode.base.utils.ActionQueue;

public abstract class AutonomousBase extends OpModeBase {
    protected final ActionQueue actions = new ActionQueue();

    @Override
    public void loop() {
        super.loop();
        actions.run();
    }
}
