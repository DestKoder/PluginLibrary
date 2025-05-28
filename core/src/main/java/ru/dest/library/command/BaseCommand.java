package ru.dest.library.command;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.command.ann.*;
import ru.dest.library.command.argument.ArgumentTypes;
import ru.dest.library.object.ISendAble;
import ru.dest.library.plugin.IPlugin;
import ru.dest.library.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        SubCommand subAnn = getClass().getDeclaredAnnotation(SubCommand.class);
        if(ann == null && subAnn == null) throw new IllegalStateException("BaseCommand must have a @Command or @SubCommand annotation");

        this.name = ann != null ? ann.value() : subAnn.name();
        if(plugin.hasLang()){
            this.usage = plugin.lang().getMessage("usage."+name);
        }else usage = null;

        this.aliases = list(ann != null ? ann.aliases() : subAnn.aliases() );
        if(ann != null && ann.permissions().length > 0) {
            this.permissions = ann.permissions();
        }else if(subAnn != null && subAnn.permissions().length > 0) {
            this.permissions = subAnn.permissions();
        }else this.permissions=null;

        this.arguments = ann != null ? ann.args() : subAnn.args();

        this.playerOnly = ann != null ? ann.playerOnly() : subAnn.playerOnly();
        this.consoleOnly = ann != null ? ann.consoleOnly() : subAnn.consoleOnly();
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
        if(getPermissions() != null && !Utils.checkOneOf(execution::hasPermission, getPermissions())){
            Library.get().getNoPermissionMessage().send(execution.executor());
            return;
        }

        if(getArguments() != null){
            if(execution.arguments().length < arguments.length){
                getUsage().send(execution.executor());
                return;
            }
            if(!ArgumentTypes.validate(execution, arguments)) return;
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

        return ArgumentTypes.getCompletions(argType, execution.argument(arg-1));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BaseCommand<?> that = (BaseCommand<?>) object;
        return playerOnly == that.playerOnly && consoleOnly == that.consoleOnly && Objects.equals(plugin, that.plugin) && Objects.equals(name, that.name) && Objects.equals(usage, that.usage) && Objects.equals(aliases, that.aliases) && Objects.deepEquals(permissions, that.permissions) && Objects.deepEquals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, name, usage, aliases, Arrays.hashCode(permissions), Arrays.hashCode(arguments), playerOnly, consoleOnly);
    }
}
