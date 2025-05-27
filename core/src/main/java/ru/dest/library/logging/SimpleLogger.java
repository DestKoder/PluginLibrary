package ru.dest.library.logging;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class SimpleLogger implements ILogger{


    private final String subName;

    public SimpleLogger(@NotNull Logger logger) {
        this.info = logger::info;
        this.warning = logger::warning;
        this.error = logger::warning;
        subName = "";
    }

    public SimpleLogger(@NotNull Logger logger, String plugin){
        this.info = logger::info;
        this.warning = logger::warning;
        this.error = logger::warning;
        this.subName = "["+GREEN+plugin+RESET+"]";
    }

    private final Consumer<String> info, error, warning;

    public SimpleLogger(Consumer<String> info, Consumer<String> error, Consumer<String> warning){
        this.info = info;
        this.error = error;
        this.warning = warning;
        this.subName = "";
    }

    public SimpleLogger(Consumer<String> info, Consumer<String> error, Consumer<String> warning, String plugin){
        this.info = info;
        this.error = error;
        this.warning = warning;
        this.subName = "["+GREEN+plugin+RESET+"]";
    }

    @Override
    public void info(String... msg) {
        Arrays.stream(msg).forEach(s -> info.accept(subName+ CYAN + s + RESET));
    }

    @Override
    public void warning(String... warnings) {
        Arrays.stream(warnings).forEach(s -> warning.accept(subName+YELLOW+ s + RESET));
    }

    public void error(String... errors){
        Arrays.stream(errors).forEach(s -> error.accept(subName+RED + s + RESET));
    }

    @Override
    public void error(Exception... errors) {
        Arrays.stream(errors).forEach(error -> {
            error.printStackTrace();
        });
    }
}
