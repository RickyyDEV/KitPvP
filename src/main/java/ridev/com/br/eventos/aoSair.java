package ridev.com.br.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ridev.com.br.api.bau.BoxEvent;
import ridev.com.br.api.combat.CombatLogAPI;
import ridev.com.br.api.inventory.menu.coins.CoinsEvent;
import ridev.com.br.api.inventory.menu.xp.XpEvent;

public class aoSair implements Listener {


    @EventHandler
    public void sair(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        XpEvent.getPlayers().remove(p);
        BoxEvent.getPlayersChat().remove(p);
        BoxEvent.getPlayers().remove(p);
        BoxEvent.getType().remove(p);
        BoxEvent.getNames().remove(p);
        BoxEvent.getUser().remove(p);
        CoinsEvent.getPlayers().remove(p);
        CoinsEvent.getUser().remove(p);
        CombatLogAPI.pcombat.remove(p);
    }
}
