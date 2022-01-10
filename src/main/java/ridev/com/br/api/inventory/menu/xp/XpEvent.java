package ridev.com.br.api.inventory.menu.xp;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;
import ridev.com.br.utils.other.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XpEvent implements Listener {

    @Getter
    static List<Player> players = new ArrayList<>();

    @Getter
    static HashMap<Player, User> user = new HashMap<>();


    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (players.contains(p)) {
            e.setCancelled(true);
            if (user.containsKey(p)) {
                if (e.getMessage().matches("-?\\d+")) {
                    int quantity = Integer.parseInt(e.getMessage());
                    user.get(p).addXp(quantity);
                    Sound.NOTE_PLING.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&a&lXP &8➸ &7Você adicionou &a" + quantity + " XP &7na conta do jogador &6" + user.get(p).getUsername()));
                    user.remove(p);
                    players.remove(p);
                }
            } else {
                User us = UserManager.getPlayer(e.getMessage());
                if (us != null) {
                    user.put(p, us);
                    p.sendMessage(FancyText.colored("&a&lXP &8➸ &7Agora diga-me a quantidade de &aXP&7!"));
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&a&lXP &8➸ &7Este usuário &cnão existe&7! Tente novamente!"));
                }
            }
        }
    }


}
