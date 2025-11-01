package ru.dest.library.cooldown;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.Pair;
import ru.dest.library.plugin.IPlugin;
import ru.dest.library.utils.TimeUtils;

import java.util.*;

/**
 * Represents a Cooldown storage.
 */
public class Cooldowns {

    protected final Map<UUID, List<Pair<String, Long>>> data = new HashMap<>();

    public Cooldowns(@NotNull IPlugin<?> plugin) {
        plugin.taskManager().callRepeating(() -> {
            data.values().forEach(dataList -> {
                dataList.removeIf(data -> data.getSecondValue() - TimeUtils.getCurrentUnixTime() <= 0);
            });
        }, 20);
    }

    /**
     * Set a cooldown on action
     * @param player {@link UUID} on which cooldown affect
     * @param action action
     * @param timeInSeconds cooldown time in seconds
     */
    public void setCooldown(UUID player, String action, int timeInSeconds){
        long expires = TimeUtils.getCurrentUnixTime() + timeInSeconds;

        if(!data.containsKey(player)) data.put(player, new ArrayList<>());

        List<Pair<String, Long>> dat = data.get(player);

        Pair<String, Long> data = getData(dat, action);

        if(data != null){
            data.setSecondValue(expires);
        }else dat.add(new Pair<>(action, expires));
    }

    /**
     * Check is uuid currently on cooldown
     * @param player {@link UUID} on which cooldown was given
     * @param action action
     * @return true if action for specified uuid on cooldown or else in other cases
     */
    public boolean isOnCooldown(UUID player, String action){
        if(!data.containsKey(player)) return false;

        return getData(this.data.get(player), action) != null;
    }

    /**
     * Get cooldown left time
     * @param player {@link UUID} on which cooldown was given
     * @param action action
     * @return seconds to wait before cooldown expired
     */
    public long getLeftTime(UUID player, String action){
        if(!data.containsKey(player)) return 0;
        Pair<String, Long> data = getData(this.data.get(player), action);
        if(data == null) return 0;

        return data.getSecondValue() - TimeUtils.getCurrentUnixTime();
    }




    private Pair<String, Long> getData( List<Pair<String, Long>> data, String action){
        for(Pair<String, Long> d : data){
            if(d.getFirstValue().equals(action)) return d;
        }
        return null;
    }

}
