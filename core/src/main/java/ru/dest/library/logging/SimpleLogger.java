package ru.dest.library.logging;

import java.util.Arrays;
import java.util.logging.Logger;

public class SimpleLogger implements ILogger{

    private final Logger logger;
    private final String subName;

    public SimpleLogger(Logger logger) {
        this.logger = logger;
        subName = "";
    }

    public SimpleLogger(Logger logger, String plugin){
        this.logger = logger;
        this.subName = "["+GREEN+plugin+RESET+"]";
    }

    @Override
    public void info(String... msg) {
        Arrays.stream(msg).forEach(s -> logger.info(subName+ CYAN + s + RESET));
    }

    @Override
    public void warning(String... warnings) {
        Arrays.stream(warnings).forEach(s -> logger.warning(subName+YELLOW+ s + RESET));
    }

    public void error(String... errors){
        Arrays.stream(errors).forEach(s -> logger.warning(subName+RED + s + RESET));
    }

    @Override
    public void error(Exception... errors) {
        Arrays.stream(errors).forEach(error -> {
            error.printStackTrace();
        });
    }
}
