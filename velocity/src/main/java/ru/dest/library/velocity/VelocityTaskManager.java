package ru.dest.library.velocity;

import com.velocitypowered.api.scheduler.ScheduledTask;
import ru.dest.library.ITaskManager;
import ru.dest.library.plugin.MinecraftPlugin;
import ru.dest.library.utils.RandomUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class VelocityTaskManager<T extends MinecraftPlugin<T, ?>> implements ITaskManager {

    private final T plugin;
    private final Map<Integer, ScheduledTask> calledTasks = new HashMap<>();


    public VelocityTaskManager(T plugin) {
        this.plugin = plugin;
    }

    private int getRandomId(){
        int id = RandomUtils.randomInt(0, Integer.MAX_VALUE);
        if(calledTasks.containsKey(id)) return getRandomId();
        return id;
    }

    @Override
    public int call(Runnable r) {
        int id = getRandomId();
        this.calledTasks.put(id, plugin.getServer().getScheduler().buildTask(plugin, r).schedule());
        return id;
    }

    @Override
    public int callAsync(Runnable r) {
        int id = getRandomId();
        this.calledTasks.put(id, plugin.getServer().getScheduler().buildTask(plugin, r).schedule());
        return id;
    }

    @Override
    public int callLater(Runnable r, long time) {
        int id = getRandomId();
        this.calledTasks.put(id, plugin.getServer().getScheduler().buildTask(plugin, r).delay(time, TimeUnit.MILLISECONDS).schedule());
        return id;
    }

    @Override
    public int callLaterAsync(Runnable r, long time) {
        return callLater(r, time);
    }

    @Override
    public int callRepeating(Runnable r, long time) {
        int id = getRandomId();
        this.calledTasks.put(id, plugin.getServer().getScheduler().buildTask(plugin, r).repeat(time, TimeUnit.MILLISECONDS).schedule());
        return id;
    }

    @Override
    public int callRepeatingAsync(Runnable r, long time) {
        return callRepeating(r, time);
    }

    @Override
    public void cancel(int id) {
        Optional.ofNullable(calledTasks.get(id)).ifPresent(task -> {task.cancel(); calledTasks.remove(id);});
    }

    @Override
    public void cancelAll() {
        for(int id : calledTasks.keySet()){
            calledTasks.get(id).cancel();
        }
        calledTasks.clear();
    }
}
