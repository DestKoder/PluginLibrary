package ru.dest.library.command;

import org.reflections.Reflections;
import ru.dest.library.command.ann.SubCommand;
import ru.dest.library.ioc.IOCUtils;
import ru.dest.library.ioc.IgnoreAutoRegister;
import ru.dest.library.listener.PluginListener;
import ru.dest.library.plugin.IPlugin;
import ru.dest.library.plugin.IRegistry;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Realization of {@link IRegistry} which supports auto-registering
 */
@SuppressWarnings("unchecked")
public abstract class ModernRegistrar<T extends IPlugin<?>> implements IRegistry<T> {

    private final T plugin;

    public ModernRegistrar(T plugin) {
        this.plugin = plugin;
    }

//    public abstract void register(BaseCommand<T> cmd);

    private final Map<BaseCommand<T>, Class<? extends CommandManager<?>>> subCommands = new HashMap<>();
    private final Map<Class<? extends BaseCommand>, CommandManager<T>> commandManagers = new HashMap<>();

    public void onPluginEnable(){
        String packageName = IOCUtils.getSearchPackage(plugin.getClass());

        Reflections scanner = new Reflections(packageName);

        //Ищем все команды
        for(Class<? extends BaseCommand> commandClass : scanner.getSubTypesOf(BaseCommand.class)){
            if(Modifier.isAbstract(commandClass.getModifiers())) continue;
            if(commandClass.isAnnotationPresent(IgnoreAutoRegister.class)) continue;
            BaseCommand<T> command;

            //Получаем объект команды
            try{
                command = (BaseCommand<T>) commandClass.getConstructor(this.plugin.getClass()).newInstance(plugin);
            }catch (Exception e){
                this.plugin.logger().warning("Couldn't register plugin command " + commandClass + ": "+ e.getMessage());
                continue;
            }
            //Проверяем является ли наша команда Под коммандой какого нить менеджера команд.
            if(commandClass.isAnnotationPresent(SubCommand.class)){
                SubCommand ann = commandClass.getDeclaredAnnotation(SubCommand.class);
                this.subCommands.put(command, ann.manager());
            }else{
                //Если нет регистрируем команду
                register(command);
            }

            //Если эта команда - менеджер команд - сохраняем её на будущее
            if(command instanceof CommandManager) commandManagers.put(commandClass, (CommandManager<T>) command);
        }
        //Бежимся по всем под командам

        subCommands.forEach((cmd, manager) -> {
            if(!commandManagers.containsKey(manager)) {
                plugin.logger().warning("Couldn't register sub command " + cmd.getClass() +": CommandManager not found");
            }
            commandManagers.get(manager).register(cmd);
        });

        subCommands.clear();
        commandManagers.clear();

        //Ищем слушатели
        for(Class<? extends PluginListener> lC : scanner.getSubTypesOf(PluginListener.class)){
            //Пропускаем класс если он абстрактный / на нём флаг игнорирования автоматической регситрации.
            if(Modifier.isAbstract(lC.getModifiers())) continue;
            if(lC.isAnnotationPresent(IgnoreAutoRegister.class)) continue;

            PluginListener<T> listener;

            //Пробуем создать объект слушателя для нашего плагина
            try{
                listener = lC.getConstructor(this.plugin.getClass()).newInstance(this.plugin);
            }catch (Exception e){
                this.plugin.logger().warning("Couldn't register plugin listener " + lC + ": "+ e.getMessage());
                continue;
            }
            //Регистрируем слушатель
            this.register(listener);

        }
    }

}
