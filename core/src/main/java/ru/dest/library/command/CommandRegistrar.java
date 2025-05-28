package ru.dest.library.command;

import org.reflections.Reflections;
import ru.dest.library.command.ann.SubCommand;
import ru.dest.library.ioc.IOCUtils;
import ru.dest.library.plugin.IPlugin;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class CommandRegistrar<T extends IPlugin<?>> {

    private final T plugin;

    public CommandRegistrar(T plugin) {
        this.plugin = plugin;
    }

    public abstract void register(BaseCommand<T> cmd);

    private final Map<BaseCommand<T>, Class<? extends CommandManager<T>>> subCommands = new HashMap<>();
    private final Map<Class<? extends BaseCommand<T>>, BaseCommand<T>> commandManagers = new HashMap<>();

    public void onPluginEnable(){
        String packageName = IOCUtils.getSearchPackage(plugin.getClass());

        Reflections scanner = new Reflections(packageName);

        for(Class<? extends BaseCommand<T>> commandClass : scanner.getSubTypesOf(BaseCommand.class)){
            BaseCommand<T> command;
            try{
                command = commandClass.getConstructor(this.plugin.getClass()).newInstance(plugin);
            }catch (Exception e){
                this.plugin.logger().warning("Couldn't register plugin command " + commandClass + ": "+ e.getMessage());
                continue;
            }

            if(commandClass.isAnnotationPresent(SubCommand.class)){
                SubCommand ann = commandClass.getDeclaredAnnotation(SubCommand.class);
                this.subCommands.put(command, (Class<? extends CommandManager<T>>) ann.manager());
            }
            if(command instanceof  CommandManager) commandManagers.put(commandClass, command);
        }

        for(BaseCommand<T> subCommand : subCommands.keySet()){
            if(!commandManagers.containsKey(subCommands.get(subCommand))) {
                plugin.logger().warning("Couldn't register sub command " + subCommand.getClass() +": CommandManager not found");
                continue;
            }

            ((CommandManager<T>)commandManagers.get(subCommands.get(subCommand))).register(subCommand);
        }
        subCommands.clear();
        commandManagers.clear();
    }

}
