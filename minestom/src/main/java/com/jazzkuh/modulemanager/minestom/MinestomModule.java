package com.jazzkuh.modulemanager.minestom;

import com.jazzkuh.modulemanager.common.modules.AbstractModule;

public abstract class MinestomModule<P extends IMinestomServer> extends AbstractModule<MinestomModuleManager<P>> {

    public MinestomModule(MinestomModuleManager<P> owningManager) {
        super(owningManager);
    }

    public P getPlugin() {
        return getOwningManager().getPlugin();
    }

}
