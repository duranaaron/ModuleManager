package com.jazzkuh.modulemanager.velocity.handlers.commands;

import com.jazzkuh.commandlib.velocity.AnnotationCommand;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.velocity.VelocityModuleManager;

public final class CommandComponentHandler implements IComponentHandler<AnnotationCommand> {

    @Override
    public void onLoad(ModuleManager manager, AnnotationCommand component) {
        //Nothing to do here
    }

    @Override
    public void onEnable(ModuleManager manager, AnnotationCommand component) {
        if (!(manager instanceof VelocityModuleManager<?> velocityModuleManager))
            return;

        component.register(velocityModuleManager.getPlugin().getProxyServer().getCommandManager());
    }

    @Override
    public void onDisable(ModuleManager manager, AnnotationCommand component) {
        //Nothing to do here
    }
}