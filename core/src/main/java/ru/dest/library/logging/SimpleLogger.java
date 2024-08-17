package ru.dest.library.logging;

import java.util.Arrays;
import java.util.logging.Logger;

public class SimpleLogger implements ILogger{

    private Logger logger;

    public SimpleLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String... msg) {
        Arrays.stream(msg).forEach(s -> logger.info(String.format("\u001B[32m%s\u001B[0m", s)));
    }

    @Override
    public void warning(String... warnings) {
        Arrays.stream(warnings).forEach(s -> logger.warning(String.format("\u001B[33m%s\u001B[0m", s)));
    }

    @Override
    public void error(Exception... errors) {
        Arrays.stream(errors).forEach(error -> {
//            logger.warning("\u001B[31m Eror occupied: " + error.getMessage() + "\u001B[0m");
            error.printStackTrace();
        });
    }
}
