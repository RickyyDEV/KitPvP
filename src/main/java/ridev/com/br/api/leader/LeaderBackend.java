package ridev.com.br.api.leader;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import ridev.com.br.api.user.User;
import ridev.com.br.sql.BackendLibrary;

public class LeaderBackend implements SQLResultAdapter<User> {
    @Override
    public User adaptResult(SimpleResultSet resultSet) {
        return BackendLibrary.select(resultSet.get("nome"));
    }
}
