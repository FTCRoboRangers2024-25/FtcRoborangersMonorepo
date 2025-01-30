package org.firstinspires.ftc.teamcode.base.utils;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

// TO BE TESTED
public class ActionQueue {
    private final List<BooleanSupplier> actions = new ArrayList<>();
    private int currentActionIndex = 0;
    private ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public ActionQueue addAction(BooleanSupplier action) {
        actions.add(action);
        return this;
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
