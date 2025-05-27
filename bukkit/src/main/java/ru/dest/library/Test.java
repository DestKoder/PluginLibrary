package ru.dest.library;

import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.command.BaseCommand;
import ru.dest.library.command.Execution;
import ru.dest.library.command.ann.Command;

public class Test extends BaseCommand<LibraryMain> {

    public Test(LibraryMain plugin) {
        super(plugin);
    }

    @Override
    public void execute(Execution execution) throws Exception {

    }
}
