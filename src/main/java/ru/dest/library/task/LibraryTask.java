package ru.dest.library.task;

import org.bukkit.scheduler.BukkitRunnable;

public class LibraryTask extends BukkitRunnable {

    private final Runnable onRun;

    public LibraryTask(Runnable onRun) {
        this.onRun = onRun;
    }

    @Override
    public void run() {
        onRun.run();
    }
}
