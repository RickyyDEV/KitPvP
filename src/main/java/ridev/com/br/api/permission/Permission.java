package ridev.com.br.api.permission;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.Main;
import ridev.com.br.language.KitLanguage;

public abstract class Permission {

    @Getter
    private static Permission instance;


    public abstract boolean hasPermission(Player player, String permission);

    public abstract void addPermission(Player player, String permission);

    public abstract void removePermission(Player player, String permission);


    public static void loadPermissions() {
        if (KitLanguage.get(KitLanguage::saveType).equalsIgnoreCase("permission")) {

            if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
                instance = new LuckPerms();
            } else {
                Main.LOGGER.severe("Luck perms não foi encontrado no servidor! Desligando...");
                Bukkit.getPluginManager().disablePlugin(Main.getInstance());
            }
        }
    }

}
