package ru.dest.library.command;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.command.ann.*;
import ru.dest.library.command.argument.ArgumentTypes;
import ru.dest.library.object.ISendAble;
import ru.dest.library.plugin.IPlugin;

import java.util.Arrays;
import java.util.List;

import static ru.dest.library.utils.Utils.list;

public abstract class BaseCommand<T extends IPlugin<?>> implements ICommand<T>, ITabCompletableCommand{
    @Getter
    protected final T plugin;

    private final String name;
    private final ISendAble usage;
    private final List<String> aliases;

    @Getter
    private final String[] permissions;
    @Getter
    private final Class<?>[] arguments;
    @Getter
    private final boolean playerOnly, consoleOnly;

    public BaseCommand(T plugin){
        this.plugin = plugin;
        Command ann = getClass().getDeclaredAnnotation(Command.class);
        if(ann == null) throw new IllegalStateException("BaseCommand must have a @Command annotation");

        this.name = ann.value();
        if(plugin.hasLang()){
            this.usage = plugin.lang().getMessage("usage."+name);
        }else usage = null;

        this.aliases = list(ann.aliases());
        if(ann.permissions().length > 0) {
            this.permissions = ann.permissions();
        }else this.permissions = null;

        if(ann.args().length > 0){
            this.arguments = ann.args();
        }else this.arguments = null;

        this.playerOnly = ann.playerOnly();
        this.consoleOnly = ann.consoleOnly();
    }

    public BaseCommand(T plugin, String name, String... args) {
        this.plugin = plugin;
        this.name = name;
        this.usage = null;
        this.aliases = Arrays.asList(args);

        if(getClass().isAnnotationPresent(Permission.class)){
            this.permissions = getClass().getDeclaredAnnotation(Permission.class).value();
        }else this.permissions = new String[0];

        if(getClass().isAnnotationPresent(Arguments.class)){
            this.arguments = getClass().getDeclaredAnnotation(Arguments.class).value();
        }else this.arguments = new Class<?>[0];

        this.playerOnly = getClass().isAnnotationPresent(PlayerOnly.class);
        this.consoleOnly = getClass().isAnnotationPresent(ConsoleOnly.class);
    }

    public BaseCommand(T plugin, String name, ISendAble usage, String... args) {
        this.plugin = plugin;
        this.name= name;
        this.usage = usage;
        this.aliases = Arrays.asList(args);

        if(getClass().isAnnotationPresent(Permission.class)){
            this.permissions = getClass().getDeclaredAnnotation(Permission.class).value();
        }else this.permissions = new String[0];

        if(getClass().isAnnotationPresent(Arguments.class)){
            this.arguments = getClass().getDeclaredAnnotation(Arguments.class).value();
        }else this.arguments = new Class<?>[0];

        this.playerOnly = getClass().isAnnotationPresent(PlayerOnly.class);
        this.consoleOnly = getClass().isAnnotationPresent(ConsoleOnly.class);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ISendAble getUsage() {
        return usage;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    public void onExecute(Execution execution) throws Exception {
        //permission check
        if(getPermissions() != null){
            boolean pass = false;
            for(String s : getPermissions()){
                if(execution.hasPermission(s)) {
                    pass = true;
                    break;
                }
            }
            if(!pass){
                Library.get().getNoPermissionMessage().send(execution.executor());
                return;
            }

        }

        //arguments check
        if(getArguments() != null){
            boolean pass = true;
            if((execution.arguments().length < getArguments().length)){
                pass = false;
            }else {
                for(int i = 0; i < getArguments().length; i ++ ){
                    String arg = execution.argument(i);
                    Class<?> argType = getArguments()[i];

                    pass = ArgumentTypes.check(argType, arg);
                }
            }
            if(!pass) {
                getUsage().send(execution.executor());
                return;
            }
        }

        execute(execution);
    }


    @Override
    public abstract void execute(Execution execution) throws Exception;

    @Override
    public List<String> getTabCompletions(@NotNull Execution execution) {
        int arg = execution.arguments().length;

        if(arguments == null) return list();

        if(arg > arguments.length) return list();
        Class<?> argType = arguments[arg-1];

        return ArgumentTypes.getCompletions(argType);
    }
}
