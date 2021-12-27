package ridev.com.br.api.cargos;

import lombok.Getter;
import lombok.val;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.Main;
import ridev.com.br.utils.text.FancyText;

public class TagAPI {

    @Getter
    private static final TagAPI instance = new TagAPI();

    private boolean executing;


    public void updateTag() {

        if (executing) return;
        executing = true;

        val plugin = Main.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (val player : Bukkit.getOnlinePlayers()) {

                Scoreboard scoreboard = player.getScoreboard() == null ? Bukkit.getScoreboardManager().getNewScoreboard() : player.getScoreboard();
                for (val target : Bukkit.getOnlinePlayers()) {
                    val user = UserManager.getPlayer(target);
                    val group = user.getRole();

                    val teamIdentifier = group.getPriority() + group.getRoleName();
                    var prefix = FancyText.colored(group.getPrefix() + " ");
                    var suffix = FancyText.colored("&7(" + user.getRank().getSymbol() + "&7)");

                    if (prefix.length() > 16) prefix = prefix.substring(0, 16);
                    if (suffix.length() > 16) suffix = suffix.substring(0, 16);

                    var team = scoreboard.getTeam(teamIdentifier);
                    if (team == null) {
                        team = scoreboard.registerNewTeam(teamIdentifier);
                        team.setPrefix(prefix);
                        team.setSuffix(suffix);
                    }

                    team.addPlayer(target);
                }

                player.setScoreboard(scoreboard);
            }

            executing = false;
        });
    }
}