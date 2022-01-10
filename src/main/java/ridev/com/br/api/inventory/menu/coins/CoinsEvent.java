package ridev.com.br.api.inventory.menu.coins;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoinsEvent implements Listener {
    @Getter
    static List<Player> players = new ArrayList<>();
    @Getter
    static HashMap<Player, User> user = new HashMap<>();


    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (players.contains(p)) {
            e.setCancelled(true);
            if (user.containsKey(p)) {
                if (e.getMessage().matches("-?\\d+")) {
                    int quantity = Integer.parseInt(e.getMessage());
                    User us = user.get(p);
                    us.addCoins(quantity);
                    Sound.NOTE_PLING.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7" + quantity + "&7 coins foram adicionados na conta de &6" + us.getUsername()));
                    user.remove(p);
                    players.remove(p);
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7O valor dado &cnão é um número&7! Tente novamente"));
                }
            } else {
                User us = UserManager.getPlayer(e.getMessage());
                if (us != null) {
                    user.put(p, us);
                    p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Agora me diga A quantidade de coins que deseja adicionar na conta do player."));
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &cEste jogador&c não encontra-se no banco de dados&7! Tente novamente"));
                }
            }
        }
    }

}
