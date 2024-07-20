package com.jazzkuh.modulemanager.velocity.handlers.tasks;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.Scheduler;
import com.jazzkuh.modulemanager.velocity.VelocityModuleManager;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;

import java.util.HashMap;
import java.util.Map;

public final class TaskComponentHandler implements IComponentHandler<Runnable> {

    private final Map<Runnable, ScheduledTask> tasks = new HashMap<>();

    @Override
    public void onLoad(ModuleManager manager, Runnable component) {
        //No need for onLoad
    }

    @Override
    public void onEnable(ModuleManager manager, Runnable component) {
        if (!(manager instanceof VelocityModuleManager<?> velocityModuleManager))
            return;

        TaskInfo dynTask = component.getClass().getAnnotation(TaskInfo.class);
        if (dynTask == null) {
            throw new IllegalArgumentException("The registered component " + component.getClass().getSimpleName() + " is not a @DynTask");
        }

        Scheduler.TaskBuilder builder = velocityModuleManager.getPlugin().getProxyServer().getScheduler()
                .buildTask(velocityModuleManager.getPlugin(), component);
        builder.delay(dynTask.delay(), dynTask.timeType());

        if (dynTask.repeating())
            builder.repeat(dynTask.period(), dynTask.timeType());

        ScheduledTask task = builder.schedule();
        tasks.put(component, task);
    }

    @Override
    public void onDisable(ModuleManager manager, Runnable component) {
        ScheduledTask task = tasks.remove(component);
        if (task != null)
            task.cancel();
    }
}
