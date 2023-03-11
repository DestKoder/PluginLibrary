package ru.dest.library.utils;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Some utils for coding
 *
 * @author DestKoder
 * @since 1.0
 */
public final class Utils {

    /**
     * Converts hex String to Bukkit Color
     * @param hex {@link String} with hex code (#000000)
     * @return the resulting {@link Color}
     */
    @Contract("_ -> new")
    public static @NotNull Color getColorFromHexString(@NotNull String hex){
        return Color.fromRGB(Integer.valueOf( hex.substring( 1, 3 ), 16 ),
                Integer.valueOf( hex.substring( 3, 5 ), 16 ),
                Integer.valueOf( hex.substring( 5, 7 ), 16 ));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull Color forRGB(int r, int g, int b){
        return Color.fromRGB(r,g,b);
    }
    /**
     * Transforms string array to a single string
     * @param args - array of strings
     * @return resulting string
     */
    @NotNull
    public static String argsToMessage(String @NotNull [] args){
        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    /**
     * Transforms string array to a single string with start position
     * @param args - array of strings
     * @param startPos - number of the element from which need start
     * @return resulting string
     */
    public static @NotNull String argsToMessage(String @NotNull [] args, int startPos){
        StringBuilder sb = new StringBuilder();

        for(int i =startPos ;i < args.length; i ++){
            sb.append(args[i]);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    /**
     * @return the number of seconds elapsed since 1 jan 1970
     */
    @Deprecated
    public static long getCurrentTimeInSeconds(){
        return TimeUtils.getUnixTime();
    }

    /**
     * Execute method if player has permission
     * @param player player to check for permission
     * @param permission permission to check
     * @param function methods which will execute
     */
    public static void executeIfHas(@NotNull Player player,@NotNull String permission,@NotNull Consumer<Player> function){
        if(player.hasPermission(permission)) function.accept(player);
    }

    public static <T> void executeIf(@NotNull T t,@NotNull Consumer<T> c, @NotNull Predicate<T> p){
        if(p.test(t)){
            c.accept(t);
        }
    }

    public static <T> void forEachIf(@NotNull Collection<T> collection, @NotNull Consumer<T> c, @NotNull Predicate<T> p){
        for(T t : collection){
            executeIf(t, c, p);
        }
    }

    /**
     * Calculate left time for pointed timestamp
     * @param expires - timestamp
     * @return 0 if the specified time has already passed or the number of seconds before it
     */
    public static long calcLeftTime(long expires){
        long current = getCurrentTimeInSeconds();

        if(current >= expires){
            return 0;
        }else return expires - current;
    }

    @Contract(pure = true)
    @SafeVarargs
    public static <T> @NotNull List<T> newList(T... values){
        return Arrays.asList(values);
    }

    public static void fillStatement(PreparedStatement stmt, Object @NotNull [] data) throws SQLException {
        for(int index = 0; index < data.length; index++){
            stmt.setObject(index+1, data[index]);
        }
    }

    @NotNull
    public Location centralize(@NotNull Location location){
        return new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()).add(0.5, 0, 0.5);
    }
}
