package ru.dest.library.command;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;

import java.util.HashMap;
import java.util.Map;

public final class Execution {

    private final Object executor;
    private final String[] arguments;
    private final ICommand<?> command;
    private final String alias;

    private final Map<String, String> flags;

    public Execution(Object sender, ICommand<?> cmd, String alias, String @NotNull [] args) {
        this.executor = sender;
        this.command = cmd;
        this.alias = alias;

        this.flags = new HashMap<>();
        int counter = 0;
        for(String arg : args){
            if(!arg.startsWith("-")) break;
            if(!arg.contains("=") && !arg.contains(":")){
                flags.put(arg.substring(1), "true");
                counter++;
                continue;
            }

            String[] data = arg.contains("=") ? arg.substring(1).split("=") : arg.substring(1).split(":");
            flags.put(data[0], data[1]);
            counter++;
        }

        this.arguments = new String[args.length - counter];
        if(args.length - counter > 0) {
            System.arraycopy(args, counter, this.arguments, 0, args.length - counter);
        }
    }



    @SuppressWarnings("unchecked")
    public <OBJ> OBJ executor() {
        return (OBJ) executor;
    }

    public String[] arguments() {
        return arguments;
    }

    public String argument(int arg) {
        if(arg >= arguments.length) throw new IllegalArgumentException("arg out of bounds");
        return arguments[arg];
    }
    @SuppressWarnings("unchecked")
    public <OBJ> OBJ onlinePlayer(int arg) {
        return (OBJ) Library.get().getMethods().getOnlinePlayer(argument(arg));
    }

    @SuppressWarnings("unchecked")
    public <OBJ> OBJ offlinePlayer(int arg) {
        return (OBJ) Library.get().getMethods().getOfflinePlayer(argument(arg));
    }

    public boolean hasPermission(String permission){
        return Library.get().getMethods().checkPermission(executor, permission);
    }

    public boolean hasFlag(String flag) {
        return flags.containsKey(flag);
    }

    public String flag(String flag) {
        return flags.getOrDefault(flag, null);
    }


    public Map<String, String> flags() {
        return flags;
    }
}
