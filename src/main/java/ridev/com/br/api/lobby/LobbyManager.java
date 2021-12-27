package ridev.com.br.api.lobby;


import lombok.Getter;
import ridev.com.br.api.warps.WarpType;

import java.util.HashMap;

public class LobbyManager {

    @Getter
    static HashMap<WarpType, Lobby> lobby = new HashMap<>();


    public static Lobby lobby() {
        return lobby.get(WarpType.LOBBY);
    }

    public static boolean lobbyIsSet() {
        return lobby.get(WarpType.LOBBY) != null;
    }

    public static void removeLobby() {
        lobby().remove();
        lobby().removeInConfig();
    }
}
