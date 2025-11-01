package ru.dest.library.lang;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.ICanBeUsedAsKickReason;
import ru.dest.library.object.IFormatAble;
import ru.dest.library.object.ISendAble;

/**
 * Describe a message which can be sent to a player or console
 */
public interface Message extends ISendAble, IFormatAble<Message>, ICanBeUsedAsKickReason {
    @NotNull
    Message setHoverText(@NotNull String text);
    @NotNull
    Message setRunCommandOnClick(@NotNull String cmd);
    @NotNull
    Message setSuggestCommandOnClick(@NotNull String cmd);
    @NotNull
    Message setOpenUrlOnClick(@NotNull String url);
    @NotNull
    Message setCopyTextOnClick(@NotNull String text);
    @NotNull
    String getRaw();

    @NotNull
    Message add(@NotNull Message message);
}
