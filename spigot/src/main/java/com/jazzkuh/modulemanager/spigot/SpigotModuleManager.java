package com.jazzkuh.modulemanager.spigot;

import com.jazzkuh.commandlib.spigot.AnnotationCommand;
import com.jazzkuh.modulemanager.common.tasks.ISteppingTask;
import com.jazzkuh.modulemanager.spigot.handlers.commands.CommandComponentHandler;
import com.jazzkuh.modulemanager.spigot.handlers.listeners.ListenerComponentHandler;
import com.jazzkuh.modulemanager.spigot.handlers.tasks.SteppingTaskComponentHandler;
import com.jazzkuh.modulemanager.spigot.handlers.tasks.TaskComponentHandler;
import lombok.Getter;
import com.jazzkuh.modulemanager.common.ModuleManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import org.slf4j.Logger;

public final class SpigotModuleManager<P extends JavaPlugin> extends ModuleManager {

    @Getter private final P plugin;

    public SpigotModuleManager(P plugin, Logger logger) {
        super(logger);
        this.plugin = plugin;

        getComponentRegistry().registerComponentHandler(Listener.class, new ListenerComponentHandler());
        getComponentRegistry().registerComponentHandler(Runnable.class, new TaskComponentHandler());
        getComponentRegistry().registerComponentHandler(AnnotationCommand.class, new CommandComponentHandler());
        getComponentRegistry().registerComponentHandler(ISteppingTask.class, new SteppingTaskComponentHandler());

    }
}
