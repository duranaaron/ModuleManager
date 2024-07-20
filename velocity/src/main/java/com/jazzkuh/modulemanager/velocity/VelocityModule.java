package com.jazzkuh.modulemanager.velocity;

import com.jazzkuh.modulemanager.common.modules.AbstractModule;

public abstract class VelocityModule<P extends IVelocityPlugin> extends AbstractModule<VelocityModuleManager<P>> {

    public VelocityModule(VelocityModuleManager<P> owningManager) {
        super(owningManager);
    }

    public P getPlugin() {
        return getOwningManager().getPlugin();
    }

}
