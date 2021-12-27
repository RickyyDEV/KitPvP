package ridev.com.br.api.world;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import ridev.com.br.Main;

import java.util.logging.Level;

public class WorldStarter {


    public WorldStarter() {
        loadAllWorlds();
    }


    public void loadAllWorlds() {
        if (Main.getWorlds().get("worlds") != null) {
            for (String s : Main.getWorlds().getConfigurationSection("worlds").getKeys(false)) {
                if (s != null) {
                    Main.LOGGER.log(Level.INFO, "Carregando o mundo " + Main.getWorlds().getString("worlds." + s + ".WorldName"));
                    WorldAPI.loadWorld(Main.getWorlds().getString("worlds." + s + ".WorldName"));
                    World w = Bukkit.getWorld(s);

                    w.setDifficulty(Difficulty.HARD);
                    w.setTime(8000L);
                    w.setStorm(false);
                }
            }
            for (World ws : Bukkit.getWorlds()) {
                for (Entity e : ws.getEntities()) {
                    e.remove();
                }
            }
        }
    }
}
