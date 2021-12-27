package ridev.com.br.utils.files;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ridev.com.br.Main;
import ridev.com.br.utils.other.ModuleLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class Files {
    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP File Schema");
    private static FileConfiguration config;
    private static FileConfiguration language;
    private static FileConfiguration location;
    private static FileConfiguration roles;
    private static FileConfiguration worlds;

    public static FileConfiguration getConfig() {
        return config;
    }

    public static FileConfiguration getLang() {
        return language;
    }

    public static FileConfiguration getLoc() {
        return location;
    }

    public static FileConfiguration getRole() {
        return roles;
    }

    public static FileConfiguration getWorlds() {
        return worlds;
    }

    public static void createConfig() {
        File configF = new File(Main.getInstance().getDataFolder() + File.separator, "config.yml");
        if (!configF.exists()) {
            configF.getParentFile().mkdirs();
            FileUtils.copyFile(Main.getInstance().getResource("config.yml"), configF);
        }
        try {
            config = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(configF), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Não Foi Possivel Ler O Arquivo config.yml ", e);
            System.exit(0);
        }
    }

    public static void createWorld() {
        File worldF = new File(Main.getInstance().getDataFolder() + File.separator, "worlds.yml");
        if (!worldF.exists()) {
            worldF.getParentFile().mkdirs();
            FileUtils.copyFile(Main.getInstance().getResource("worlds.yml"), worldF);
        }
        try {
            worlds = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(worldF), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Não Foi Possivel Ler O Arquivo worlds.yml ", e);
            System.exit(0);
        }
    }


    public static void createLang() {
        File lang = new File(Main.getInstance().getDataFolder() + File.separator, "language.yml");
        if (!lang.exists()) {
            lang.getParentFile().mkdirs();
            FileUtils.copyFile(Main.getInstance().getResource("language.yml"), lang);
        }
        try {
            language = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(lang), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Não Foi Possivel Ler O Arquivo language.yml ", e);
            System.exit(0);
        }
    }

    public static void createLoc() {
        File loc = new File(Main.getInstance().getDataFolder() + File.separator, "location.yml");
        if (!loc.exists()) {
            loc.getParentFile().mkdirs();
            FileUtils.copyFile(Main.getInstance().getResource("location.yml"), loc);
        }
        try {
            location = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(loc), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Não Foi Possivel Ler O Arquivo location.yml ", e);
            System.exit(0);
        }
    }

    public static void createRole() {
        File role = new File(Main.getInstance().getDataFolder() + File.separator, "cargos.yml");
        if (!role.exists()) {
            role.getParentFile().mkdirs();
            FileUtils.copyFile(Main.getInstance().getResource("cargos.yml"), role);
        }
        try {
            roles = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(role), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Não Foi Possivel Ler O Arquivo cargos.yml ", e);
            System.exit(0);
        }
    }


    public static void saveLangConfig() throws IOException {
        Main.getLang().save(new File(Main.getInstance().getDataFolder() + File.separator, "language.yml"));
    }

    public static void saveLocConfig() throws IOException {
        Main.getLoc().save(new File(Main.getInstance().getDataFolder() + File.separator, "location.yml"));
    }

    public static void saveRoleConfig() throws IOException {
        Main.getRole().save(new File(Main.getInstance().getDataFolder() + File.separator, "language.yml"));
    }

    public static void saveConfigConfig() throws IOException {
        Main.getInstance().getConfig().save(new File(Main.getInstance().getDataFolder() + File.separator, "config.yml"));
    }

    public static void saveWorldConfig() throws IOException {
        Main.getWorlds().save(new File(Main.getInstance().getDataFolder() + File.separator, "worlds.yml"));
    }


    public static void criarArquivos() {
        try {
            createConfig();
            createLang();
            createLoc();
            createRole();
            createWorld();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, ChatColor.DARK_RED + "Ocorreu Um Erro ao tentar Criar os Arquivos De Configuração!", e);
        }
    }


}
