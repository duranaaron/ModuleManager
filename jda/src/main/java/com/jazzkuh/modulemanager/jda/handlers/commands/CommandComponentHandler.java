package com.jazzkuh.modulemanager.jda.handlers.commands;

import com.jazzkuh.commandlib.jda.AnnotationCommand;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;
import com.jazzkuh.modulemanager.jda.JDAModuleManager;

public final class CommandComponentHandler implements IComponentHandler<AnnotationCommand> {

    @Override
    public void onLoad(ModuleManager manager, AnnotationCommand component) {
        //Nothing to do here
    }

    @Override
    public void onEnable(ModuleManager manager, AnnotationCommand component) {
        if (!(manager instanceof JDAModuleManager<?> JDAModuleManager))
            return;

        component.register(JDAModuleManager.getBotInstance().getJDA());
    }

    @Override
    public void onDisable(ModuleManager manager, AnnotationCommand component) {
        //Nothing to do here
    }
}