package com.jazzkuh.modulemanager.spigot;

import com.jazzkuh.modulemanager.common.modules.AbstractModule;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SpigotModule<P extends JavaPlugin> extends AbstractModule<SpigotModuleManager<P>> {

    public SpigotModule(SpigotModuleManager<P> moduleManager) {
        super(moduleManager);
    }

    public P getPlugin() {
        return getOwningManager().getPlugin();
    }

}
