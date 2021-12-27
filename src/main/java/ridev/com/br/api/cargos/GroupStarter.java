package ridev.com.br.api.cargos;

import org.bukkit.Bukkit;
import ridev.com.br.Main;
import ridev.com.br.utils.other.ModuleLogger;

import java.util.logging.Level;

public class GroupStarter {
    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP Roles");
    Main plugin;

    public GroupStarter(Main plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTask(plugin, this::loadAllGroups);
    }

    public void loadAllGroups() {
        if (Main.getRole().get("cargos") != null) {
            int i = 0;
            for (String s : Main.getRole().getConfigurationSection("cargos").getKeys(false)) {
                boolean bd = Main.getRole().getBoolean("cargos." + s + ".broadcast_lobby");
                if (Main.getRole().get("cargos." + s + ".broadcast_lobby") == null) {
                    bd = false;
                }
                String perm = Main.getRole().getString("cargos." + s + ".Permissao");
                if (Main.getRole().getString("cargos." + s + ".Permissao").equalsIgnoreCase("empty") || Main.getRole().getString("cargos." + s + ".Permissao").isEmpty())
                    perm = "empty";
                Group gp = new Group(Main.getRole().getString("cargos." + s + ".Nome"), perm, Main.getRole().getString("cargos." + s + ".Prefix"), bd, i);
                gp.createGroup();
                i++;
            }
            LOGGER.log(Level.INFO, i + " Cargos carregados!");
        }
    }
}
