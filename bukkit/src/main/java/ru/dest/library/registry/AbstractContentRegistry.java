package ru.dest.library.registry;

import ru.dest.library.exception.ContentAlreadyRegisteredException;
import ru.dest.library.object.RegistryKey;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AbstractContentRegistry<OBJ extends IRegistrable> {

    protected final Map<RegistryKey, OBJ> content = new HashMap<>();

    public <T extends OBJ> T register(Supplier<T> obj){
        T object = obj.get();
        if(content.containsKey(object.getId())){
            throw new ContentAlreadyRegisteredException(object.getId());
        }
        content.put(object.getId(), object);

        return object;
    }

    public boolean isRegistered(RegistryKey id){
        return content.containsKey(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends OBJ> T byId(RegistryKey id){
        return (T)content.get(id);
    }

    public Set<RegistryKey> getRegisteredIds(){
        return Collections.unmodifiableSet(content.keySet());
    }

    public Collection<OBJ> getRegisteredContent(){
        return Collections.unmodifiableCollection(content.values());
    }

    @SuppressWarnings("unchecked")
    public <T extends OBJ> Collection<T> getRegisteredContent(Predicate<OBJ> filter){
        List<T> result = new ArrayList<>();
        content.values().forEach(obj -> {
            if(filter.test(obj)) result.add((T)obj);
        });
        return result;
    }

}
