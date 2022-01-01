package ridev.com.br.eventos;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

public class BussolaEvent implements Listener {


    @EventHandler
    public void clicarEmBussola(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            User us = UserManager.getPlayer(e.getPlayer());
            Player p = e.getPlayer();
            if (us.getKit() != null) {
                boolean nulled = false;
                for (Entity players : e.getPlayer().getNearbyEntities(100.0D, 150.0D, 100.0D)) {
                    if (players instanceof Player) {
                        User us2 = UserManager.getPlayer((Player) players);

                        if (us2.getKit() != null) {
                            nulled = true;
                            p.setCompassTarget(players.getLocation());
                            p.sendMessage(FancyText.colored(LangValue.get(LangValue::bussolaSucess)));
                        }
                    }
                }
                if (!nulled) {
                    p.sendMessage(FancyText.colored(LangValue.get(LangValue::bussolaFailed)));
                }
            }
        }
    }
}
