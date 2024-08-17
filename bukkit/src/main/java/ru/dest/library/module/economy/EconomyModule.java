package ru.dest.library.module.economy;

public interface EconomyModule {

    enum Result {
        SUCCESS, ERROR_INTERNAL, ERROR_BALANCE;

        boolean isSuccess(){ return this == SUCCESS; }
    }


}
