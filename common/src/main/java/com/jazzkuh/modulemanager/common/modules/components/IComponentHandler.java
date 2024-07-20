package com.jazzkuh.modulemanager.common.modules.components;

import com.jazzkuh.modulemanager.common.ModuleManager;

public interface IComponentHandler<C> {
    void onLoad(ModuleManager manager, C component);
    void onEnable(ModuleManager manager, C component);
    void onDisable(ModuleManager manager, C component);
}
