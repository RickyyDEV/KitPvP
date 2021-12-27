package ridev.com.br.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;

import java.util.IllegalFormatException;

public class ChatEvent implements Listener {


    @EventHandler
    public void aoEnviar(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) return;
        Player p = e.getPlayer();
        User us = UserManager.getPlayer(p);
        try {
            if (p.hasPermission("rikitpvp.colorido")) {
                e.setFormat(FancyText.colored(" &7(" + us.getRank().getSymbol() + "&7) " + us.getRole().getPrefix() + p.getName() + FancyText.colored(" &8➸&f ") + e.getMessage().replaceAll("&", "§").replaceAll("%", " ")));
            } else {
                if (e.getMessage().contains("&") || e.getMessage().contains("§")) {
                    String finalMessage = e.getMessage().replaceAll("§", "&").replaceAll("%", " ");
                    e.setFormat(FancyText.colored(" &7(" + us.getRank().getSymbol() + "&7) " + us.getRole().getPrefix() + p.getName() + FancyText.colored(" &8➸&f ") + finalMessage));
                } else {
                    e.setFormat(FancyText.colored(" &7(" + us.getRank().getSymbol() + "&7) " + us.getRole().getPrefix() + p.getName() + FancyText.colored(" &8➸&f ") + e.getMessage().replaceAll("%", " ")));
                }
            }
        } catch (IllegalFormatException | NullPointerException illegalFormatException) {
            illegalFormatException.printStackTrace();
        }
    }
}
