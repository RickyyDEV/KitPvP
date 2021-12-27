package ridev.com.br.api.bau;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.Main;

import java.util.logging.Level;

public class BoxStarter {
    Main plugin;

    public BoxStarter(Main plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTask(plugin, this::loadAllBoxes);
    }


    private void loadAllBoxes() {
        if (Main.getLoc().get("bau") != null) {
            for (String s : Main.getLoc().getConfigurationSection("bau").getKeys(false)) {
                String string = Main.getLoc().getString("bau." + s + ".spawn");
                Location loc = LocationAPI.deserialize(string);
                if (loc.getBlock() != null && loc.getBlock().getType().equals(Material.CHEST)) {
                    Box box = new Box(s, loc.getBlock());
                    box.createMysteryBox();
                } else {
                    Main.LOGGER.log(Level.INFO, "O baú " + s + " Não foi setado corretamente. Exclua-lo e crie-o novamente!");
                }
            }
        }
    }
}
