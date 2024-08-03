package com.jazzkuh.modulemanager.minestom;

import com.jazzkuh.commandlib.minestom.AnnotationCommand;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.tasks.ISteppingTask;
import com.jazzkuh.modulemanager.minestom.handlers.commands.CommandComponentHandler;
import com.jazzkuh.modulemanager.minestom.handlers.listeners.*;
import com.jazzkuh.modulemanager.minestom.handlers.tasks.SteppingTaskComponentHandler;
import com.jazzkuh.modulemanager.minestom.handlers.tasks.TaskComponentHandler;
import lombok.Getter;
import net.minestom.server.event.EventListener;
import org.slf4j.Logger;

public final class MinestomModuleManager<S extends IMinestomPlatform> extends ModuleManager {

    @Getter
    private final S server;

    public MinestomModuleManager(S server, Logger logger) {
        super(logger);
        this.server = server;

        getComponentRegistry().registerComponentHandler(EventListener.class, new MinestomListenerComponentHandler());
        getComponentRegistry().registerComponentHandler(Listener.class, new ListenerComponentHandler());
        getComponentRegistry().registerComponentHandler(PacketListener.class, new PacketListenerComponentHandler());
        getComponentRegistry().registerComponentHandler(Runnable.class, new TaskComponentHandler());
        getComponentRegistry().registerComponentHandler(AnnotationCommand.class, new CommandComponentHandler());
        getComponentRegistry().registerComponentHandler(ISteppingTask.class, new SteppingTaskComponentHandler());
    }
}
