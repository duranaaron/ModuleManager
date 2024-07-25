package com.jazzkuh.modulemanager.spigot.handlers.tasks;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.common.tasks.ISteppingTask;
import com.jazzkuh.modulemanager.common.tasks.StepInfo;
import com.jazzkuh.modulemanager.common.tasks.SteppingTask;
import com.jazzkuh.modulemanager.spigot.SpigotModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class SteppingTaskComponentHandler implements IComponentHandler<ISteppingTask> {

    private final Map<ISteppingTask, BukkitTask> registeredTasks = new HashMap<>();
    private int totalMsPerTickMax = 0;

    @Override
    public void onLoad(ModuleManager moduleManager, ISteppingTask iSteppingTask) {
        //Nothing to do here
    }

    @Override
    public void onEnable(ModuleManager moduleManager, ISteppingTask iSteppingTask) {
        if (!(moduleManager instanceof SpigotModuleManager<?> spigotModuleManager)) return;

        StepInfo stepInfo = iSteppingTask.getClass().getAnnotation(StepInfo.class);
        if (stepInfo == null) throw new IllegalStateException("SteppingTask " + iSteppingTask.getClass().getName() + " does not have a StepInfo annotation");

        SteppingTask steppingTask = new SteppingTask(iSteppingTask);
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(spigotModuleManager.getPlugin(), steppingTask, stepInfo.pollDelay(), stepInfo.pollPeriod());
        this.registeredTasks.put(iSteppingTask, bukkitTask);
        this.totalMsPerTickMax += iSteppingTask.getMaxMsPerTick();

        moduleManager.getLogger().log(Level.INFO, () -> "Registered SteppingTask " + iSteppingTask.getClass()
                .getSimpleName() + ". Total MS-per-tick: " + this.totalMsPerTickMax + ".");
    }

    @Override
    public void onDisable(ModuleManager moduleManager, ISteppingTask iSteppingTask) {
        BukkitTask task = this.registeredTasks.remove(iSteppingTask);
        if (task != null) task.cancel();
    }
}