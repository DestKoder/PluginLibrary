package ru.dest.library.utils;

public class Parser {

    public static int parseInt(String s){
        try {
            return Integer.parseInt(s);
        }catch (NumberFormatException e){
            return 0;
        }
    }
}
