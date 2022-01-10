package ridev.com.br.api.arena;

import ridev.com.br.Main;
import ridev.com.br.api.world.LocationAPI;

public class ArenaStarter {


    public ArenaStarter(Main plugin) {
        loadArenas();
    }


    public void loadArenas() {
        if (Main.getLoc().get("arenas") != null) {
            for (String s : Main.getLoc().getConfigurationSection("arenas").getKeys(false)) {
                Arena a = new Arena(ArenaType.transform(s));
                a.setLocation(LocationAPI.deserialize(Main.getLoc().getString("arenas." + s)));
                a.save();
            }
        }
    }

}
