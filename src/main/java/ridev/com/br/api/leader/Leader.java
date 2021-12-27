package ridev.com.br.api.leader;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.*;
import org.bukkit.Location;
import ridev.com.br.api.user.User;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.Main;
import ridev.com.br.utils.files.Files;
import ridev.com.br.utils.text.FancyText;

@Data
@Builder
@AllArgsConstructor
public class Leader {
    private String name;
    private LeaderType type;
    private Location location;
    private Hologram hologram;


    public Leader(String name, LeaderType type, Location location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public void createLeader() {
        if (this.type.equals(LeaderType.KILLS)) {
            // HOLOGRAM PART
            Hologram holograma = HologramsAPI.createHologram(Main.getInstance(), this.location);
            holograma.appendTextLine(FancyText.colored("&b&lTOP 10 KILLS"));
            holograma.appendTextLine(FancyText.colored("&r"));
            String username;
            if (!LeaderValues.getKillsLeader().isEmpty()) {
                var locale = 1;
                for (User us : LeaderValues.getKillsLeader()) {
                    username = us.getUsername();
                    String number = FancyText.formatNumber(us.getKills());
                    holograma.appendTextLine(FancyText.colored("&e" + locale + "&e. " + us.getRole().getPrefix() + " " + username + " &7- &e" + number));
                    locale++;
                    if (locale > 10) locale = 1;
                }
                if (LeaderValues.getKillsLeader().size() < 10) {
                    for (int i = LeaderValues.getKillsLeader().size() + 1; i < 11; i++) {
                        username = "Nenhum";
                        holograma.appendTextLine(FancyText.colored("&e" + i + "&e. &7" + username));
                    }
                }
            } else {
                for (int i2 = 1; i2 < 10; i2++) {
                    username = "Nenhum";
                    holograma.appendTextLine(FancyText.colored("&e" + i2 + "&e. &7" + username));
                }
            }
            this.hologram = holograma;
            Leader leader = Leader.builder()
                    .hologram(this.hologram)
                    .type(this.type)
                    .location(this.location)
                    .name(this.name)
                    .build();
            LeaderManager.getLeaders().put(this.name, leader);
        }

        if (this.type.equals(LeaderType.XP)) {
            // HOLOGRAM PART
            Hologram holograma = HologramsAPI.createHologram(Main.getInstance(), this.location);
            assert holograma != null;
            holograma.appendTextLine(FancyText.colored("&b&lTOP 10 XP"));
            holograma.appendTextLine(FancyText.colored("&r"));
            String username;
            int xp;


            if (!LeaderValues.getXpLeader().isEmpty()) {
                var locale = 1;
                for (User us : LeaderValues.getXpLeader()) {
                    username = us.getUsername();
                    String number = FancyText.formatNumber(us.getXp());
                    holograma.appendTextLine(FancyText.colored("&e" + locale + "&e. " + us.getRole().getPrefix() + " " + username + "&7 - &e" + number));
                    locale++;
                    if (locale > 10) locale = 1;
                }
                if (LeaderValues.getXpLeader().size() < 10) {
                    for (int i = LeaderValues.getXpLeader().size() + 1; i < 11; i++) {
                        username = "Nenhum";
                        holograma.appendTextLine(FancyText.colored("&e" + i + "&e. &7" + username));
                    }
                }
            } else {
                for (int i2 = 1; i2 < 10; i2++) {
                    username = "Nenhum";
                    holograma.appendTextLine(FancyText.colored("&e" + i2 + "&e. &7" + username));
                }
            }
            this.hologram = holograma;
            Leader leader = Leader.builder()
                    .hologram(this.hologram)
                    .type(this.type)
                    .location(this.location)
                    .name(this.name)
                    .build();
            LeaderManager.getLeaders().put(this.name, leader);
        }
    }

    @SneakyThrows
    public void saveInConfig() {
        String loc = LocationAPI.serlialize(this.location);
        Main.getLoc().set("leaderboard." + this.name + ".Name", this.name);
        Main.getLoc().set("leaderboard." + this.name + ".Type", this.type.toString().toLowerCase());
        Main.getLoc().set("leaderboard." + this.name + ".spawn", loc);
        Files.saveLocConfig();
    }

    @SneakyThrows
    public void removeInConfig() {
        Main.getLoc().set("leaderboard." + this.name, null);
        Files.saveLocConfig();
    }


    public void removeLeader() {
        this.getHologram().delete();
        LeaderManager.getLeaders().remove(this.getName());
    }
}
