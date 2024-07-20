package com.jazzkuh.modulemanager.velocity.handlers.listeners;

import com.jazzkuh.modulemanager.velocity.VelocityModuleManager;
import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.components.IComponentHandler;

public final class ListenerComponentHandler implements IComponentHandler<AbstractListener> {

    @Override
    public void onLoad(ModuleManager manager, AbstractListener component) {
        //DO NOTHING
    }

    @Override
    public void onEnable(ModuleManager manager, AbstractListener component) {
        if (!(manager instanceof VelocityModuleManager<?> velocityModuleManager))
            return;

        velocityModuleManager.getPlugin().getProxyServer().getEventManager().register(velocityModuleManager.getPlugin(), component);
    }

    @Override
    public void onDisable(ModuleManager manager, AbstractListener component) {
        if (!(manager instanceof VelocityModuleManager<?> velocityModuleManager))
            return;

        velocityModuleManager.getPlugin().getProxyServer().getEventManager()
                .unregisterListener(velocityModuleManager.getPlugin(), component);
    }
}
