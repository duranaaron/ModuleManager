package com.jazzkuh.modulemanager.common.loader;

import com.jazzkuh.modulemanager.common.modules.AbstractModule;

import java.lang.reflect.Constructor;
import java.util.List;

public record ScannedModule(Class<? extends AbstractModule<?>> moduleClass, Constructor<?> injectionConstructor, List<Class<?>> dependencies) {

}
