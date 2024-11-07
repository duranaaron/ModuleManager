package com.jazzkuh.modulemanager.generic.handlers.tasks;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.generic.GenericModuleManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public final class TaskComponentHandler implements IComponentHandler<TimerTask> {

    private final Map<Runnable, Timer> timers = new HashMap<>();

    @Override
    public void onLoad(ModuleManager manager, TimerTask component) {
        //No need for onLoad
    }

    @Override
    public void onEnable(ModuleManager manager, TimerTask component) {
        if (!(manager instanceof GenericModuleManager genericModuleManager)) return;

        TaskInfo taskInfo = component.getClass().getAnnotation(TaskInfo.class);

        if (taskInfo == null)
            throw new IllegalStateException("Component " + component.getClass().getName() + " is not annotated with @TaskInfo");

        if (taskInfo.repeating()) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(component, taskInfo.delay(), taskInfo.period());
            timers.put(component, timer);
        } else {
            if (taskInfo.delay() < 0) {
                component.run(); // Run the task immediately
            } else {
                Timer timer = new Timer();
                timer.schedule(component, taskInfo.delay());
                timers.put(component, timer);
            }
        }
    }

    @Override
    public void onDisable(ModuleManager manager, TimerTask component) {
        Timer timer = timers.remove(component);
        if (timer != null) timer.cancel();
    }
}
