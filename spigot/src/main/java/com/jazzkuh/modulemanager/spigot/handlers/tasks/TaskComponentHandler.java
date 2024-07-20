package com.jazzkuh.modulemanager.spigot.handlers.tasks;

import com.jazzkuh.modulemanager.spigot.SpigotModuleManager;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.WeakHashMap;

public final class TaskComponentHandler implements IComponentHandler<Runnable> {

    private final Map<Runnable, BukkitTask> registeredTasks = new WeakHashMap<>();

    @Override
    public void onLoad(ModuleManager manager, Runnable component) {
        //Nothing to do here
    }

    @Override
    public void onEnable(ModuleManager manager, Runnable component) {
        if (!(manager instanceof SpigotModuleManager spigotModuleManager))
            return;
        Plugin plugin = spigotModuleManager.getPlugin();


        TaskInfo taskInfo = component.getClass().getAnnotation(TaskInfo.class);

        if (taskInfo == null)
            throw new IllegalStateException("Component " + component.getClass().getName() + " is not annotated with @TaskInfo");

        if (taskInfo.async()) {
            if (taskInfo.repeating()) {
                BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, component,
                        taskInfo.delay(), taskInfo.period());
                registeredTasks.put(component, task);
            } else {
                if (taskInfo.delay() < 0) {
                    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, component);
                } else {
                    BukkitTask task = plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, component, taskInfo.delay());
                    registeredTasks.put(component, task);
                }
            }
        } else {
            if (taskInfo.repeating()) {
                BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin, component,
                        taskInfo.delay(), taskInfo.period());
                registeredTasks.put(component, task);
            } else {
                if (taskInfo.delay() < 0) {
                    plugin.getServer().getScheduler().runTask(plugin, component);
                } else {
                    BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, component, taskInfo.delay());
                    registeredTasks.put(component, task);
                }
            }
        }
    }

    @Override
    public void onDisable(ModuleManager manager, Runnable component) {
        BukkitTask task = registeredTasks.remove(component);
        if (task != null)
            task.cancel();
    }
}
