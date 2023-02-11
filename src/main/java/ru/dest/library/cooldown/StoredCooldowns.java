package ru.dest.library.cooldown;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a manager for file-stored cooldowns
 *
 * @since 1.0
 * @author DestKoder
 */
public class StoredCooldowns extends Cooldowns{

    private File data;

    public StoredCooldowns(Plugin plugin, @NotNull File data) throws FileNotFoundException {
        super(plugin);
        this.data = data;

        if(!data.exists()) throw new FileNotFoundException("Data file isn't exists!");
    }

    public void loadCooldowns() {
        super.data.clear();

        FileConfiguration config = YamlConfiguration.loadConfiguration(data);

        config.getKeys(false).forEach(uuidData -> {
            UUID uuid = UUID.fromString(uuidData);
            List<CooldownData> data = new ArrayList<>();

            config.getConfigurationSection(uuidData).getKeys(false).forEach(action -> {
                data.add(new CooldownData(action, config.getLong(uuidData + "." + action)));
            });

            super.data.put(uuid, data);
        });
    }

    public void saveCooldowns() throws IOException {
        FileConfiguration config = new YamlConfiguration();

        super.data.forEach((uuid, data) -> {
            String dataKey = uuid.toString();

            data.forEach(cooldownData ->  {
                config.set(dataKey + "." + cooldownData.getAction(), cooldownData.getExpires());
            });
        });

        config.save(data);
    }
}
