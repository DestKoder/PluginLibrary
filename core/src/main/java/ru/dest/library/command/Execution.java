package ru.dest.library.command;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.command.argument.ArgumentTypes;
import ru.dest.library.command.argument.IArgumentType;
import ru.dest.library.lang.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Info about command execution
 */
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

    /**
     * Send message to executor
     * @param message {@link Message} to send
     */
    public void answer(@NotNull Message message){
        message.send(executor);
    }

    /**
     * Command which is executing
     * @return
     */
    public ICommand<?> command(){
        return command;
    }

    /**
     * Get alias of command
     */
    public String alias(){
        return alias;
    }

    /**
     * Executor of command
     */
    @SuppressWarnings("unchecked")
    public <OBJ> OBJ executor() {
        return (OBJ) executor;
    }

    /**
     * Get passes arguments
     */
    public String[] arguments() {
        return arguments;
    }

    /**
     * Get specified argument by number
     * @param arg arg number starting from 0
     */
    public String argument(int arg) {
        if(arg >= arguments.length) throw new IllegalArgumentException("arg out of bounds");
        return arguments[arg];
    }

    /**
     * Get specified argument by type and number
     * @param arg argument number starting from 0
     * @param cl argument type class
     */
    public <T> T argument(int arg, Class<T> cl){
        if(arg >= arguments.length) throw new IllegalArgumentException("arg out of bounds");
        IArgumentType<T> type = ArgumentTypes.getType(cl);
        if(type == null) throw new IllegalArgumentException("No such argument type");

        if(!type.isValid(argument(arg))) throw new IllegalArgumentException("Argument " + arg + " is not valid for type " + cl);

        return type.get(argument(arg));
    }

    /**
     * Get Online Player from arguments
     * @param arg number of argument which contains player name
     */
    @SuppressWarnings("unchecked")
    public <OBJ> OBJ onlinePlayer(int arg) {
        return (OBJ) Library.get().getMethods().getOnlinePlayer(argument(arg));
    }

    /**
     * Get Offline player from argument
     * @param arg number of argument which contains player name
     */
    @SuppressWarnings("unchecked")
    public <OBJ> OBJ offlinePlayer(int arg) {
        return (OBJ) Library.get().getMethods().getOfflinePlayer(argument(arg));
    }

    /**
     * Checks if executor has a permission
     * @param permission permission to check
     * @return true if has or false in other cases
     */
    public boolean hasPermission(String permission){
        return Library.get().getMethods().checkPermission(executor, permission);
    }

    /**
     * Check is flag was passed
     * @param flag flag to check
     */
    public boolean hasFlag(String flag) {
        return flags.containsKey(flag);
    }

    /**
     * Get flag value
     * @param flag flag to check
     */
    public String flag(String flag) {
        return flags.getOrDefault(flag, null);
    }

    /**
     * Get all passes flags
     */
    public Map<String, String> flags() {
        return flags;
    }
}
