package com.jazzkuh.modulemanager.minestom.handlers.tasks;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.common.tasks.ISteppingTask;
import com.jazzkuh.modulemanager.common.tasks.StepInfo;
import com.jazzkuh.modulemanager.common.tasks.SteppingTask;
import com.jazzkuh.modulemanager.minestom.MinestomModuleManager;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class SteppingTaskComponentHandler implements IComponentHandler<ISteppingTask> {

    private final Map<ISteppingTask, Task> registeredTasks = new HashMap<>();
    private int totalMsPerTickMax = 0;

    @Override
    public void onLoad(ModuleManager moduleManager, ISteppingTask iSteppingTask) {
        //Nothing to do here
    }

    @Override
    public void onEnable(ModuleManager moduleManager, ISteppingTask iSteppingTask) {
        if (!(moduleManager instanceof MinestomModuleManager<?> minestomModuleManager)) return;

        StepInfo stepInfo = iSteppingTask.getClass().getAnnotation(StepInfo.class);
        if (stepInfo == null) throw new IllegalStateException("SteppingTask " + iSteppingTask.getClass().getName() + " does not have a StepInfo annotation");

        SteppingTask steppingTask = new SteppingTask(iSteppingTask);
        Task task = minestomModuleManager.getPlugin().getMinecraftServer().getSchedulerManager()
                .scheduleTask(steppingTask, TaskSchedule.duration(stepInfo.pollDelay(), ChronoUnit.MILLIS), TaskSchedule.duration(stepInfo.pollPeriod(), ChronoUnit.MILLIS));
        this.registeredTasks.put(iSteppingTask, task);
        this.totalMsPerTickMax += iSteppingTask.getMaxMsPerTick();

        moduleManager.getLogger().log(Level.INFO, () -> "Registered SteppingTask " + iSteppingTask.getClass()
                .getSimpleName() + ". Total MS-per-tick: " + this.totalMsPerTickMax + ".");
    }

    @Override
    public void onDisable(ModuleManager moduleManager, ISteppingTask iSteppingTask) {
        Task task = this.registeredTasks.remove(iSteppingTask);
        if (task != null) task.cancel();
    }
}