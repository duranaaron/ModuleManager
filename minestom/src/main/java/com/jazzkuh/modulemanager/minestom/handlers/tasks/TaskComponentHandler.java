package com.jazzkuh.modulemanager.minestom.handlers.tasks;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.minestom.MinestomModuleManager;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;

import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

public final class TaskComponentHandler implements IComponentHandler<Runnable> {

    private final Map<Runnable, Task> tasks = new HashMap<>();

    @Override
    public void onLoad(ModuleManager manager, Runnable component) {
        //No need for onLoad
    }

    @Override
    public void onEnable(ModuleManager manager, Runnable component) {
        if (!(manager instanceof MinestomModuleManager<?> minestomModuleManager))
            return;

        TaskInfo dynTask = component.getClass().getAnnotation(TaskInfo.class);
        if (dynTask == null) {
            throw new IllegalArgumentException("The registered component " + component.getClass().getSimpleName() + " is not a @DynTask");
        }

        TemporalUnit timeType = dynTask.timeType().toChronoUnit();
        TaskSchedule repeating = !dynTask.repeating() ? TaskSchedule.immediate() :
                TaskSchedule.duration(dynTask.period(), timeType);

        Task task = minestomModuleManager.getServer().getMinecraftServer().getSchedulerManager()
                .scheduleTask(component, TaskSchedule.duration(dynTask.delay(), timeType), repeating);
        tasks.put(component, task);
    }

    @Override
    public void onDisable(ModuleManager manager, Runnable component) {
        Task task = tasks.remove(component);
        if (task != null)
            task.cancel();
    }
}
