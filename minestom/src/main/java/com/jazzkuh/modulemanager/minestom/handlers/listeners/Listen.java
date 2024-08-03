package com.jazzkuh.modulemanager.minestom.handlers.listeners;

import net.minestom.server.network.ConnectionState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listen {
    ConnectionState connectionState() default ConnectionState.PLAY;
}