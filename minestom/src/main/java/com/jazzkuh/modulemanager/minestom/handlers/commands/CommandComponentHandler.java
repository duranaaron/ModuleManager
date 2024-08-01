package com.jazzkuh.modulemanager.minestom.handlers.commands;

import com.jazzkuh.commandlib.minestom.AnnotationCommand;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.minestom.MinestomModuleManager;

public final class CommandComponentHandler implements IComponentHandler<AnnotationCommand> {

    @Override
    public void onLoad(ModuleManager manager, AnnotationCommand component) {
        //Nothing to do here
    }

    @Override
    public void onEnable(ModuleManager manager, AnnotationCommand component) {
        if (!(manager instanceof MinestomModuleManager<?> minestomModuleManager))
            return;

        component.register(minestomModuleManager.getServer().getMinecraftServer().getCommandManager());
    }

    @Override
    public void onDisable(ModuleManager manager, AnnotationCommand component) {
        //Nothing to do here
    }
}