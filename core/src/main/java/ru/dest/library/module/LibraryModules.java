package ru.dest.library.module;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@UtilityClass
@SuppressWarnings("unchecked")
public final class LibraryModules {

    private final Map<Class<? extends Module>, Module> moduleContainer = new HashMap<>();

    public <T extends Module> void registerModule(Class<T> moduleClass, T module) {
        if(moduleContainer.containsKey(moduleClass)) throw new IllegalStateException("Module already registered");
        moduleContainer.put(moduleClass, module);
    }


    public <T extends Module> T get(Class<T> moduleClass) {
        if(!moduleContainer.containsKey(moduleClass)) throw new IllegalArgumentException("Such module doesn't registered");
        return (T) moduleContainer.get(moduleClass);
    }

    public <T extends Module> Optional<T> getOptional(Class<T> moduleClass) {
        if(!moduleContainer.containsKey(moduleClass)) {
            return Optional.empty();
        }
        return Optional.of((T) moduleContainer.get(moduleClass));
    }


    public <T extends Module> boolean isRegistered(Class<T> moduleClass){
        return moduleContainer.containsKey(moduleClass);
    }
}
