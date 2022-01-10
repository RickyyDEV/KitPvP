package ridev.com.br.api.warps.lava;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpType;

public class Lava implements Listener {

    @EventHandler
    public void seBater(EntityDamageByEntityEvent e) {
        if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
            User author = UserManager.getPlayer((Player) e.getDamager());
            User victim = UserManager.getPlayer((Player) e.getEntity());
            if (author.getWarp().equals(WarpType.LAVA) && victim.getWarp().equals(WarpType.LAVA)) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        User us = UserManager.getPlayer(p);
        if (us.getWarp().equals(WarpType.LAVA)) {
            e.setDeathMessage(null);

        }
    }

    @EventHandler
    public void damage(EntityDamageEvent ev) {
        if (ev.getEntity() instanceof Player) {
            Player p = (Player) ev.getEntity();
            User us = UserManager.getPlayer(p);
            if (ev.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                if (us.getWarp().equals(WarpType.LAVA)) {
                    if ((p.getHealth() - ev.getFinalDamage()) < 0.5) {
                        Bukkit.getPluginManager().callEvent(new OnPlayerLoseLava(us));
                    }
                }
            }
        }
    }

}

