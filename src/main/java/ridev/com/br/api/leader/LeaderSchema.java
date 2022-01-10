package ridev.com.br.api.leader;

import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.utils.hologram.CustomHologram;
import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class LeaderSchema {

    Main plugin;

    public LeaderSchema(Main plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::loadAllLeaders, 1, 20 * 30);

        Main.LOGGER.log(Level.INFO, "LeaderBoards carregadas com sucesso!");
    }

    public void loadAllLeaders() {

        if (!LeaderManager.getLeaders().isEmpty()) {
            for (Map.Entry<String, Leader> leaders : LeaderManager.getLeaders().entrySet()) {
                Leader leader = leaders.getValue();
                HashMap<Integer, String> places = new HashMap<>();
                CustomHologram hl = new CustomHologram(leader.getHologram());
                if (leader.getType().equals(LeaderType.KILLS)) {
                    String username;
                    if (!LeaderValues.getKillsLeader().isEmpty()) {
                        var locale = 1;
                        for (User us : LeaderValues.getKillsLeader()) {
                            username = us.getUsername();
                            places.put(locale, username);
                            locale++;
                            if (locale > 10) locale = 1;
                        }
                    }
                }
                if (leader.getType().equals(LeaderType.XP)) {
                    String username;
                    if (!LeaderValues.getXpLeader().isEmpty()) {
                        var locale = 1;
                        for (User us : LeaderValues.getXpLeader()) {
                            username = us.getUsername();
                            places.put(locale, username);
                            locale++;
                            if (locale > 10) locale = 1;
                        }
                    }
                }
                for (int i = 2; i < 10; i++) {
                    if (places.get(i - 1) != null && UserManager.getPlayer(places.get(i - 1)) != null) {
                        User us = UserManager.getPlayer(places.get(i - 1));
                        int number = leader.getType().equals(LeaderType.XP) ? us.getXp() : us.getKills();
                        hl.updateTextLine(i, FancyText.colored("&e" + (i - 1) + "&e. " + us.getRole().getPrefix() + " " + us.getUsername() + " &7- &e" + number));
                    } else {
                        break;
                    }
                }
            }


        } else {

            if (Main.getLoc().get("leaderboard") != null) {
                for (String s : Main.getLoc().getConfigurationSection("leaderboard").getKeys(false)) {
                    if (Main.getLoc().getString("leaderboard." + s + ".Type").equalsIgnoreCase("kills")) {
                        String name = Main.getLoc().getString("leaderboard." + s + ".Name");
                        Location loc = LocationAPI.deserialize(Main.getLoc().getString("leaderboard." + s + ".spawn"));
                        new BukkitRunnable() {
                            public void run() {
                                Leader leader = new Leader(name, LeaderType.KILLS, loc);
                                leader.createLeader();
                            }
                        }.runTaskLater(this.plugin, 1);
                    }
                }
                for (String s : Main.getLoc().getConfigurationSection("leaderboard").getKeys(false)) {
                    if (Main.getLoc().getString("leaderboard." + s + ".Type").equalsIgnoreCase("xp")) {
                        String name = Main.getLoc().getString("leaderboard." + s + ".Name");
                        Location loc = LocationAPI.deserialize(Main.getLoc().getString("leaderboard." + s + ".spawn"));
                        new BukkitRunnable() {
                            public void run() {
                                Leader leader = new Leader(name, LeaderType.XP, loc);
                                leader.createLeader();
                            }
                        }.runTaskLater(this.plugin, 1);
                    }
                }
            }
        }
    }


}
