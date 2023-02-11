package ru.dest.library.command;

import org.bukkit.plugin.java.JavaPlugin;

public interface ManagerCommand<T extends JavaPlugin> extends ICommand<T>{

    void addSubCommand(ICommand<T> cmd);
}
