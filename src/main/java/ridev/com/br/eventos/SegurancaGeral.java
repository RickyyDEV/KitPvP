package ridev.com.br.eventos;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.help.HelpTopic;
import org.bukkit.material.Openable;
import ridev.com.br.api.builder.BuildAPI;
import ridev.com.br.api.combat.CombatLogAPI;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.utils.other.PlayerAPI;
import ridev.com.br.utils.text.FancyText;

public class SegurancaGeral implements Listener {

    @EventHandler
    public void aoQuebrarSemBuild(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!BuildAPI.isBuilder(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void colocarCabeca(BlockPlaceEvent e) {
        if (!BuildAPI.isBuilder(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void aoFisica(BlockPhysicsEvent e) {
        e.setCancelled(true);
    }


    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        User us = UserManager.getPlayer(p);
        if (us.getWarp().equals(WarpType.LOBBY)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityVoid(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                Player p = (Player) e.getEntity();
                User us = UserManager.getPlayer(p);
                if (us.getWarp().equals(WarpType.LOBBY)) {
                    e.getEntity().teleport(WarpLibrary.getWarp(WarpType.LOBBY).getSpawn());
                } else {
                    PlayerAPI.killPlayer(p);
                }
            }
        }
    }

    @EventHandler
    public void placedupes(BlockPlaceEvent e) {
        if (e.getBlock().getType().toString().contains("REDSTONE")) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.TNT) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.DISPENSER) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.DROPPER) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.PISTON_BASE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void hopper(final InventoryOpenEvent e) {
        if (e.getInventory().getType() == InventoryType.HOPPER) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void cavalo(final PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Horse) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void openInv(InventoryOpenEvent e) {
        if (e.getInventory().getType() == InventoryType.HOPPER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDoorClick(PlayerInteractEvent e) {
        Block b = e.getClickedBlock();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (b.getType() == Material.TRAP_DOOR) {
                e.setCancelled(true);
                BlockState state = b.getState();
                Openable openable = (Openable) state.getData();
                openable.setOpen(false);
            }
        }
    }

    @EventHandler
    public void onUnknown(PlayerCommandPreprocessEvent event) {
        if (!event.isCancelled()) {
            Player p = event.getPlayer();
            String msg = event.getMessage().split(" ")[0];
            HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(msg);
            if (topic == null) {
                event.setCancelled(true);
                p.sendMessage(FancyText.colored("&c&lERRO &8➸ &cComando Desconhecido!!"));
            } else if (event.getMessage().equalsIgnoreCase("/me") || event.getMessage().equalsIgnoreCase("/pl") || event.getMessage().equalsIgnoreCase("/plugins") || event.getMessage().equalsIgnoreCase("/ver") || event.getMessage().toLowerCase().startsWith("/bukkit") || event.getMessage().toLowerCase().startsWith("/help") || event.getMessage().toLowerCase().startsWith("/version") || event.getMessage().toLowerCase().startsWith("/about") || event.getMessage().toLowerCase().startsWith("/?")) {
                event.setCancelled(true);
                p.sendMessage(FancyText.colored("&fPlugins (1): &aRiKitPvP V2"));
            } else if (CombatLogAPI.playerIsInCombat(p) && p.hasPermission("rikitpvp.admin") && !p.isOp()) {
                event.setCancelled(true);
                p.sendMessage(FancyText.colored("&eCOMBAT LOG &8➸ &cVocê não pode usar comando em combate!"));
            }
        }
    }

    @EventHandler
    public void banAds(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("rikitpvp.admin") && (e.getMessage().contains(".net") || e.getMessage().contains("Entre agora") || e.getMessage().contains("meu server") || e.getMessage().contains("(.)") || e.getMessage().contains(".host") || e.getMessage().contains(".minesv") || e.getMessage().contains(".playbr") || e.getMessage().contains(".cookiehosting") || e.getMessage().contains(".playmine") || e.getMessage().contains(".com") || e.getMessage().contains(".server") || e.getMessage().contains(".me") || e.getMessage().contains(".io") || e.getMessage().contains(".com") || e.getMessage().contains(".com.br") || e.getMessage().contains(".pro") || e.getMessage().contains(".nu") || e.getMessage().contains(".tk") || e.getMessage().contains("(,)") || e.getMessage().contains(",net") || e.getMessage().contains(",host") || e.getMessage().contains(",minesv") || e.getMessage().contains(",playbr") || e.getMessage().contains(",cookiehosting") || e.getMessage().contains(",playmine") || e.getMessage().contains(",com") || e.getMessage().contains(",server") || e.getMessage().contains(",me") || e.getMessage().contains(",io") || e.getMessage().contains(",com") || e.getMessage().contains(",com.br") || e.getMessage().contains(",pro") || e.getMessage().contains(",nu") || e.getMessage().contains(",tk"))) {
            e.setCancelled(true);
            p.sendMessage(FancyText.colored("&c&lWARN &8➸ &cVocê não pode enviar Links Aqui!"));
        }
    }
}
