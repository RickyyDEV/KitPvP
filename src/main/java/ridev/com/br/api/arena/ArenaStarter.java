package ridev.com.br.api.arena;

import org.bukkit.Bukkit;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.Main;

public class ArenaStarter {


    public ArenaStarter(Main plugin) {
        Bukkit.getScheduler().runTask(Main.getInstance(), this::loadArenas);
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
