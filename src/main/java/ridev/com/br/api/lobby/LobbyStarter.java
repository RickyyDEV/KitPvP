package ridev.com.br.api.lobby;

import ridev.com.br.Main;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.api.world.WorldAPI;

public class LobbyStarter {

    public LobbyStarter(Main plugin) {
        loadLobbys();
    }

    public void loadLobbys() {
        if (Main.getLoc().get("lobby.spawn") != null) {
            Lobby lb = new Lobby(LocationAPI.deserialize(Main.getLoc().getString("lobby.spawn")));
            WorldAPI.loadChunk(LocationAPI.deserialize(Main.getLoc().getString("lobby.spawn")));
            lb.setItens(LobbyItens.getItens());
            lb.save();
        }
    }

}
