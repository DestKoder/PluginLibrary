package ru.dest.library.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CommandData {

    private final Map<String, String> flags = new HashMap<>();
    private final CommandSender sender;
    private final String[] args;
    private final Command command;
    private final String label;

    public CommandData(String @NotNull [] args, CommandSender sender, Command command, String label){
        this.sender = sender;
        this.command = command;
        this.label = label;
        int counter = 0;
        for(String arg : args){
            if(!arg.startsWith("-")) break;
            if(!arg.contains("=")){
                flags.put(arg.substring(1), "true");
                counter++;
                continue;
            }

            String[] data = arg.substring(1).split("=");

            flags.put(data[0], data[1]);
            counter++;
        }

        this.args = new String[args.length - counter];
        if (args.length - counter >= 0) System.arraycopy(args, counter, this.args, 0, args.length - counter);
    }

    public Player getSenderAsPlayer(){
        if(!(sender instanceof Player))return null;
        return (Player) sender;
    }
    /**
     * Get this object as String[]
     * @return {@link CommandData} as String array;
     */
    @NotNull
    public String[] getArgs() {return args;}

    /**
     * Gets value of flag
     * @param flagName flag which value we want to get
     * @return value of flag or null if flag not present
     */
    @Nullable
    public String getFlagValue(String flagName){
        return flags.get(flagName);
    }

    /**
     * Checks if command has a flag
     * @param flagName Name of flag to check
     * @return true if such flag provided or false another ways
     */
    public boolean hasFlag(String flagName){
        return flags.containsKey(flagName);
    }

    public Command getBukkitCommand(){return command;}

    public String getLabel() {
        return label;
    }

    public CommandSender getSender() {
        return sender;
    }
}
