package com.jazzkuh.modulemanager.minestom.handlers.listeners;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.minestom.MinestomModuleManager;
import net.minestom.server.MinecraftServer;

public final class PacketListenerComponentHandler implements IComponentHandler<PacketListener> {

    @Override
    public void onLoad(ModuleManager manager, PacketListener component) {
        //DO NOTHING
    }

    @Override
    public void onEnable(ModuleManager manager, PacketListener component) {
        if (!(manager instanceof MinestomModuleManager<?> minestomModuleManager))
            return;

        component.register(MinecraftServer.getPacketListenerManager());
    }

    @Override
    public void onDisable(ModuleManager manager, PacketListener component) {
        if (!(manager instanceof MinestomModuleManager<?> minestomModuleManager))
            return;
    }
}
