package ru.dest.library.config;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Getter
@SuppressWarnings("unchecked")
public final class DataConfig {

    private final Map<String, Object> data;

    public DataConfig(Map<String, Object> data) {
        this.data = data == null ? new HashMap<>() : data;
    }

    @Nullable
    public Object get(@NotNull String key){
        return get(key, null);
    }

    public Object get(@NotNull String key, Object def){
        Object val = null;

        System.out.println(key);
        if(!key.contains(".")){
            return data.getOrDefault(key, def);
        };

        String[] tmp = key.split("\\.");
        for(int i = 0 ; i < tmp.length; i ++){
            if(i != tmp.length-1){
                if(val == null) val = data.get(tmp[i]);
                else val = ((Map<String, Object>)val).get(tmp[i]);
            }else {
                if(val != null) val = ((Map<String, Object>)val).get(tmp[i]);
                else val = data.get(tmp[i]);
            }
        }
        if(val == null){
            return def;
        }else return val;
    }


    public void set(@NotNull String key, Object value){
        Map<String, Object> m = data;
        if(!key.contains(".")){
            m.put(key, value);
            return;
        }
        String[] tmp = key.split("\\.");
        for(int i = 0 ; i < tmp.length; i ++){
            if(i != tmp.length - 1){
                if(!m.containsKey(tmp[i])) m.put(tmp[i], new HashMap<>());
                m = (Map<String, Object>) m.get(tmp[i]);
            }
        }
        key = tmp[tmp.length-1];
        m.put(key, value);
    }

    public Set<String> getKeys(){
        return getKeys(true);
    }

    public Set<String> getKeys(boolean deep){
        return deep ? fillKeys(data,"", new HashSet<>()) : data.keySet();
    }

    @Contract("_, _, _ -> param3")
    private Set<String> fillKeys(@NotNull Map<String, Object> section, @NotNull String parent, @NotNull Set<String> set){
        for(String s : section.keySet()){
            Object o = section.get(s);
            String nk = parent.isEmpty() ? s : parent + "." + s;
            if(o instanceof Map) fillKeys((Map<String, Object>)o, nk,  set);
            else set.add(nk);
        }
        return set;
    }

    @Override
    public String toString(){ return data.toString();}

}
