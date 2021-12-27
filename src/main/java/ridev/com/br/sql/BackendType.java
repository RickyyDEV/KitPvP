package ridev.com.br.sql;

import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import lombok.Data;
import ridev.com.br.language.ConfigValue;

import java.io.File;

@Data(staticConstructor = "of")
public final class BackendType {


    public SQLConnector createSqlConnector() {
        String databaseType = ConfigValue.get(ConfigValue::databaseType);

        switch (databaseType.toLowerCase()) {
            case "sqlite":
                return buildSqliteDatabaseType().connect();
            case "mysql":
                return buildMysqlDatabaseType().connect();
            default:
                throw new UnsupportedOperationException("Tipo de database Não suportada!");
        }
    }

    private SQLiteDatabaseType buildSqliteDatabaseType() {
        return SQLiteDatabaseType.builder()
                .file(new File("plugins/RiKitPvP/sql/database.db"))
                .build();
    }

    private MySQLDatabaseType buildMysqlDatabaseType() {
        return MySQLDatabaseType.builder()
                .address(ConfigValue.get(ConfigValue::databaseAddress) + ":3306")
                .username(ConfigValue.get(ConfigValue::databaseUsername))
                .password(ConfigValue.get(ConfigValue::databasePassword))
                .database(ConfigValue.get(ConfigValue::databaseDatabase))
                .build();
    }

}
