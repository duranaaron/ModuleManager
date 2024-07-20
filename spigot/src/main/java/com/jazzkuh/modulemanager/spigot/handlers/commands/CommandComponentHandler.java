package com.jazzkuh.modulemanager.spigot.handlers.commands;

import com.jazzkuh.commandlib.spigot.AnnotationCommand;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.spigot.SpigotModuleManager;

public final class CommandComponentHandler implements IComponentHandler<AnnotationCommand> {

    @Override
    public void onLoad(ModuleManager manager, AnnotationCommand component) {
        //Nothing to do here
    }

    @Override
    public void onEnable(ModuleManager manager, AnnotationCommand component) {
        if (!(manager instanceof SpigotModuleManager<?> spigotModuleManager))
            return;

        component.register(spigotModuleManager.getPlugin());
    }

    @Override
    public void onDisable(ModuleManager manager, AnnotationCommand component) {
        //Nothing to do here
    }
}