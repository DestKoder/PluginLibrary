package ru.dest.library.object;

/**
 * Describes any object that can be sent.
 *
 * @author DestKoder
 * @since 1.0
 */
public interface ISendAble {

    void send(Object receiver);
    void broadcast();
    void broadcast(String permission);

    String raw();
}
