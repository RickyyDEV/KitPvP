package ridev.com.br.eventos;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpType;

import java.util.HashMap;

public class Protecao implements Listener {

    private enum ImortalEnum {
        OFF, ON
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static final HashMap<Player, ImortalEnum> imortal = new HashMap();

    @EventHandler
    public void dano(EntityDamageEvent e) {
        if ((e.getEntity() instanceof Player && e.getCause() != DamageCause.LAVA)) {
            Player p = (Player) e.getEntity();
            if (isImortal(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player d = (Player) e.getEntity();
            User us = UserManager.getPlayer(d);
            if (us.getWarp().equals(WarpType.LAVA)) {
                d.damage(2.0);
            } else {
                if ((e.getDamager() instanceof Player)) {
                    Player p = (Player) e.getDamager();
                    if (isImortal(p)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }


    public static ImortalEnum getImortal(Player p) {
        return imortal.get(p);
    }

    public static boolean isImortal(Player p) {
        return getImortal(p) == ImortalEnum.ON;
    }

    public static void setImortal(Player p, boolean i) {
        if (i) {
            p.setGameMode(GameMode.SURVIVAL);
            imortal.put(p, ImortalEnum.ON);
        } else {
            p.setGameMode(GameMode.SURVIVAL);
            imortal.put(p, ImortalEnum.OFF);
        }
    }

}
