package ru.dest.library;

public interface ITaskManager {

    /**
     * Call a parallel task
     * @param r task to call
     * @return internal id of task (can be used in {@link ITaskManager#cancel})
     */
    int call(Runnable r);

    /**
     * Call an async parallel task
     * @param r task to call
     * @return internal id of task (can be used in {@link ITaskManager#cancel})
     */
    int callAsync(Runnable r);

    /**
     * Calls a parallel task after the specified time
     * @param r task to call
     * @param time wait time
     * @return internal id of task (can be used in {@link ITaskManager#cancel})
     */
    int callLater(Runnable r, long time);

    /**
     * Calls an async parallel task after the specified time
     * @param r task to call
     * @param time wait time
     * @return internal id of task (can be used in {@link ITaskManager#cancel})
     */
    int callLaterAsync(Runnable r, long time);

    /**
     * Calls repeating task
     * @param r task to call
     * @param time repeat int ticks
     * @return internal id of task (can be used in {@link ITaskManager#cancel})
     */
    int callRepeating(Runnable r, long time);

    /**
     * Calls async repeating task
     * @param r task to call
     * @param time repeat int ticks
     * @return internal id of task (can be used in {@link ITaskManager#cancel})
     */
    int callRepeatingAsync(Runnable r, long time);

    void cancel(int id);
    void cancelAll();

}
