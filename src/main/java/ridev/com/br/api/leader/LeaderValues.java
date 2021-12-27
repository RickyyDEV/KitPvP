package ridev.com.br.api.leader;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import ridev.com.br.api.user.User;
import ridev.com.br.Main;

import java.util.Set;

public class LeaderValues {

    public static Set<User> getKillsLeader() {
        return executor().resultManyQuery(
                "SELECT * FROM jogadores ORDER BY kills DESC LIMIT 10;",
                statement -> {
                },
                LeaderBackend.class);
    }

    public static Set<User> getXpLeader() {
        return executor().resultManyQuery(
                "SELECT * FROM jogadores ORDER BY xp DESC LIMIT 10;",
                statement -> {
                },
                LeaderBackend.class);
    }

    private static SQLExecutor executor() {
        return new SQLExecutor(Main.getSqlConnector());
    }
}
