package ridev.com.br.api.rank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.text.FancyText;

public class Ranks {


    public static void checkRankPlayer(User player) {
        if (!player.getRank().equals(RankType.PROFESSIONAL)) {
            if (player.getRank().getNextRank() != null) {
                if (player.getXp() >= player.getRank().getNextRank().getXp()) {
                    player.setRank(player.getRank().getNextRank());
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.sendMessage(FancyText.colored("&b&lRANK &8➸ &7O jogador " + player.getRole().getRoleColor() + player.getUsername() + " &7Passou para o rank " + player.getRank().getBeatifulName()));
                        for (Player p2 : Bukkit.getOnlinePlayers()) {
                            User user = UserManager.getPlayer(p2);
                            MineReflect.sendNameTag(p2, FancyText.colored(user.getRole().getPrefix() + ""), FancyText.colored(" &7(" + user.getRank().getSymbol() + "&7)"), user.getRole().getPriority());
                        }
                    }
                }
            }
        }
    }
}
