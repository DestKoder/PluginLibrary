package ru.dest.library.exception;

public class CommandNotFoundException extends RuntimeException{

    public CommandNotFoundException(String name) {
        super("Command with name " + name + " not found. If you want to use ConfigCommand you must provide name and aliases of command in plugin.yml");
    }
}
