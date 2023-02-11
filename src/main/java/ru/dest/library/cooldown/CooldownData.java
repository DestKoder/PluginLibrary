package ru.dest.library.cooldown;

/**
 * This class represents a information of cooldown
 *
 * @since 1.0
 * @author DestKoder
 */
public class CooldownData {

    private final String action;
    private long expires;

    public String getAction() {
        return action;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public CooldownData(String action, long expires) {
        this.action = action;
        this.expires = expires;
    }
}
