package ridev.com.br.api.kit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ridev.com.br.api.kit.kits.Barbarian;
import ridev.com.br.api.kit.kits.Frost;
import ridev.com.br.api.kit.kits.Gladiador;
import ridev.com.br.api.kit.kits.Naruto;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;

public class KitUtils implements Listener {


    @EventHandler(priority = EventPriority.MONITOR)
    public void aoBater(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player author = (Player) e.getDamager();
            Player victm = (Player) e.getEntity();
            User authorUs = UserManager.getPlayer(author);
            User victimUs = UserManager.getPlayer(victm);

            if (authorUs.getKit() != null && victimUs.getKit() != null) {
                if (!(authorUs.getKit() instanceof Gladiador && authorUs.getKit() instanceof Barbarian) && !(victimUs.getKit() instanceof Gladiador)) {
                    if ((victm.getHealth() - e.getFinalDamage()) <= 0.5) {
                        e.setCancelled(true);
                        if (victimUs.getKit() instanceof Naruto) {
                            if (Naruto.playersWolfs.containsKey(victimUs) && Naruto.playersWolfs.get(victimUs) != null)
                                Naruto.playersWolfs.get(victimUs).remove();
                        }
                        if (victimUs.getKit() instanceof Barbarian) {
                            Barbarian.kills.remove(victm);
                        }
                        if (victimUs.getKit() instanceof Frost) {
                            Frost.playerFrosted.remove(victm);
                        }
                        Bukkit.getPluginManager().callEvent(new KitWinEvent(authorUs, victimUs));
                    }
                }
            }
        }
    }
}
