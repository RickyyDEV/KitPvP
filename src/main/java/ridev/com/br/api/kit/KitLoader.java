package ridev.com.br.api.kit;

import org.bukkit.Bukkit;
import ridev.com.br.Main;
import ridev.com.br.utils.other.ClassGetter;
import ridev.com.br.utils.other.ModuleLogger;

import java.util.logging.Level;

public class KitLoader {

    private final Main plugin;
    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP Kits");

    public KitLoader(Main plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTask(plugin, this::loadKits);
    }

    public void loadKits() {

        for (Class<?> classes : ClassGetter.getClassesForPackage(this.plugin, "ridev.com.br.api.kit.kits")) {
            try {
                if (Kit.class.isAssignableFrom(classes)) {
                    Kit kit = (Kit) classes.newInstance();
                    KitLibrary.getKits().add(kit);
                    Bukkit.getPluginManager().registerEvents(kit.event(), this.plugin);
                }
            } catch (Exception excpetion) {
                LOGGER.log(Level.SEVERE, "Não foi possivel carregar os kits!", excpetion);

            }
        }
        LOGGER.log(Level.INFO, "Kits carregados Com sucesso!");
    }
}
