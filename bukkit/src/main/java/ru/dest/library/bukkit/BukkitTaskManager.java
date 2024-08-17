package ru.dest.library.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import ru.dest.library.ITaskManager;
import ru.dest.library.utils.RandomUtils;

import java.util.HashMap;
import java.util.Map;

public class BukkitTaskManager implements ITaskManager {

    private final JavaPlugin plugin;
    private final Map<Integer, BukkitTask> calledTasks;

    public BukkitTaskManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.calledTasks = new HashMap<>();
    }

    private int getRandomId(){
        int id = RandomUtils.randomInt(0, Integer.MAX_VALUE);
        if(calledTasks.containsKey(id)) return getRandomId();
        return id;
    }

    @Override
    public int call(Runnable r) {
        int id = getRandomId();
        calledTasks.put(id, Bukkit.getScheduler().runTask(plugin, r));
        return id;
    }

    @Override
    public int callAsync(Runnable r) {
        int id = getRandomId();
        calledTasks.put(id, Bukkit.getScheduler().runTaskAsynchronously(plugin, r));
        return id;
    }

    @Override
    public int callLater(Runnable r, long time) {
        int id = getRandomId();
        calledTasks.put(id, Bukkit.getScheduler().runTaskLater(plugin, r, time));
        return id;
    }

    @Override
    public int callLaterAsync(Runnable r, long time) {
        int id = getRandomId();
        calledTasks.put(id, Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, r, time));
        return id;
    }

    @Override
    public int callRepeating(Runnable r, long time) {
        int id = getRandomId();
        calledTasks.put(id, Bukkit.getScheduler().runTaskTimer(plugin, r, 0, time));
        return id;
    }

    @Override
    public int callRepeatingAsync(Runnable r, long time) {
        int id = getRandomId();
        calledTasks.put(id, Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, r, 0, time));
        return id;
    }

    @Override
    public void cancel(int id) {
        calledTasks.get(id).cancel();
        calledTasks.remove(id);
    }

    @Override
    public void cancelAll() {
        for(int id : calledTasks.keySet()){
            calledTasks.get(id).cancel();
        }
        calledTasks.clear();
    }
}
