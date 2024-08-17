package ru.dest.library.bukkit;

import lombok.Getter;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitTask;
import ru.dest.library.Library;
import ru.dest.library.bukkit.listener.EventListener;
import ru.dest.library.bukkit.listener.GUIListener;
import ru.dest.library.bukkit.listener.ItemsListener;
import ru.dest.library.command.argument.ArgumentTypes;
import ru.dest.library.command.argument.IArgumentType;
import ru.dest.library.lang.Lang;
import ru.dest.library.lang.impl.ComponentMessage;
import ru.dest.library.plugin.MinecraftPlugin;
import ru.dest.library.utils.TimeUtils;

import java.util.*;


import static ru.dest.library.utils.Utils.list;

public class LibraryMain extends MinecraftPlugin<LibraryMain, LibraryConfig> implements Listener {

    @Getter
    private static LibraryMain instance;

    @Getter
    private boolean papiEnable;

    @Getter
    private AudienceProvider audienceProvider;

    private ItemStack skullItem;

    private final Map<Long, Runnable> syncActions = new HashMap<>();

    @Override
    public void load() throws Exception {}

    private BukkitTask syncTask;
    @Getter
    private NamespacedKey itemId;

    @Override
    public void enable() throws Exception {

        try{
            Player.class.getMethod("sendMessage", Component.class);
            logger().info("Detected build-in net.kyori.adventure provider");
        }catch (NoSuchMethodException | SecurityException e){
            //Raw bukkit
            audienceProvider =  BukkitAudiences.create(this);
            logger().info("Initialized Bukkit net.kyori.adventure provider");
        }
        Lang.registerSerializer(config().getMessageType());
        ArgumentTypes.register(Player.class, new IArgumentType() {
            @Override
            public boolean isValid(String arg) {
                return Bukkit.getPlayer(arg) != null;
            }

            @Override
            public List<String> getCompletions() {
                return list(Bukkit.getOnlinePlayers(), Player::getName);
            }
        });

        try{
            skullItem = new ItemStack(Objects.requireNonNull(Material.getMaterial(config().getMaterialHead())));
            SkullMeta meta= (SkullMeta)skullItem.getItemMeta();
        }catch (Exception e){
            logger().warning("Invalid SkullItem Material. Some functionality will be disabled");
            skullItem = null;
        }


        new Library(logger(), new BukkitMethods(), getDataFolder());

        Library.get().setConsoleOnlyMessage(new ComponentMessage(config().getMessageConsoleonly()));
        Library.get().setPlayerOnlyMessage(new ComponentMessage(config().getMessagePlayeronly()));
        Library.get().setNoPermissionMessage(new ComponentMessage(config().getMessageNopermission()));
        Library.get().setInternalErrorMessage(new ComponentMessage(config().getMessageInternalError()));
        Library.get().setPlayerNotFoundMessage(new ComponentMessage(config().getMessagePlayerNotFound()));

        registry().register(new ItemsListener(this));
        registry().register(new GUIListener(this));
        registry().register(new EventListener(this));
        getServer().getPluginManager().registerEvents(this, this);


        this.itemId = new NamespacedKey(this, "itemId");

        instance = this;

        startTasks();
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event){
        if(event.getPlugin().getName().equals("PlaceholderAPI")){
            papiEnable = true;
        }
    }


    private void startTasks(){
        syncTask = Bukkit.getScheduler().runTaskTimer(this, () -> {
            for(long l  : syncActions.keySet()){
                syncActions.get(l).run();
                syncActions.remove(l);
            }
        }, 0L,10L);
    }

    public void sync(Runnable r){
        this.syncActions.put(TimeUtils.getCurrentUnixTime(), r);
    }

    @Override
    public void disable() throws Exception {
        if(audienceProvider != null){
            audienceProvider.close();
        }
        if(syncTask != null) syncTask.cancel();
    }

    public ItemStack skullItem(){
        if(skullItem == null) return null;
        return skullItem.clone();
    }

    @Override
    public Class<LibraryConfig> configClass() {
        return LibraryConfig.class;
    }
}