package com.jazzkuh.modulemanager.common.modules.components;

import com.jazzkuh.modulemanager.common.ModuleManager;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ComponentRegistry {
    private final ModuleManager moduleManager;

    protected Map<Class<?>, IComponentHandler<?>> componentHandlerMap = new HashMap<>();

    public <C> void registerComponentHandler(Class<C> componentClass, IComponentHandler<?> handler) {
        componentHandlerMap.put(componentClass, handler);
    }

    public <C> void execute(ModuleManager.State state, List<C> object) {
        execute(state, object.toArray());
    }

    public void execute(ModuleManager.State state, Object... objects) {
        for (Object object : objects) {
            execute(state, object);
        }
    }

    public void execute(ModuleManager.State state, Object object) {
        boolean found = false;

        for (Map.Entry<Class<?>, IComponentHandler<?>> entry : componentHandlerMap.entrySet()) {
            if (isClassRegistered(entry.getKey(), object.getClass())) {
                executeState(state, (IComponentHandler<Object>) entry.getValue(), object);
                found = true;
            }
        }
    }

    protected void executeState(ModuleManager.State state, IComponentHandler<Object> handler, Object object) {
        switch (state) {
            case LOAD:
                handler.onLoad(moduleManager, object);
                break;
            case ENABLE:
                handler.onEnable(moduleManager, object);
                break;
            case DISABLE:
                handler.onDisable(moduleManager, object);
                break;
            default:
                throw new IllegalStateException("Unknown state: " + state);
        }
    }

    public boolean isClassRegistered(Class<?> clazz) {
        return componentHandlerMap.keySet().stream().anyMatch(componentClass -> isClassRegistered(componentClass, clazz));
    }

    private boolean isClassRegistered(Class<?> componentClass, Class<?> target) {
        return componentClass.isAssignableFrom(target) || (componentClass.isAnnotation()
                && target.getDeclaredAnnotation((Class<Annotation>) componentClass) != null);
    }
}
