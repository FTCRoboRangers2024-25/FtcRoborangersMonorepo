package org.firstinspires.ftc.teamcode.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ActionQueue {
    private final List<BooleanSupplier> actions = new ArrayList<>();
    private int currentActionIndex = 0;

    public void addAction(BooleanSupplier action) {
        actions.add(action);
    }

    public void run() {
        if (isComplete()) {
            return;
        }

        BooleanSupplier currentAction = actions.get(currentActionIndex);

        if (currentAction.getAsBoolean()) {
            currentActionIndex++;
        }
    }

    public boolean isComplete() {
        return currentActionIndex >= actions.size();
    }

    public void reset() {
        currentActionIndex = 0;
    }
}
