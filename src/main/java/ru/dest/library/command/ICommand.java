package ru.dest.library.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand<T extends JavaPlugin>{

    void execute(CommandData data, String[] args);
    @NotNull
    T getPlugin();
    @NotNull
    List<String> getAliases();
}
