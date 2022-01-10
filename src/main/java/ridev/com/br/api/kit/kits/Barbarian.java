package ridev.com.br.api.kit.kits;

import com.google.common.collect.Maps;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.kit.KitWinEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Barbarian implements Kit {

    public static Map<Player, String> kills = Maps.newConcurrentMap();

    @Override
    public @NonNull String name() {
        return "Barbarian";
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::barbarianPermission);
    }

    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::barbarianDescription));
    }

    @Override
    public @NonNull ItemStack icone() {
        return new ItemStack(Material.WOOD_SWORD);
    }

    @Override
    public int id() {
        return 1;
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
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
        itens.put(8, bussola);
        return itens;
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::barbarianPreco);
    }

    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.MEDIANO;
    }

    @Override
    public Listener event() {
        return new Listener() {


            @EventHandler
            public void onKill(EntityDamageByEntityEvent e) {
                if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                    Player author = (Player) e.getDamager();
                    Player victim = (Player) e.getEntity();
                    User authorUs = UserManager.getPlayer(author);
                    User victimUs = UserManager.getPlayer(victim);

                    if (authorUs.getKit() != null && authorUs.getKit() instanceof Barbarian) {
                        if ((victim.getHealth() - e.getFinalDamage()) < 0.5) {
                            e.setCancelled(true);

                            if (kills.get(author) != null) {
                                int userKills = Integer.parseInt(kills.get(author));
                                kills.replace(author, String.valueOf((userKills + 1)));
                            } else {
                                kills.put(author, String.valueOf(1));
                            }
                            if (author.getItemInHand().getItemMeta().getDisplayName().contains(FancyText.colored("&aBarbarian"))) {
                                int userKills = Integer.parseInt(kills.get(author));
                                if (author.getItemInHand().getType().equals(Material.WOOD_SWORD)) {
                                    if (userKills == 3) {
                                        ItemStack stone = new ItemBuilder(Material.STONE_SWORD).setName("&aBarbarian II").setUnbreakable(true).build();
                                        author.setItemInHand(stone);
                                        author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::barbarianLevelPassed).replace("%sword%", "pedra")));
                                    } else {
                                        author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::barbarianRemaing).replace("%kills_remaing%", String.valueOf((3 - userKills)))));
                                    }
                                } else if (author.getItemInHand().getType().equals(Material.STONE_SWORD)) {
                                    if (userKills == 6) {
                                        ItemStack iron = new ItemBuilder(Material.IRON_SWORD).setName("&aBarbarian III").setUnbreakable(true).build();
                                        author.setItemInHand(iron);
                                        author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::barbarianLevelPassed).replace("%sword%", "ferro")));
                                    } else {
                                        author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::barbarianRemaing).replace("%kills_remaing%", String.valueOf((6 - userKills)))));
                                    }

                                } else if (author.getItemInHand().getType().equals(Material.IRON_SWORD)) {
                                    if (userKills == 9) {
                                        ItemStack diamond = new ItemBuilder(Material.DIAMOND_SWORD).setName("&aBarbarian IIII").setUnbreakable(true).build();
                                        author.setItemInHand(diamond);
                                        author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::barbarianLevelPassed).replace("%sword%", "diamante (nível final)")));
                                    } else {
                                        author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::barbarianRemaing).replace("%kills_remaing%", String.valueOf((9 - userKills)))));
                                    }
                                }
                            }
                            Bukkit.getPluginManager().callEvent(new KitWinEvent(authorUs, victimUs));
                        }
                    }
                }
            }


            @EventHandler
            public void mexerInInv(InventoryMoveItemEvent e) {
                Player p = (Player) e.getInitiator().getHolder();
                User us = UserManager.getPlayer(p);
                if ((us.getKit() != null && us.getKit() instanceof Barbarian) && e.getItem().getItemMeta().getDisplayName().contains(FancyText.colored("&aBarbarian"))) {
                    e.setCancelled(true);
                }
            }

            @EventHandler
            public void drop(PlayerDropItemEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);
                if ((us.getKit() != null && us.getKit() instanceof Barbarian) && e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains(FancyText.colored("&aBarbarian"))) {
                    e.setCancelled(true);
                }
            }

        };

    }


}
