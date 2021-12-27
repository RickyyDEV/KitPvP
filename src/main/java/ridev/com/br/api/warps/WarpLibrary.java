package ridev.com.br.api.warps;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import ridev.com.br.api.lobby.LobbyManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class WarpLibrary {

    @Getter
    private static HashMap<WarpType, Warp> warps = Maps.newHashMap();


    public static Warp getWarp(WarpType type) {
        return warps.get(type);
    }


    public static List<WarpType> getWarpNotSet() {
        List<WarpType> lista = new ArrayList<>();
        if (warps.get(WarpType.FPS) == null) lista.add(WarpType.FPS);
        if (warps.get(WarpType.LAVA) == null) lista.add(WarpType.LAVA);
        if (warps.get(WarpType.ONEVSONE) == null || warps.get(WarpType.ONEVSONE).getSpawn() == null || warps.get(WarpType.ONEVSONE).getFirstSpawn() == null || warps.get(WarpType.ONEVSONE).getSecondSpawn() == null)
            lista.add(WarpType.ONEVSONE);
        if (warps.get(WarpType.SUMO) == null || warps.get(WarpType.SUMO).getSpawn() == null || warps.get(WarpType.SUMO).getFirstSpawn() == null || warps.get(WarpType.SUMO).getSecondSpawn() == null)
            lista.add(WarpType.SUMO);
        if (!LobbyManager.lobbyIsSet()) lista.add(WarpType.LOBBY);
        return lista;
    }

    public static List<WarpType> getWarpSetted() {
        List<WarpType> lista = new ArrayList<>();
        if (warps.get(WarpType.FPS) != null) lista.add(WarpType.FPS);
        if (warps.get(WarpType.LAVA) != null) lista.add(WarpType.LAVA);
        if (warps.get(WarpType.ONEVSONE) != null) lista.add(WarpType.ONEVSONE);
        if (warps.get(WarpType.SUMO) != null) lista.add(WarpType.SUMO);
        if (LobbyManager.lobbyIsSet()) lista.add(WarpType.LOBBY);
        return lista;
    }
}
