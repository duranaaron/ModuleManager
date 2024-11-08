package com.jazzkuh.modulemanager.generic;


import com.jazzkuh.modulemanager.common.modules.AbstractModule;

public abstract class GenericModule extends AbstractModule<GenericModuleManager> {

    public GenericModule(GenericModuleManager owningManager) {
        super(owningManager);
    }

}
