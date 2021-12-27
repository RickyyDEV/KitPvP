package ridev.com.br.api.warps.fps;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.eventos.Protecao;

public class FPS implements Listener {

    @EventHandler
    public void aoAtacar(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            User us = UserManager.getPlayer(p);
            if (us.getWarp().equals(WarpType.FPS)) {
                if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL) || e.getCause().equals(EntityDamageEvent.DamageCause.FALLING_BLOCK))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent ev) {
        if ((ev.getDamager() instanceof Player) && (ev.getEntity() instanceof Player)) {
            Player pqbateu = (Player) ev.getDamager();
            Player pqsofreu = (Player) ev.getEntity();
            User author = UserManager.getPlayer(pqbateu);
            User victim = UserManager.getPlayer(pqsofreu);
            if (author.getWarp().equals(WarpType.FPS) && victim.getWarp().equals(WarpType.FPS)) {
                if (((pqsofreu.getHealth() - ev.getFinalDamage()) <= 0.5) && ev.getEntity() instanceof Player) {
                    ev.setCancelled(true);
                    Bukkit.getPluginManager().callEvent(new OnPlayerWinFps(author, victim));

                    pqbateu.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_HELMET)});
                }
            }
        }
    }

    @EventHandler
    public void aoMover(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        User us = UserManager.getPlayer(p);
        if (us != null && us.getWarp().equals(WarpType.FPS)) {
            if (WarpLibrary.getWarp(WarpType.FPS).isItensBeforeFall()) {
                if (!FPSItem.hasItens(p)) {
                    if (p.getLocation().getY() < WarpLibrary.getWarp(WarpType.FPS).getSpawn().getY()) {
                        FPSItem.setItens(p);
                        Protecao.setImortal(p, false);
                    }
                }
            }
        }
    }

}
