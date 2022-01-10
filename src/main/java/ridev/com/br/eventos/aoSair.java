package ridev.com.br.eventos;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ridev.com.br.api.bau.BoxEvent;
import ridev.com.br.api.inventory.menu.coins.CoinsEvent;
import ridev.com.br.api.inventory.menu.leader.LeaderEvent;
import ridev.com.br.api.inventory.menu.mysterybox.addbox.AddBoxEvent;
import ridev.com.br.api.inventory.menu.mysterybox.location.MysteryBoxLocationSetEvent;
import ridev.com.br.api.inventory.menu.xp.XpEvent;
import ridev.com.br.api.kit.kits.Barbarian;
import ridev.com.br.api.kit.kits.Frost;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.scoreboard.ScoreboardManager;

public class aoSair implements Listener {


    @EventHandler(priority = EventPriority.MONITOR)
    public void sair(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        User us = UserManager.getPlayer(p);
        XpEvent.getPlayers().remove(p);
        XpEvent.getUser().remove(p);
        BoxEvent.getPlayersChat().remove(p);
        BoxEvent.getPlayers().remove(p);
        BoxEvent.getType().remove(p);
        BoxEvent.getNames().remove(p);
        BoxEvent.getUser().remove(p);
        AddBoxEvent.getBox().remove(us);
        AddBoxEvent.getUsers().remove(us);
        AddBoxEvent.getPlayers().remove(us);
        MysteryBoxLocationSetEvent.getName().remove(p);
        MysteryBoxLocationSetEvent.getPlayers().remove(p);
        CoinsEvent.getPlayers().remove(p);
        CoinsEvent.getUser().remove(p);
        LeaderEvent.getPlayers().remove(p);
        if (us.getKit() instanceof Barbarian) {
            Barbarian.kills.remove(p);
        }
        if (us.getKit() instanceof Frost) {
            Frost.playerFrosted.remove(p);
        }
        for (Player pls : Bukkit.getOnlinePlayers()) {
            ScoreboardManager.reloadScoreboard(pls);
        }
    }
}
