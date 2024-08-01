package com.jazzkuh.modulemanager.minestom.handlers.listeners;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.minestom.MinestomModuleManager;
import net.minestom.server.event.EventListener;

public final class ListenerComponentHandler implements IComponentHandler<EventListener> {

    @Override
    public void onLoad(ModuleManager manager, EventListener component) {
        //DO NOTHING
    }

    @Override
    public void onEnable(ModuleManager manager, EventListener component) {
        if (!(manager instanceof MinestomModuleManager<?> minestomModuleManager))
            return;

        minestomModuleManager.getServer().getMinecraftServer().getGlobalEventHandler().addListener(component);
    }

    @Override
    public void onDisable(ModuleManager manager, EventListener component) {
        if (!(manager instanceof MinestomModuleManager<?> velocityModuleManager))
            return;

        velocityModuleManager.getServer().getMinecraftServer().getGlobalEventHandler()
                .removeListener(component);
    }
}
