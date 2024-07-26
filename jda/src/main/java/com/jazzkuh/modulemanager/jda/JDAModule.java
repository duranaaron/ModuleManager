package com.jazzkuh.modulemanager.jda;


import com.jazzkuh.modulemanager.common.modules.AbstractModule;

public abstract class JDAModule<P extends IDiscordBot> extends AbstractModule<JDAModuleManager<P>> {

    public JDAModule(JDAModuleManager<P> owningManager) {
        super(owningManager);
    }

    public P getBotInstance() {
        return getOwningManager().getBotInstance();
    }

}
