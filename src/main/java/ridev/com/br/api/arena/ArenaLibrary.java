package ridev.com.br.api.arena;

import lombok.Getter;
import ridev.com.br.utils.apis.Mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaLibrary {


    @Getter
    static HashMap<ArenaType, Arena> arenas = new HashMap<>();


    public static Arena getArena(ArenaType type) {
        return arenas.get(type);
    }


    public static Arena getRandomArena() {
        List<Arena> list = new ArrayList<>();
        for (Map.Entry<ArenaType, Arena> arenass : arenas.entrySet()) {
            list.add(arenass.getValue());
        }
        return Mine.getRandom(list);
    }

    public static boolean arenaExists(ArenaType type) {
        return arenas.get(type) != null;
    }

    public static List<ArenaType> arenasNotSetted() {
        List<ArenaType> lista = new ArrayList<>();
        if (arenas.get(ArenaType.A1) == null) lista.add(ArenaType.A1);
        if (arenas.get(ArenaType.A2) == null) lista.add(ArenaType.A2);
        if (arenas.get(ArenaType.A3) == null) lista.add(ArenaType.A3);
        if (arenas.get(ArenaType.A4) == null) lista.add(ArenaType.A4);
        if (arenas.get(ArenaType.A5) == null) lista.add(ArenaType.A5);
        if (arenas.get(ArenaType.A6) == null) lista.add(ArenaType.A6);
        if (arenas.get(ArenaType.A7) == null) lista.add(ArenaType.A7);
        if (arenas.get(ArenaType.A8) == null) lista.add(ArenaType.A8);
        if (arenas.get(ArenaType.A9) == null) lista.add(ArenaType.A9);
        if (arenas.get(ArenaType.A10) == null) lista.add(ArenaType.A10);
        return lista;
    }

    public static List<ArenaType> arenasIsSetted() {
        List<ArenaType> lista = new ArrayList<>();
        if (arenas.get(ArenaType.A1) != null) lista.add(ArenaType.A1);
        if (arenas.get(ArenaType.A2) != null) lista.add(ArenaType.A2);
        if (arenas.get(ArenaType.A3) != null) lista.add(ArenaType.A3);
        if (arenas.get(ArenaType.A4) != null) lista.add(ArenaType.A4);
        if (arenas.get(ArenaType.A5) != null) lista.add(ArenaType.A5);
        if (arenas.get(ArenaType.A6) != null) lista.add(ArenaType.A6);
        if (arenas.get(ArenaType.A7) != null) lista.add(ArenaType.A7);
        if (arenas.get(ArenaType.A8) != null) lista.add(ArenaType.A8);
        if (arenas.get(ArenaType.A9) != null) lista.add(ArenaType.A9);
        if (arenas.get(ArenaType.A10) != null) lista.add(ArenaType.A10);
        return lista;
    }

}
