package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
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

public class Troller implements Kit {
    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::trollerDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::trollerPreco);
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::trollerPermission);
    }

    @Override
    public @NonNull ItemStack icone() {
        return CacheSystem.getItem("troll_head");
    }

    @Override
    public int id() {
        return 16;
    }

    @Override
    public @NonNull String name() {
        return "Troller";
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack bola = new ItemBuilder(Material.SNOW_BALL).setName("&aTroller &7(Clique direito)").build();
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
        itens.put(1, bola);
        itens.put(8, bussola);
        return itens;
    }


    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.COMUM;
    }

    @Override
    public Listener event() {
        return new Listener() {

            @EventHandler
            public void onSendBall(EntityDamageByEntityEvent e) {
                if (e.getDamager() instanceof Snowball) {
                    Snowball bola = (Snowball) e.getDamager();
                    Player author = (Player) bola.getShooter();
                    User authorUs = UserManager.getPlayer(author);
                    if (authorUs.getKit() != null && authorUs.getKit() instanceof Troller) {
                        Player victim = (Player) e.getEntity();
                        if (victim != null) {
                            User victimUs = UserManager.getPlayer(victim);
                            if (victimUs.getKit() != null) {
                                Location victimLocation = victim.getLocation();
                                Location authorLocation = author.getLocation();
                                victim.teleport(authorLocation);
                                author.teleport(victimLocation);
                                author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::trollerTeleport).replace("%usre%", victim.getName())));
                            }
                        }
                    }
                }
            }

            @EventHandler
            public void interact(PlayerInteractEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() instanceof Troller) {
                    if (p.getItemInHand().getType().equals(Material.SNOW_BALL)) {
                        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                            ItemStack bola = new ItemBuilder(Material.SNOW_BALL).setName("&aTroller &7(Clique direito)").build();
                            p.getInventory().addItem(bola);
                            if (CooldownAPI.isInCooldown(p.getName(), "troller")) {
                                e.setCancelled(true);
                                p.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::trollerCooldown).replace("%time%", String.valueOf(CooldownAPI.getTimeLeft(p.getName(), "troller")))));
                            } else {
                                CooldownAPI cd = new CooldownAPI(p.getName(), "troller", 4);
                                cd.start();
                            }
                        }
                    }
                }
            }


            @EventHandler
            public void onDrop(PlayerDropItemEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() != null && us.getKit() instanceof Troller) {
                    if (e.getItemDrop().getItemStack().getType().equals(Material.SNOW_BALL)) {
                        e.setCancelled(true);
                    }
                }
            }
        };
    }

}
