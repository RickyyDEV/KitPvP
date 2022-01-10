package ridev.com.br.api.kit.kits;

import com.google.common.collect.Maps;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
import java.util.Map;

public class Frost implements Kit {

    public static final Map<Player, Player> playerFrosted = Maps.newConcurrentMap();

    @Override
    public @NonNull String name() {
        return "Frost";
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::frostPermission);
    }

    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::frostDescription));
    }

    @Override
    public @NonNull ItemStack icone() {
        return CacheSystem.getItem("ice_head");
    }

    @Override
    public int id() {
        return 5;
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack ice = new ItemBuilder(Material.ICE).setName("&aGelo &7(Frost)").addLore("&aClique com o direito!").build();
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
        itens.put(1, ice);
        itens.put(8, bussola);
        return itens;
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::frostPreco);
    }

    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.RARO;
    }

    @Override
    public Listener event() {
        return new Listener() {
            @EventHandler
            public void placeIceBlock(BlockPlaceEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() != null && us.getKit() instanceof Frost) {
                    if (e.getBlock().getType().equals(Material.ICE)) {
                        e.setCancelled(true);
                    }
                }
            }

            @EventHandler
            public void drop(PlayerDropItemEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() != null && us.getKit() instanceof Frost) {
                    if (e.getItemDrop().getItemStack().getType().equals(Material.ICE)) {
                        e.setCancelled(true);
                    }
                }
            }

            @EventHandler
            public void interact(PlayerInteractEntityEvent e) {
                Player author = e.getPlayer();
                User authorUs = UserManager.getPlayer(author);
                if (authorUs.getKit() != null && authorUs.getKit() instanceof Frost) {
                    if (author.getItemInHand().getType().equals(Material.ICE)) {
                        if (e.getRightClicked() instanceof Player) {
                            if (CooldownAPI.isInCooldown(author.getName(), "frost")) {
                                author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::frostCooldown).replace("%time%", String.valueOf(CooldownAPI.getTimeLeft(author.getName(), "frost")))));
                            } else {
                                CooldownAPI cd = new CooldownAPI(author.getName(), "dinamite", 7);
                                Player clicked = (Player) e.getRightClicked();
                                author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::frostedAuthor).replace("%player%", clicked.getName())));
                                author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::frostedVictim)));
                                playerFrosted.put(author, clicked);
                                cd.start();
                                new BukkitRunnable() {
                                    public void run() {
                                        author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::frostedUnfreeze).replace("%player%", clicked.getName())));
                                        playerFrosted.remove(author);
                                    }
                                }.runTaskLater(Main.getInstance(), 20 * 7);
                            }
                        }
                    }
                }
            }

            @EventHandler
            public void mexer(PlayerMoveEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);
                if (us.getKit() != null) {
                    if (playerFrosted.containsValue(p)) {
                        e.setTo(p.getLocation());
                    }
                }

            }
        };
    }
}
