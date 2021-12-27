package ridev.com.br.eventos;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import ridev.com.br.api.inventory.*;
import ridev.com.br.utils.text.FancyText;

public class Menus implements Listener {


    @EventHandler
    public void aoClicarInvsLobby(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (p.getItemInHand().getType().equals(Material.COMPASS) && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(FancyText.colored("&aServidores &7(Clique Direito)"))) {
                new ServerInventory().init().openInventory(p);
            }
            if (p.getItemInHand().getType().equals(Material.EXPLOSIVE_MINECART) && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(FancyText.colored("&aWarps &7(Clique Direito)"))) {
                new WarpsInventory().init().openInventory(p);
            }
            if ((p.getItemInHand().getType().equals(Material.SKULL_ITEM) || p.getItemInHand().getType().equals(Material.SKULL)) && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(FancyText.colored("&aSeu Perfil &7(Clique Direito)"))) {
                new ProfileInventory().init().openInventory(p);
            }
            if (p.getItemInHand().getType().equals(Material.EMERALD) && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(FancyText.colored("&aLoja &7(Clique Direito)"))) {
                new ShopMenuInv().init().openInventory(p);

            }
            if (p.getItemInHand().getType().equals(Material.NETHER_STAR) && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(FancyText.colored("&aKits &7(Clique Direito)"))) {
                new KitsInventory().init().init().openInventory(p);
            }
        }
    }
}
