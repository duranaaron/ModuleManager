package com.jazzkuh.modulemanager.minestom.handlers.listeners;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.minestom.MinestomModuleManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventListener;

public final class ListenerComponentHandler implements IComponentHandler<Listener> {

    @Override
    public void onLoad(ModuleManager manager, Listener component) {
        //DO NOTHING
    }

    @Override
    public void onEnable(ModuleManager manager, Listener component) {
        if (!(manager instanceof MinestomModuleManager<?> minestomModuleManager))
            return;

        component.register();
    }

    @Override
    public void onDisable(ModuleManager manager, Listener component) {
        if (!(manager instanceof MinestomModuleManager<?> minestomModuleManager))
            return;
    }
}
