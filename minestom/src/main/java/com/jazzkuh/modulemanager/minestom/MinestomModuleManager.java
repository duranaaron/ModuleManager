package com.jazzkuh.modulemanager.minestom;

import com.jazzkuh.commandlib.minestom.AnnotationCommand;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.minestom.handlers.commands.CommandComponentHandler;
import com.jazzkuh.modulemanager.minestom.handlers.listeners.ListenerComponentHandler;
import com.jazzkuh.modulemanager.minestom.handlers.tasks.TaskComponentHandler;
import lombok.Getter;

import java.util.EventListener;
import java.util.logging.Logger;

public final class MinestomModuleManager<P extends IMinestomServer> extends ModuleManager {

    @Getter private final P plugin;

    public MinestomModuleManager(P plugin, Logger logger) {
        super(logger);
        this.plugin = plugin;

        getComponentRegistry().registerComponentHandler(EventListener.class, new ListenerComponentHandler());
        getComponentRegistry().registerComponentHandler(Runnable.class, new TaskComponentHandler());
        getComponentRegistry().registerComponentHandler(AnnotationCommand.class, new CommandComponentHandler());
    }
}
