package ridev.com.br.eventos;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;

public class SopaEvent implements Listener {
    @EventHandler
    public void onSopa(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && p.getItemInHand().getType().equals(Material.MUSHROOM_SOUP)) {
            if (p.getHealth() < 20.0) {
                p.getItemInHand().setType(Material.BOWL);
                p.setHealth(Math.min(p.getHealth() + 7.0, p.getMaxHealth()));
                p.getWorld().playEffect(p.getEyeLocation(), Effect.HEART, 1);
            }
        }
    }


    @EventHandler
    public void dropAnimation(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (e.getItemDrop().getItemStack().getType().equals(Material.STONE_SWORD) || e.getItemDrop().getItemStack().getType().equals(Material.DIAMOND_SWORD) || e.getItemDrop().getItemStack().getType().equals(Material.IRON_SWORD) || e.getItemDrop().getItemStack().getType().equals(Material.GOLD_SWORD) || e.getItemDrop().getItemStack().getType().equals(Material.GOLD_SWORD)) {

            e.setCancelled(true);
        } else {

            new BukkitRunnable() {
                public void run() {
                    e.getItemDrop().remove();
                    e.getItemDrop().getWorld().playEffect(e.getItemDrop().getLocation(), Effect.SMOKE, 1);
                }
            }.runTaskLater(Main.getInstance(), 40);

        }
    }
}
