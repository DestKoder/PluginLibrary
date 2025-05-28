package ru.dest.library.command;


public interface IExecution<SENDER> {

    <T extends SENDER> T executor();

    String[] arguments();

    String argument(int arg);

    <T> T argument(int arg, Class<T> type);

    String alias();

    String flag(String flag);

    boolean hasFlag(String flag);


}
