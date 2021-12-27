package ridev.com.br.api.bau.animation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class BoxArmorStandEvent implements Listener {


    @EventHandler
    public void OnInteractAtEntity(PlayerInteractAtEntityEvent e) {

        if (!(e instanceof Player)) {
            e.setCancelled(true);
        }
    }
}
