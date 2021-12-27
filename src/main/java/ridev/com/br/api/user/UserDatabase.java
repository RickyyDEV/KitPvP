package ridev.com.br.api.user;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.cargos.GroupManager;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitLibrary;
import ridev.com.br.api.rank.RankType;
import ridev.com.br.api.warps.WarpType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDatabase implements SQLResultAdapter<User> {
    @Override
    public User adaptResult(SimpleResultSet simpleResultSet) {
        Map<BoxType, Integer> box = BoxType.toHash(simpleResultSet.get("caixas"));
        List<Kit> kitlist = new ArrayList<>();
        String kits = simpleResultSet.get("kits");
        for (String s : kits.toLowerCase().split(";")) {
            kitlist.add(KitLibrary.getKit(s.toLowerCase()));
        }
        return User.builder()
                .username(simpleResultSet.get("nome"))
                .rank(RankType.transform(simpleResultSet.get("rank")))
                .coins(simpleResultSet.get("coins"))
                .xp(simpleResultSet.get("xp"))
                .kills(simpleResultSet.get("kills"))
                .caixas(simpleResultSet.get("caixas"))
                .boxes(box)
                .kit(null)
                .kits(kitlist)
                .role(GroupManager.getGroup(simpleResultSet.get("role")))
                .mortes(simpleResultSet.get("mortes"))
                .warp(WarpType.LOBBY)
                .build();
    }
}
