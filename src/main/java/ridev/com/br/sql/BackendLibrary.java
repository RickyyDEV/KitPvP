package ridev.com.br.sql;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import ridev.com.br.Main;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserDatabase;


public class BackendLibrary {

    private static final String TABLE = "jogadores";
    Main plugin;

    public BackendLibrary(Main plugin) {
        this.plugin = plugin;
        createTable();
    }

    public void createTable() {
        executor().updateQuery(
                "CREATE TABLE IF NOT EXISTS " + TABLE + " ("
                        + "nome VARCHAR(16) NOT NULL PRIMARY KEY,"
                        + "coins INTEGER NOT NULL,"
                        + "xp INTEGER NOT NULL,"
                        + "kills INTEGER NOT NULL,"
                        + "mortes INTEGER NOT NULL,"
                        + "rank VARCHAR(12) NOT NULL,"
                        + "caixas VARCHAR(255) NOT NULL,"
                        + "kits VARCHAR(255) NOT NULL,"
                        + "role VARCHAR(30) NOT NULL"
                        + ");"
        );


    }


    public static User select(String playerName) {
        return executor().resultOneQuery(
                "SELECT * FROM " + TABLE + " WHERE nome = ?",
                statement -> statement.set(1, playerName),
                UserDatabase.class);
    }

    public static void insert(User user) {
        executor().updateQuery(
                "REPLACE INTO " + TABLE + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                statement -> {
                    statement.set(1, user.getUsername());
                    statement.set(2, user.getCoins());
                    statement.set(3, user.getXp());
                    statement.set(4, user.getKills());
                    statement.set(5, user.getMortes());
                    statement.set(6, user.getRank().getName());
                    statement.set(7, user.getCaixas());
                    statement.set(8, user.getKitsString());
                    statement.set(9, user.getRole().getRoleName());
                }
        );
    }

    private static SQLExecutor executor() {
        return new SQLExecutor(Main.getSqlConnector());
    }

}
