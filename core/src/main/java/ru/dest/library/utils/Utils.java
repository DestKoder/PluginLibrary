package ru.dest.library.utils;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.Library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Some common utils for coding
 *
 * @since 1.0
 * @author DestKoder
 */
public final class Utils {

    @Contract(value = "null -> fail; !null -> param1", pure = true)
    public static <T> @NotNull T nonNull(@Nullable T t){
        if(t == null) throw new NullPointerException();
        return t;
    }

    /**
     * Convert given objects array to list of objects;
     * @param values - list of objects
     * @return list of given objects;
     * @param <T> type of objects;
     */
    @SafeVarargs
    @Contract(pure = true)
    @Deprecated
    public static  <T> @NotNull List<T> newList(T... values){
        return list(values);
    }

    /**
     * Convert given objects array to list of objects;
     * @param values - list of objects
     * @return list of given objects;
     * @param <T> type of objects;
     */
    @SafeVarargs
    public static <T> @NotNull List<T> list(T... values){
        return Arrays.asList(values);
    }

    public static <T, V> @NotNull List<T> list(@NotNull Collection<V> values, Function<V, T> function){
        List<T> result = new ArrayList<>();
        values.forEach(v -> result.add(function.apply(v)));
        return result;
    }

    /**
     * Check one of values pass a condition
     * @param condition condition
     * @param values values to check
     * @return true if one of values pass a condition or else in other cases
     * @param <T> type of value
     */
    @SafeVarargs
    public static <T> boolean checkOneOf(Predicate<T> condition, T... values){
        for(T t : values){
            if(condition.test(t)) return true;
        }
        return false;
    }


    /**
     * Transforms string array to a single string
     * @param args - array of strings
     * @return resulting string
     */
    @NotNull
    public static String argsToString(String @NotNull [] args){
        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    /**
     * Call consumer if result of given {@link Predicate} equals true
     * @param t object which will be checked
     * @param c consumer which will be called
     * @param p checking condition
     * @param <T> type of checking object
     */
    public static <T> void executeIf(@NotNull T t, @NotNull Consumer<T> c, @NotNull Predicate<T> p){
        if(p.test(t)){
            c.accept(t);
        }
    }


    /**
     * Transforms string array to a single string with start position
     * @param args - array of strings
     * @param startPos - number of the element from which need start
     * @return resulting string
     */
    public static @NotNull String argsToString(String @NotNull [] args, int startPos){
        StringBuilder sb = new StringBuilder();

        for(int i =startPos ;i < args.length; i ++){
            sb.append(args[i]);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    public static @NotNull String argsToString(String @NotNull [] args, String separator) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if(i != args.length-1) sb.append(separator);
        }

        return sb.toString().trim();
    }

    public static @NotNull String argsToString(String @NotNull [] args, int startPos, String separator) {
        StringBuilder sb = new StringBuilder();

        for(int i =startPos ;i < args.length; i ++){
            sb.append(args[i]);
            if(i != args.length-1) sb.append(separator);
        }

        return sb.toString().trim();
    }

    /**
     * Transforms {@link Collection} of {@link String} to a single string
     * @param collection - collection of strings
     * @return resulting string
     */
    @NotNull
    public static String collectionToString(@NotNull Collection<String> collection){
        StringBuilder sb = new StringBuilder();

        for (String arg : collection) {
            sb.append(arg);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    /**
     * Transforms {@link Collection} of {@link String} to a single string with specified separator
     * @param collection - collection of strings
     * @param separator - separator
     * @return resulting string
     */
    @NotNull
    public static String collectionToString(@NotNull Collection<String> collection, String separator){
        StringBuilder sb = new StringBuilder();

        for (String arg : collection) {
            sb.append(arg);
            sb.append(separator);
        }

        String s = sb.toString().trim();

        return s.substring(0, s.length()-separator.length()).trim();
    }

    /**
     * Transforms {@link List} of {@link String} to a single string with start position
     * @param collection - collection of strings
     * @param startPos - number of the element from which need start
     * @return resulting string
     */
    public static @NotNull String collectionToString(@NotNull List<String> collection, int startPos){
        return collectionToString(collection, startPos, " ");
    }

    /**
     * Transforms {@link List} of {@link String} to a single string with specified separator
     * @param collection - collection of strings
     * @param separator - separator
     * @return resulting string
     */
    public static @NotNull String collectionToString(@NotNull List<String> collection, String separator) {
        return collectionToString(collection, 0, separator);
    }
    /**
     * Transforms {@link List} of {@link String} to a single string with specified separator and start position
     * @param collection - collection of strings
     * @param separator - separator
     * @param startPos - number of element from which we need to start
     * @return resulting string
     */
    public static @NotNull String collectionToString(@NotNull List<String> collection, int startPos, String separator) {
        StringBuilder sb = new StringBuilder();

        for(int i =startPos; i < collection.size(); i ++){
            sb.append(collection.get(i));
            if(i != collection.size()-1) sb.append(separator);
        }

        return sb.toString().trim();
    }


//    public static void send(@NotNull CommandSender sender, @NotNull String message){
//        Arrays.stream(message.split(Library.msgSeparator)).forEach(sender::sendMessage);
//    }


}
