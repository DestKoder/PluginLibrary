package ru.dest.library.bukkit.command;

import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.command.CommandManager;
import ru.dest.library.command.ann.Command;


@Command(value = "pluginlibrary", permissions = "pluginlibrary.admin", aliases = {"pluginlib", "dklib"})
public class LibraryCommand extends CommandManager<LibraryMain> {

    public LibraryCommand(LibraryMain plugin) {
        super(plugin);
    }

}
