package ridev.com.br.sql;


import com.henryfabio.sqlprovider.executor.SQLExecutor;
import ridev.com.br.Main;

public class Backend {


    public static SQLExecutor executor() {
        return new SQLExecutor(Main.getSqlConnector());
    }

}