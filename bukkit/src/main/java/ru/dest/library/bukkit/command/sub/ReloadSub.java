package ru.dest.library.bukkit.command.sub;

import org.bukkit.plugin.Plugin;
import ru.dest.library.bukkit.LibraryMain;
import ru.dest.library.command.BaseCommand;
import ru.dest.library.command.Execution;
import ru.dest.library.command.ann.Command;
import ru.dest.library.plugin.IPlugin;
import ru.dest.library.plugin.MinecraftPlugin;


@Command(value = "reload", args = {String.class})
public class ReloadSub extends BaseCommand<LibraryMain> {

    public ReloadSub(LibraryMain plugin) {
        super(plugin);
    }

    @Override
    public void execute(Execution execution) throws Exception {
        String pName = execution.argument(0);

        Plugin p = plugin.getServer().getPluginManager().getPlugin(pName);

        if(p==null) {
            //No Such plugin
            execution.answer(plugin.lang().getMessage("command.reload.no-such-plugin").format("plugin", pName));
            return;
        }
        if(!(p instanceof IPlugin)) {
            execution.answer(plugin.lang().getMessage("command.reload.not-library-plugin").format("plugin", pName));
            return;
        }

    }
}
