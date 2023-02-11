package ru.dest.library.economy;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Economies {

    private Map<String, IEconomy> economies = new HashMap<>();

    public void r(String t, IEconomy e){
        economies.put(t,e);
    }

    @Nullable
    public IEconomy get(String key){
        return economies.getOrDefault(key, null);
    }
}
