package ru.dest.library.lang;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.object.Message;
import ru.dest.library.object.Title;

public interface Lang {

    /**
     * Get message as string, if you want to get title add .title or .subtitle
     * @param key Key of needed Message
     * @return Message as raw String without placeholders support etc. or null if no message provided with such key
     */
    String getRawString(String key);

    /**
     * Get message from file
     * @param key Key of needed Message
     * @return {@link Message} from file or null if key not exists;
     */
    @Nullable
    Message getMessage(String key);
    @Nullable
    Title getTitle(String key);

    @Nullable
    Message getMessage(String key, Player placeholder);
    @Nullable
    Title getTitle(String key, Player placeholder);
}
