package com.jazzkuh.modulemanager.minestom.handlers.listeners;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public interface Listener {
    int DEFAULT_PRIORITY = Integer.MAX_VALUE / 2;

    default void register() {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Listen.class)) continue;

            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 1 || !Event.class.isAssignableFrom(paramTypes[0])) {
                throw new IllegalArgumentException("Method annotated with @Listen must have a single parameter of type Event or a subclass of Event.");
            }

            Class<?> registeredEvent = paramTypes[0];

            GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
            EventNode<Event> eventNode = EventNode.all(UUID.randomUUID().toString());
            eventNode.setPriority(DEFAULT_PRIORITY - method.getAnnotation(Listen.class).priority());

            eventNode.addListener(registeredEvent.asSubclass(Event.class), event -> {
                try {
                    method.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });

            globalEventHandler.addChild(eventNode);
        }
    }
}