package com.jazzkuh.modulemanager.common.loader;

import com.jazzkuh.modulemanager.common.ModuleManager;
import com.jazzkuh.modulemanager.common.modules.AbstractModule;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public final class ModuleFinder {

    private final Map<Class<? extends AbstractModule<?>>, AbstractModule<?>> createdModules = new LinkedHashMap<>();
    private final ModuleManager moduleManager;

    public ModuleFinder(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public Map<Class<? extends AbstractModule<?>>, AbstractModule<?>> getModulesInOrder(String rootPackage) {
        DependencyTree dependencyTree = new DependencyTree();

        for (Class<? extends AbstractModule<?>> clazz : scanPackage(rootPackage)) {
            Constructor<?> injectionConstructor = getInjectionConstructor(clazz);
            Class<?>[] dependencies = injectionConstructor.getParameterTypes();

            ScannedModule scannedModule = new ScannedModule(clazz, injectionConstructor, Arrays.asList(dependencies));
            dependencyTree.registerDependency(scannedModule);
        }

        if (dependencyTree.hasLoop())
            throw new IllegalStateException("Dependency loop detected");

        for (ScannedModule sortedModule : dependencyTree.getSortedDependencies()) {
            List<Object> collect = sortedModule.dependencies().subList(1, sortedModule.dependencies().size()).stream()
                    .map(createdModules::get).collect(Collectors.toList());

            collect.add(0, moduleManager);

            try {
                createdModules.put(sortedModule.moduleClass(), (AbstractModule<?>) sortedModule.injectionConstructor()
                        .newInstance(collect.toArray()));
            } catch (Exception e) {
                throw new IllegalStateException("Failed to create instance of " + sortedModule.moduleClass().getName(), e);
            }
        }

        return createdModules;
    }

    private static Set<Class<? extends AbstractModule<?>>> scanPackage(String rootPackage) {
        Reflections reflections = new Reflections(rootPackage);
        return reflections.getSubTypesOf(AbstractModule.class).stream()
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .map(clazz -> (Class<? extends AbstractModule<?>>) clazz).collect(Collectors.toSet());
    }

    private Constructor<?> getInjectionConstructor(Class<? extends AbstractModule<?>> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 0)
                continue;
            if (!ModuleManager.class.isAssignableFrom(parameterTypes[0]))
                continue;

            boolean continueLoop = false;
            for (int i = 1; i < parameterTypes.length; i++) {
                if (!AbstractModule.class.isAssignableFrom(parameterTypes[i])) {
                    continueLoop = true;
                    break;
                }
            }

            if (continueLoop)
                continue;

            return constructor;
        }

        throw new IllegalStateException("No valid constructor found for " + clazz.getName());
    }
}
