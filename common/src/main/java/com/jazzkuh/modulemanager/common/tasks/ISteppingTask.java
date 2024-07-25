package com.jazzkuh.modulemanager.common.tasks;

public interface ISteppingTask {

    default void start() {
        //Is not required to be implemented by subclass
    }

    void step();

    boolean shouldStart();

    boolean shouldStep();

    boolean isDone();

    int getMaxMsPerTick();
}