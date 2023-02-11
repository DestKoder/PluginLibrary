package ru.dest.library.object;

/**
 * Simple class for stored key-value pairs
 * @param <T> Key class
 * @param <A> Value class
 */
public class Pair<T, A> {

    private T firstVal;
    private A secondVal;

    public Pair(T firstVal, A secondVal) {
        this.firstVal = firstVal;
        this.secondVal = secondVal;
    }

    public T getFirstVal() {
        return firstVal;
    }

    public A getSecondVal() {
        return secondVal;
    }

    public void setFirstVal(T firstVal) {
        this.firstVal = firstVal;
    }

    public void setSecondVal(A secondVal) {
        this.secondVal = secondVal;
    }
}
