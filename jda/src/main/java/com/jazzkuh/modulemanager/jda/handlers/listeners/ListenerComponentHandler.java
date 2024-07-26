package com.jazzkuh.modulemanager.jda.handlers.listeners;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.jda.JDAModuleManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class ListenerComponentHandler implements IComponentHandler<ListenerAdapter> {

    @Override
    public void onLoad(ModuleManager manager, ListenerAdapter component) {
        //DO NOTHING
    }

    @Override
    public void onEnable(ModuleManager manager, ListenerAdapter component) {
        if (!(manager instanceof JDAModuleManager<?> jdaModuleManager))
            return;

        jdaModuleManager.getBotInstance().getJDA().addEventListener(component);
    }

    @Override
    public void onDisable(ModuleManager manager, ListenerAdapter component) {
        if (!(manager instanceof JDAModuleManager<?> jdaModuleManager))
            return;

        jdaModuleManager.getBotInstance().getJDA().removeEventListener(component);
    }
}
