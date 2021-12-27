package ridev.com.br.eventos;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpType;

public class DamagerFixer implements Listener {


    @EventHandler
    public void asd(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            final Player p = (Player) e.getDamager();
            User us = UserManager.getPlayer(p);
            if (!us.getWarp().equals(WarpType.LOBBY)) {
                if (p.getItemInHand().getType().equals(Material.DIAMOND_SWORD) || p.getItemInHand().getType().equals(Material.STONE_SWORD) || p.getItemInHand().getType().equals(Material.IRON_SWORD) || p.getItemInHand().getType().equals(Material.WOOD_SWORD) || p.getItemInHand().getType().equals(Material.DIAMOND_SWORD) || p.getItemInHand().getType().equals(Material.GOLD_SWORD)) {
                    final ItemStack sword = p.getItemInHand();
                    final double danoEspada = this.getDamage(sword.getType());
                    e.setDamage(danoEspada);
                    sword.setDurability((short) 2);
                }
            }
        }
    }

    @EventHandler
    public void aoquebrar(PlayerItemDamageEvent e) {
        if (!e.getItem().hasItemMeta()) return;
        e.setCancelled(true);
    }

    private double getDamage(final Material type) {
        double damage = 0.0;
        if (type.toString().contains("DIAMOND_")) {
            damage = 8.0;
        } else if (type.toString().contains("IRON_")) {
            damage = 5.0;
        } else if (type.toString().contains("STONE_")) {
            damage = 4.5;
        } else if (type.toString().contains("WOOD_")) {
            damage = 3.0;
        } else if (type.toString().contains("GOLD_")) {
            damage = 3.0;
        }
        if (!type.toString().contains("_SWORD")) {
            --damage;
            if (!type.toString().contains("_AXE")) {
                --damage;
                if (!type.toString().contains("_PICKAXE")) {
                    --damage;
                    if (!type.toString().contains("_SPADE")) {
                        damage = 1.0;
                    }
                }
            }
        }
        return damage;
    }
}
