package com.jazzkuh.modulemanager.generic;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.generic.handlers.tasks.TaskComponentHandler;
import lombok.Getter;
import org.slf4j.Logger;

import java.util.TimerTask;

@Getter
public final class GenericModuleManager extends ModuleManager {

    public GenericModuleManager(Logger logger) {
        super(logger);

        this.getComponentRegistry().registerComponentHandler(TimerTask.class, new TaskComponentHandler());
    }
}
