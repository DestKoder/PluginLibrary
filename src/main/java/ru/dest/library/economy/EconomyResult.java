package ru.dest.library.economy;

public class EconomyResult {

    public enum ResultType {
        FAIL,SUCCESS, FAIL_NOMONEY
    }

    public final double amount;
    public final double balance;
    public final ResultType type;

    public EconomyResult(double amount, double balance, ResultType type) {
        this.amount = amount;
        this.balance = balance;
        this.type = type;
    }

    public boolean isSuccess(){
        return type == ResultType.SUCCESS;
    }
}
