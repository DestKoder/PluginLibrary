package ru.dest.library.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.config.DatabaseConfig;

import java.lang.reflect.InvocationTargetException;

public enum DBType {
    MYSQL("ru.dest.library.database.provider.MySQLProvider"),
    SQLITE("ru.dest.library.database.provider.SQLiteProvider"),
    H2_LOCAL("ru.dest.library.database.provider.H2LocalProvider"),
    H2_REMOTE("ru.dest.library.database.provider.H2RemoteProvider")
    ;

    private final String providerClass;

    DBType(String providerClass) {
        this.providerClass = providerClass;
    }

    @NotNull
    public DBProvider getProvider(DatabaseConfig config) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (DBProvider) Class.forName(providerClass).getDeclaredConstructor(DatabaseConfig.class).newInstance(config);
    }

    @Nullable
    public static DBType getByName(String name){
        for(DBType t : DBType.values()){
            if(t.name().equalsIgnoreCase(name))return t;
        }
        return null;
    }
}
