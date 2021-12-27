package ridev.com.br.language;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;


@Data(staticConstructor = "of")
public class Language {

    private final JavaPlugin plugin;

    public void init() {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);

        configurationInjector.saveDefaultConfiguration(
                plugin,
                "config.yml",
                "language.yml"
        );

        configurationInjector.injectConfiguration(
                ConfigValue.instance(),
                LangValue.instance()
        );

    }

}
