package ru.dest.library.task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Class for calling parallel tasks
 * and guarantees that all tasks will be closed
 * </p>
 * @since 1.0
 * @author DestKoder
 */
public class TaskManager {

    private static TaskManager i;

    private final Map<String, BukkitTask> tasks;

    private TaskManager(){
        this.tasks = new HashMap<>();
    }

    public static TaskManager get() {
        if(i == null) i = new TaskManager();
        return i;
    }

    /**
     * Call task with specified id
     * @param id task id
     * @param plugin plugin which call task
     * @param async need to call this task async ?
     * @param r {@link BukkitRunnable} which will be called
     */
    public void call(String id, Plugin plugin, boolean async, BukkitRunnable r){
        this.tasks.put(id, async ? r.runTaskAsynchronously(plugin) : r.runTask(plugin));
    }

    /**
     * Call task synchronously with specified id
     * @param id task id
     * @param plugin plugin which call task
     * @param r {@link BukkitRunnable} which will be called
     */
    public void call(String id, Plugin plugin, BukkitRunnable r){
        this.call(id,plugin,false,r);
    }

    /**
     * Call repeating task with specified id
     * @param id task id
     * @param plugin plugin which call task
     * @param async need to call this task async ?
     * @param repeatInTicks in how many tick must action execute
     * @param r {@link BukkitRunnable} which will be called
     */
    public void callRepeating(String id, Plugin plugin, long repeatInTicks, long repeatDelay, boolean async, BukkitRunnable r){
        this.tasks.put(id, async ? r.runTaskTimerAsynchronously(plugin, repeatDelay, repeatInTicks) : r.runTaskTimer(plugin, repeatDelay, repeatInTicks));
    }

    /**
     * Call repeating task with specified id
     * @param id task id
     * @param plugin plugin which call task
     * @param async need to call this task async ?
     * @param repeatInTicks in how many tick must action execute
     * @param r {@link BukkitRunnable} which will be called
     */
    public void callRepeating(String id, Plugin plugin, long repeatInTicks, boolean async, BukkitRunnable r){
        callRepeating(id, plugin, repeatInTicks, 0L, async, r);
    }

    /**
     * Call repeating task with specified id
     * @param id task id
     * @param plugin plugin which call task
     * @param repeatInTicks in how many tick must action execute
     * @param r {@link BukkitRunnable} which will be called
     */
    public void callRepeating(String id, Plugin plugin, long repeatInTicks, long repeatDelay, BukkitRunnable r){
        callRepeating(id, plugin, repeatInTicks, repeatDelay, false, r);
    }

    /**
     * Call repeating task with specified id
     * @param id task id
     * @param plugin plugin which call task
     * @param repeatInTicks in how many tick must action execute
     * @param r {@link BukkitRunnable} which will be called
     */
    public void callRepeating(String id, Plugin plugin, long repeatInTicks, BukkitRunnable r){
        callRepeating(id, plugin, repeatInTicks, 0L, false, r);
    }

    public void callLater(@NotNull Plugin plugin, long delay, boolean async, BukkitRunnable r){
        this.tasks.put(plugin.getName().toLowerCase() + "_"+ Utils.getCurrentTimeInSeconds(), async ? r.runTaskLaterAsynchronously(plugin, delay) : r.runTaskLater(plugin, delay));
    }

    public void callLater(Plugin plugin, long delay, BukkitRunnable r) {
        callLater(plugin, delay, false, r);
    }

    public void call(@NotNull Plugin plugin, boolean async, BukkitRunnable r){
        this.tasks.put(plugin.getName().toLowerCase() + "_"+ Utils.getCurrentTimeInSeconds(), async ? r.runTaskAsynchronously(plugin) : r.runTask(plugin));
    }

    public void call(Plugin plugin, BukkitRunnable r){
        this.call(plugin,false,r);
    }

    public void callRepeating(@NotNull Plugin plugin, long repeatInTicks, long repeatDelay, boolean async, BukkitRunnable r){
        this.tasks.put(plugin.getName().toLowerCase() + "_"+ Utils.getCurrentTimeInSeconds(), async ? r.runTaskTimerAsynchronously(plugin, repeatDelay, repeatInTicks) : r.runTaskTimer(plugin, repeatDelay, repeatInTicks));
    }

    public void callRepeating(Plugin plugin, long repeatInTicks, boolean async, BukkitRunnable r){
        callRepeating(plugin, repeatInTicks, 0L, async, r);
    }

    public void callRepeating(Plugin plugin, long repeatInTicks, long repeatDelay, BukkitRunnable r){
        callRepeating(plugin, repeatInTicks, repeatDelay, false, r);
    }

    public void callRepeating(Plugin plugin, long repeatInTicks, BukkitRunnable runnable){
        callRepeating(plugin, repeatInTicks, 0L, false, runnable);
    }

    public void callLater(String id, Plugin plugin, long delay, boolean async, BukkitRunnable r){
        this.tasks.put(id, async ? r.runTaskLaterAsynchronously(plugin, delay) : r.runTaskLater(plugin, delay));
    }

    public void callLater(String id, Plugin plugin, long delay, BukkitRunnable r) {
        callLater(id, plugin, delay, false, r);
    }


    public void cancel(String id){
        if(!tasks.containsKey(id)) return;

        tasks.get(id).cancel();
        tasks.remove(id);
    }

    public void cancelAll(){
        for(String id  : tasks.keySet()){
            cancel(id);
        }
    }
}
