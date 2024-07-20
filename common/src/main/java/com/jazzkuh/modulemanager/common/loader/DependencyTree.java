package com.jazzkuh.modulemanager.common.loader;

import com.jazzkuh.modulemanager.common.modules.AbstractModule;

import java.util.*;

public final class DependencyTree {

    private final Map<Class<? extends AbstractModule<?>>, ScannedModule> scannedModuleHashMap = new HashMap<>();
    private final Map<ScannedModule, List<Class<? extends AbstractModule<?>>>> dependencyMap = new HashMap<>();

    public void registerDependency(ScannedModule scannedModule) {
        List<Class<? extends AbstractModule<?>>> dependencyList = new ArrayList<>();
        scannedModuleHashMap.put(scannedModule.moduleClass(), scannedModule);

        for (int i = 1; i < scannedModule.dependencies().size(); i++) {
            Class<?> dependency = scannedModule.dependencies().get(i);

            if (AbstractModule.class.isAssignableFrom(dependency)) {
                dependencyList.add((Class<? extends AbstractModule<?>>) dependency);
            }
        }

        dependencyMap.put(scannedModule, dependencyList);
    }

    public boolean hasLoop() {
        Set<Class<? extends AbstractModule<?>>> history = new HashSet<>();

        for (Map.Entry<ScannedModule, List<Class<? extends AbstractModule<?>>>> entry : dependencyMap.entrySet()) {
            if (!checkDependencies(entry.getKey(), history))
                return true;
        }

        return false;
    }

    private boolean checkDependencies(ScannedModule scannedModule, Set<Class<? extends AbstractModule<?>>> history) {
        for (Class<? extends AbstractModule<?>> dependency : dependencyMap.get(scannedModule)) {
            if (history.contains(dependency))
                return false;

            Set<Class<? extends AbstractModule<?>>> newHistory = new HashSet<>(history);
            newHistory.add(dependency);
            if (!checkDependencies(scannedModuleHashMap.get(dependency), newHistory))
                return false;
        }

        return true;
    }

    public List<ScannedModule> getSortedDependencies() {
        return dependencyMap.entrySet().stream()
                .sorted(Comparator.comparingInt(value -> value.getValue().size()))
                .map(Map.Entry::getKey)
                .toList();
    }
}
