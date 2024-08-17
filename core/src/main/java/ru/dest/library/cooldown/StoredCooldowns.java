package ru.dest.library.cooldown;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.config.ConfigWorker;
import ru.dest.library.config.DataConfig;
import ru.dest.library.object.Pair;
import ru.dest.library.plugin.IPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StoredCooldowns extends Cooldowns{

    private final File dataFile;

    public StoredCooldowns(@NotNull IPlugin<?> plugin, File dataFile) throws IOException {
        super(plugin);
        this.dataFile = dataFile;

        if(!dataFile.exists()) {
            FileUtils.createParentDirectories(dataFile);
            dataFile.createNewFile();
        }
    }

    public void load() throws IOException {
        super.data.clear();
        DataConfig config = ConfigWorker.load(dataFile);

        config.getKeys(false).forEach(uuidData -> {
            UUID uuid = UUID.fromString(uuidData);
            List<Pair<String, Long>> data = new ArrayList<>();

            ((Map<String,Object>)config.get(uuidData)).forEach((key, val) -> {
                data.add(new Pair<>(key, (Long)val));
            });
            super.data.put(uuid, data);
        });
    }

    public void save() throws IOException{
        DataConfig cfg = new DataConfig(new HashMap<>());
        super.data.forEach((uuid, dataList) -> {
            String dataKey = uuid.toString();
            dataList.forEach(data -> {
                cfg.set(dataKey +"." + data.getFirstValue(), data.getSecondValue());
            });
        });

        ConfigWorker.save(dataFile, cfg);
    }
}
