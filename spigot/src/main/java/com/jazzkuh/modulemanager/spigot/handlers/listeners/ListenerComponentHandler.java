package com.jazzkuh.modulemanager.spigot.handlers.listeners;

import com.jazzkuh.modulemanager.spigot.SpigotModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.common.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public final class ListenerComponentHandler implements IComponentHandler<Listener> {

    @Override
    public void onLoad(ModuleManager manager, Listener component) {
        //Nothing to do here
    }

    @Override
    public void onEnable(ModuleManager manager, Listener component) {
        if (!(manager instanceof SpigotModuleManager<?> spigotModuleManager))
            return;

        Bukkit.getServer().getPluginManager().registerEvents(component, spigotModuleManager.getPlugin());
    }

    @Override
    public void onDisable(ModuleManager manager, Listener component) {
        HandlerList.unregisterAll(component);
    }
}
