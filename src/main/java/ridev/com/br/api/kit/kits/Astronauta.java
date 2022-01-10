package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.cooldown.CooldownAPI;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Astronauta implements Kit {
    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::astronautaDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::astronautaPreco);
    }

    @Override
    public @NonNull ItemStack icone() {
        return CacheSystem.getItem("astronauta_head");
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public @NonNull String name() {
        return "Astronauta";
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::astronautaPermission);
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack estrela = new ItemBuilder(Material.NETHER_STAR).setName("&aMeteoritos &7(Clique Direito)").addLore("&aClique com o direito!").build();
        ItemStack bussola = new ItemBuilder(Material.COMPASS).setName("&aProcurar jogadores").addLore("&aClique com o direito!").build();
        for (int i = 1; i < 36; i++) {
            if (i == 13) {
                itens.put(13, pote);
            } else if (i == 14) {
                itens.put(14, coguVermelho);
            } else if (i == 15) {
                itens.put(15, coguMarrom);
            } else {
                itens.put(i, sopa);
            }
        }
        itens.put(0, espada);
        itens.put(1, estrela);
        itens.put(8, bussola);
        return itens;
    }


    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.RARO;
    }

    @Override
    public Listener event() {
        return new Listener() {
            @EventHandler
            public void interact(PlayerInteractEvent e) {
                User us = UserManager.getPlayer(e.getPlayer());
                Player p = e.getPlayer();
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    if (p.getItemInHand().getType().equals(Material.NETHER_STAR)) {
                        if (us.getKit() instanceof Astronauta) {
                            if (!CooldownAPI.isInCooldown(p.getName(), "astronauta")) {
                                CooldownAPI cd = new CooldownAPI(p.getName(), "astronauta", 10);
                                Location area = e.getClickedBlock().getLocation();
                                new BukkitRunnable() {
                                    public void run() {
                                        p.getWorld().strikeLightning(area.clone().subtract(0, 0, 1));
                                        p.getWorld().strikeLightning(area.clone().add(0, 0, 1));
                                        p.getWorld().strikeLightning(area.clone().add(1, 0, 0));
                                    }
                                }.runTaskLaterAsynchronously(Main.getInstance(), 1);
                                cd.start();
                                p.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::astronautaSended)));
                            } else {
                                p.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::astronautaCooldown).replace("%time%", String.valueOf(CooldownAPI.getTimeLeft(p.getName(), "astronauta")))));
                            }
                        }
                    }
                }
            }

            @EventHandler
            public void onDrop(PlayerDropItemEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() != null && us.getKit() instanceof Astronauta) {
                    if (e.getItemDrop().getItemStack().getType().equals(Material.NETHER_STAR)) {
                        e.setCancelled(true);
                    }
                }
            }

        };
    }

}
