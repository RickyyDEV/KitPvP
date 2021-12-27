package ridev.com.br.api.world;

import lombok.SneakyThrows;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.utils.files.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;

public class WorldAPI {
    public static void setDifficulty(final World mundo, final Difficulty dificuldade) {
        mundo.setDifficulty(dificuldade);
    }

    public static Difficulty getDifficulty(final World mundo) {
        return mundo.getDifficulty();
    }

    public static boolean getAutoSave(final World mundo) {
        return mundo.isAutoSave();
    }

    public static void setAutoSave(final World mundo, final boolean bool) {
        mundo.setAutoSave(bool);
    }

    public static Location getSpawnLocation(final World mundo) {
        return mundo.getSpawnLocation();
    }

    public static void setSpawnLocation(final World mundo, final int x, final int y, final int z) {
        mundo.setSpawnLocation(x, y, z);
    }

    public static boolean canGenerateStructures(final World mundo) {
        return mundo.canGenerateStructures();
    }

    public static boolean getPvP(final World mundo) {
        return mundo.getPVP();
    }

    public static int getSeaLevel(final World mundo) {
        return mundo.getSeaLevel();
    }

    public static List<Entity> getEntity(final World mundo) {
        return mundo.getEntities();
    }

    public static void saveWorld(final World mundo) {
        mundo.save();
    }

    public static void createWorld(final String nome) {
        Bukkit.createWorld(WorldCreator.name(nome));
    }

    public static void deleteWorld(final String nome) {
        Bukkit.unloadWorld(nome, true);
    }

    @SneakyThrows
    public static void unloadWorld(final String nome) {
        Bukkit.unloadWorld(nome, true);
        Main.getWorlds().set("worlds." + nome, null);
        Files.saveWorldConfig();
    }

    public static void cancelUnloadWorld(final String nome) {
        Bukkit.unloadWorld(nome, false);
    }


    public static void loadChunk(Location loc) {
        new BukkitRunnable() {
            @Override
            public void run() {
                loc.getChunk().load();
            }
        }.runTask(Main.getInstance());

    }

    public static void loadWorld(final String nome) {
        try {
            Bukkit.getServer().createWorld(new WorldCreator(nome));
            Bukkit.getWorld(nome).setAutoSave(true);
            Bukkit.getWorld(nome).setDifficulty(Difficulty.PEACEFUL);
            Main.getWorlds().set("worlds." + nome + ".WorldName", Bukkit.getWorld(nome).getName());
            Files.saveWorldConfig();
        } catch (Exception e) {
            Main.LOGGER.log(Level.SEVERE, "erro ao criar mundo", e);
        }
    }

    public static void createExplosion(final Player jogador, final int tamanho) {
        jogador.getWorld().createExplosion(jogador.getLocation(), (float) tamanho, true);
    }

    public static void reloadMap(final String nome) {
        unloadWorld(nome);
        loadWorld(nome);
    }

    public static List<World> getLoadedWorlds() {
        return Bukkit.getWorlds();
    }

    public static ArrayList<String> getUnloadedWorlds() {

        ArrayList<String> results = new ArrayList<>();
        File[] files = Bukkit.getServer().getWorldContainer().listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    for (File f : Objects.requireNonNull(file.listFiles())) {
                        if (f.getName().equalsIgnoreCase("level.dat")) {
                            if (Bukkit.getWorld(file.getName()) == null) {
                                results.add(file.getName());
                            }
                            break;
                        }
                    }
                }
            }
        }
        results.remove("plugins");
        results.remove("logs");
        return results;
    }

    @SneakyThrows
    public static String getLevelName() {
        BufferedReader is = new BufferedReader(new FileReader("server.properties"));
        Properties props = new Properties();
        props.load(is);
        is.close();
        return String.valueOf(props.getProperty("level-name"));
    }

    public static void loadAllMaps() {
        if (Main.getWorlds().get("worlds") != null) {
            for (String s : Main.getWorlds().getConfigurationSection("worlds").getKeys(false)) {
                if (s != null) {
                    Main.LOGGER.log(Level.INFO, "Carregando o mundo " + Main.getWorlds().getString("worlds." + s + ".WorldName"));
                    WorldAPI.loadWorld(Main.getWorlds().getString("worlds." + s + ".WorldName"));
                    Bukkit.getWorld(s).setDifficulty(Difficulty.HARD);
                    for (Entity en : Bukkit.getWorld(Main.getWorlds().getString("worlds." + s + ".WorldName")).getEntities()) {
                        en.remove();
                    }
                } else {
                    break;
                }
            }
        }
        for (final World w : Bukkit.getServer().getWorlds()) {
            w.setTime(8000L);
            w.setStorm(false);
            w.setThundering(false);
        }
    }

}
