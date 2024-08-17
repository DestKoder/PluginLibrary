package ru.dest.library.command;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.ISendAble;
import ru.dest.library.plugin.IPlugin;

import java.util.ArrayList;
import java.util.List;


public abstract class CommandManager<T extends IPlugin<?>> extends BaseCommand<T>{

    private final List<ICommand<T>> subCommands = new ArrayList<>();

    public CommandManager(T plugin) {
        super(plugin);
    }

    public CommandManager(T plugin, String name, String... args) {
        super(plugin, name, args);
    }

    public CommandManager(T plugin, String name, ISendAble usage, String... args) {
        super(plugin, name, usage, args);
    }

    public void register(ICommand<T> cmd){
        this.subCommands.add(cmd);
    }

    public ICommand<T> findSub(String name){
        for(ICommand<T> cmd : subCommands){
            if(cmd.getName().equalsIgnoreCase(name) || cmd.getAliases().contains(name)) return cmd;
        }
        return null;
    }

    protected void __default(Execution execution){
        if(getUsage()!= null) getUsage().send(execution.executor());
    }

    @Override
    public final void execute(@NotNull Execution execution) throws Exception {
        if(execution.arguments().length < 1){
            __default(execution);
            return;
        }

        ICommand<T> sub = findSub(execution.argument(0));

        if(sub == null){
            __default(execution);
            return;
        }

        String[] arguments = new String[execution.arguments().length - 1];
        System.arraycopy(execution.arguments(), 1, arguments, 0, execution.arguments().length-1);
        Execution newExec = new Execution(execution.executor(), sub, execution.argument(0), arguments);

        if(sub instanceof BaseCommand){
            ((BaseCommand<T>)sub).onExecute(newExec);
            return;
        }else sub.execute(newExec);
    }

    @Override
    public List<String> getTabCompletions(@NotNull Execution execution) {
        if(execution.arguments().length == 1){
            List<String> result = new ArrayList<>();

            for(ICommand<T> cmd : subCommands){
                if(!(cmd instanceof BaseCommand)) {
                    result.add(cmd.getName());
                    result.addAll(cmd.getAliases());
                    continue;
                }
                BaseCommand<T> command = (BaseCommand<T>) cmd;
                if(command.getPermissions() == null ){
                    result.add(cmd.getName());
                    result.addAll(cmd.getAliases());
                    continue;
                }

                boolean pass = false;
                for(String s : command.getPermissions()){
                    if(execution.hasPermission(s)){
                        pass = true;
                        break;
                    };
                }
                if(pass){
                    result.add(cmd.getName());
                    result.addAll(cmd.getAliases());
                    continue;
                }
            }
            return result;
        }
        if(execution.arguments().length > 1){
            ICommand<T> cmd = findSub(execution.argument(0));
            if(!(cmd instanceof ITabCompletableCommand)) return super.getTabCompletions(execution);
            String[] arguments = new String[execution.arguments().length - 1];
            System.arraycopy(execution.arguments(), 1, arguments, 0, execution.arguments().length-1);
            Execution newExec = new Execution(execution.executor(), cmd, execution.argument(0), arguments);
            return ((ITabCompletableCommand)cmd).getTabCompletions(newExec);
        }
        return super.getTabCompletions(execution);
    }


}
