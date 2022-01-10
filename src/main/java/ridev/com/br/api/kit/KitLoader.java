package ridev.com.br.api.kit;

import org.bukkit.Bukkit;
import ridev.com.br.Main;
import ridev.com.br.utils.other.ClassGetter;
import ridev.com.br.utils.other.ModuleLogger;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class KitLoader {

    private final Main plugin;
    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP Kits");

    public KitLoader(Main plugin) {
        this.plugin = plugin;
        loadKits();
    }

    public void loadKits() {


        int i = 0;
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
        KitLibrary.kits = organize(KitLibrary.getKits());
        LOGGER.log(Level.INFO, "Kits carregados Com sucesso!");
    }


    public List<Kit> organize(List<Kit> list) {
        return list.stream().sorted(Comparator.comparing(Kit::rarity)).collect(Collectors.toList());
    }

//    public String getKitPermission(String kitname) {
//        for (String s : KitLanguage.get(KitLanguage::kitsPermissions).getKeys(false)) {
//            if (s.equalsIgnoreCase(kitname)) return;
//        }
//    }
}
