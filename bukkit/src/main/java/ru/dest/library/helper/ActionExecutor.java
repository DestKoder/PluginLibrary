package ru.dest.library.helper;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.dest.library.utils.ColorUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@UtilityClass
public class ActionExecutor {

    private Map<String, BiConsumer<Player, String>> actions = new HashMap<>();

    public void executeAction(Player player, String action) {
        for(String s : actions.keySet()){
            if(action.startsWith(s)){
                actions.get(s).accept(player, action.substring(s.length()));
            }
        }
    }

    static {
        actions.put("[command]", Bukkit::dispatchCommand);
        actions.put("[console]", (pl, cmd) -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd));
        actions.put("[message]", (pl, msg) -> pl.sendMessage(ColorUtils.parse(msg)));
    }

    public void register(String action, BiConsumer<Player, String> handler){
        actions.put("["+action+"]", handler);
    }

}
