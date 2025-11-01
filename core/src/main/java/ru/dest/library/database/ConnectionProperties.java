package ru.dest.library.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.database.provider.ConnectionProvider;
import ru.dest.library.exception.UnknownDatabaseDriver;

import java.io.File;

@AllArgsConstructor
public class ConnectionProperties {

    @NotNull
    @Getter
    private final String driver;
    @Getter
    private final File rootDir;

    @Nullable
    @Getter
    private final String host, user, password, base, params;
    @Getter
    private final int port;
    @Getter
    private final int poolSize;

    public ConnectionProvider create() throws Exception {
        DatabaseDriver d = DatabaseDriver.byName(driver);
        if(d == null) throw new UnknownDatabaseDriver(driver);

        ConnectionProvider provider = d.createProvider(this);

        return provider;
    }

}
