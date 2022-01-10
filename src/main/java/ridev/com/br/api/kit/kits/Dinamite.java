package ridev.com.br.api.kit.kits;

import com.google.common.collect.Maps;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.cooldown.CooldownAPI;
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

public class Dinamite implements Kit {

    HashMap<Player, Player> playerInDinamite = Maps.newHashMap();

    @Override
    public @NonNull String name() {
        return "Dinamite";
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::dinamitePermission);
    }

    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::dinamiteDescription));
    }

    @Override
    public @NonNull ItemStack icone() {
        return new ItemStack(Material.TNT);
    }

    @Override
    public int id() {
        return 2;
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack tnt = new ItemBuilder(Material.TNT).setName("&aDinamite").addLore("&7Exploda seus inimigos!").build();
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
        itens.put(1, tnt);
        itens.put(8, bussola);
        return itens;
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::dinamitePreco);
    }

    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.RARO;
    }

    @Override
    public Listener event() {
        return new Listener() {
            @EventHandler
            public void interact(PlayerInteractAtEntityEvent e) {
                if (e.getRightClicked() instanceof Player) {
                    Player author = e.getPlayer();
                    Player victim = (Player) e.getRightClicked();

                    User authorUs = UserManager.getPlayer(author);
                    User victimUs = UserManager.getPlayer(victim);

                    if (authorUs.getKit() != null && authorUs.getKit() instanceof Dinamite) {
                        if (CooldownAPI.isInCooldown(author.getName(), "dinamite")) {
                            author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::dinamiteCooldownWait).replace("%time%", String.valueOf(CooldownAPI.getTimeLeft(author.getName(), "dinamite")))));
                        } else {
                            CooldownAPI cd = new CooldownAPI(author.getName(), "dinamite", 15);
                            ItemStack tnt = new ItemBuilder(Material.TNT).setName("&aDinamite").addLore("&7Clique para se sair!").build();

                            victim.getInventory().setHelmet(tnt);

                            playerInDinamite.put(author, victim);

                            victim.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::dinamitePuted)));
                            cd.start();
                            final int[] i = {5};
                            new BukkitRunnable() {
                                public void run() {

                                    if (playerInDinamite.containsValue(victim)) {
                                        if (i[0] == 0) {
                                            victim.getInventory().setHelmet(null);
                                            playerInDinamite.remove(author);
                                            Bukkit.getPluginManager().callEvent(new KitWinEvent(authorUs, victimUs));
                                        } else {
                                            victim.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::dinamiteExploseTime).replace("%time%", String.valueOf(i[0]))));
                                            i[0]--;
                                        }
                                    } else {
                                        this.cancel();
                                    }
                                }
                    }.runTaskTimer(Main.getInstance(), 20, 20);
                        }
                    }
                }
            }

            @EventHandler
            public void inventory(InventoryClickEvent e) {
                if (e.getWhoClicked() instanceof Player) {
                    Player p = (Player) e.getWhoClicked();
                    User us = UserManager.getPlayer(p);
                    if (us.getKit() != null && us.getKit() instanceof Dinamite) {
                        if (e.getCurrentItem() != null) {
                            if (e.getCurrentItem().hasItemMeta()) {
                                if (e.getCurrentItem().getType().equals(Material.TNT)) {
                                    if (playerInDinamite.containsValue(p)) {
                                        Player crimnes = null;
                                        for (Map.Entry<Player, Player> players : playerInDinamite.entrySet()) {
                                            if (players.getValue().equals(p)) {
                                                crimnes = players.getKey();
                                                break;
                                            }
                                        }
                                        e.setCancelled(true);
                                        e.setCurrentItem(null);
                                        p.getInventory().setHelmet(null);
                                        playerInDinamite.remove(crimnes);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }
}
