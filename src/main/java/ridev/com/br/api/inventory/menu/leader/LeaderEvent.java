package ridev.com.br.api.inventory.menu.leader;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.leader.Leader;
import ridev.com.br.api.leader.LeaderManager;
import ridev.com.br.api.leader.LeaderType;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;

public class LeaderEvent implements Listener {

    @Getter
    static HashMap<Player, LeaderType> players = new HashMap<>();


    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (players.containsKey(p)) {
            e.setCancelled(true);
            String leaderName = e.getMessage();
            if (LeaderManager.getLeader(leaderName) != null) {
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&6&lLEADER &8➸ &cJá existe uma Leader Board com esse nome! Tente novamente!"));
            } else {
                new BukkitRunnable() {
                    public void run() {
                        Leader lead = new Leader(leaderName, players.get(p), p.getLocation().clone().add(0, 4, 0));
                        lead.createLeader();
                        lead.saveInConfig();
                        Sound.NOTE_PLING.play(p, 10, 10);
                        p.sendMessage(FancyText.colored("&6&lLEADER &8➸ &7LeaderBoard " + leaderName + " &acriada com sucesso&7!"));
                        players.remove(p);
                    }
                }.runTask(Main.getInstance());

            }
        }
    }
}