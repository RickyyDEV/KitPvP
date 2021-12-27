package ridev.com.br;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ridev.com.br.api.arena.ArenaStarter;
import ridev.com.br.api.bau.BoxStarter;
import ridev.com.br.api.cargos.GroupStarter;
import ridev.com.br.api.combat.CombatLogRunnable;
import ridev.com.br.api.key.ApiKEY;
import ridev.com.br.api.kit.KitLoader;
import ridev.com.br.api.leader.LeaderSchema;
import ridev.com.br.api.lobby.LobbyStarter;
import ridev.com.br.api.updater.UpdaterAPI;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpStarter;
import ridev.com.br.api.world.WorldStarter;
import ridev.com.br.comandos.Commands;
import ridev.com.br.eventos.Eventos;
import ridev.com.br.language.Language;
import ridev.com.br.sql.BackendLibrary;
import ridev.com.br.sql.BackendType;
import ridev.com.br.utils.apis.Metrics;
import ridev.com.br.utils.files.Files;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.ModuleLogger;
import ridev.com.br.utils.scoreboard.ScoreboardManager;
import ridev.com.br.utils.tab.TabListRunnable;
import ridev.com.br.utils.text.FancyText;

import java.util.logging.Level;

@Getter
public class Main extends JavaPlugin {

    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP");

    public Main() {
        instance = this;
    }

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private static SQLConnector sqlConnector;

    public void onLoad() {
        Files.criarArquivos();
        Language.of(this).init();
        ApiKEY.isLicencied();
        UpdaterAPI.isUpdated();
        instance = this;
        sqlConnector = BackendType.of().createSqlConnector();
        super.onLoad();
    }

    public void onEnable() {

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            LOGGER.log(Level.SEVERE, "O Plugin HolographicDisplays Não está habilitado!");
            LOGGER.log(Level.SEVERE, "O RiKitPvP apenas funciona com ele! Saindo...");
            this.setEnabled(false);
            return;
        }

        setupEnable();

        LOGGER.log(Level.INFO, "\n" +
                "  _____  _ _  ___ _   _____        _____  \n" +
                " |  __ \\(_) |/ (_) | |  __ \\      |  __ \\ \n" +
                " | |__) |_| ' / _| |_| |__) |_   _| |__) |\n" +
                " |  _  /| |  < | | __|  ___/\\ \\ / /  ___/ \n" +
                " | | \\ \\| | . \\| | |_| |     \\ V /| |     \n" +
                " |_|  \\_\\_|_|\\_\\_|\\__|_|      \\_/ |_|     \n" +
                "                                          \n" +
                "                                          \n" +
                "              PLUGIN HABILITADO COM SUCESSO!              ");

        super.onEnable();
    }

    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            User user = UserManager.map.get(p.getName());
            if (user != null) {
                BackendLibrary.insert(user);
            }
            p.kickPlayer(FancyText.colored("&fO servidor está &c&lREINICIANDO/DESLIGANDO&f! \n\n &e&lEntre novamente em alguns segundos"));
        }
        LOGGER.log(Level.INFO, "\n" +
                "  _____  _ _  ___ _   _____        _____  \n" +
                " |  __ \\(_) |/ (_) | |  __ \\      |  __ \\ \n" +
                " | |__) |_| ' / _| |_| |__) |_   _| |__) |\n" +
                " |  _  /| |  < | | __|  ___/\\ \\ / /  ___/ \n" +
                " | | \\ \\| | . \\| | |_| |     \\ V /| |     \n" +
                " |_|  \\_\\_|_|\\_\\_|\\__|_|      \\_/ |_|     \n" +
                "                                          \n" +
                "                                          \n" +
                "              PLUGIN DESABILITADO COM SUCESSO!              ");
        super.onDisable();
    }

    public FileConfiguration getConfig() {
        return Files.getConfig();
    }

    public static FileConfiguration getLang() {
        return Files.getLang();
    }

    public static FileConfiguration getLoc() {
        return Files.getLoc();
    }

    public static FileConfiguration getRole() {
        return Files.getRole();
    }

    public static FileConfiguration getWorlds() {
        return Files.getWorlds();
    }

    private void setupEnable() {
        new WorldStarter();
        new KitLoader(getInstance());
        new ArenaStarter(getInstance());
        InventoryManager.enable(getInstance());
        new UserManager(getInstance());
        new GroupStarter(getInstance());
        new CacheSystem().setupCache();
        new LeaderSchema(getInstance());
        new BoxStarter(getInstance());
        new WarpStarter(getInstance());
        new LobbyStarter(getInstance());
        Commands.setupCommands();
        new Eventos(getInstance());
        new Metrics(getInstance(), 13275);
        new ScoreboardManager(getInstance());
        new TabListRunnable(getInstance());
        new CombatLogRunnable(getInstance());
    }

    public static SQLConnector getSqlConnector() {
        return sqlConnector;
    }
}

