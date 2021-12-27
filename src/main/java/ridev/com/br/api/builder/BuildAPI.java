package ridev.com.br.api.builder;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class BuildAPI {

    private static final HashMap<Player, Player> b = new HashMap<>();


    public static void addBuilder(Player p) {
        b.put(p, p);
    }

    public static boolean isBuilder(Player p) {
        return b.containsKey(p);
    }

    public static void removeBuilder(Player p) {
        b.remove(p);
    }

}
