package ridev.com.br.api.kit.kits;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ridev.com.br.api.combat.CombatLogAPI;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class Kangaroo implements Kit {

    public static List<Player> usingKang = new ArrayList<>();


    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::kangarooDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::kangarooPreco);
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::kangarooPermission);
    }

    @Override
    public @NonNull ItemStack icone() {
        return new ItemStack(Material.FIREWORK);
    }

    @Override
    public int id() {
        return 8;
    }

    @Override
    public @NonNull String name() {
        return "Kangaroo";
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack kangaroo = new ItemBuilder(Material.FIREWORK).setName("&aFogos de artificio &7(Kangaroo)").addLore("&aClique com o direito!").build();
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
        itens.put(1, kangaroo);
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
                if (us.getKit() != null && us.getKit() instanceof Kangaroo) {
                    if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getType().equals(Material.FIREWORK)) {
                            e.setCancelled(true);
                            if (CombatLogAPI.playerIsInCombat(p)) {
                                p.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::kangarooInBatle)));
                            } else {
                                if (!usingKang.contains(p)) {
                                    if (!p.isSneaking()) {
                                        p.setFallDistance(-2.0F);
                                        Vector vector = p.getEyeLocation().getDirection();
                                        vector.multiply(0.6F);
                                        vector.setY(1.0F);
                                        p.setVelocity(vector);
                                    } else {
                                        p.setFallDistance(-3.0F);
                                        Vector vector = p.getEyeLocation().getDirection();
                                        vector.multiply(1.35F);
                                        vector.setY(0.66D);
                                        p.setVelocity(vector);
                                    }
                                }
                                usingKang.add(p);
                            }
                        }
                    }
                }
            }

            @EventHandler
            public void onMove(PlayerMoveEvent event) {
                Player p = event.getPlayer();
                User us = UserManager.getPlayer(p);
                if (us != null) {
                    if (us.getKit() != null && us.getKit() instanceof Kangaroo) {
                        if (usingKang.contains(p)) {
                            Block b = p.getLocation().getBlock();
                            if (b != null) {
                                if ((b.getType() != Material.AIR) ||
                                        (b.getRelative(BlockFace.DOWN).getType() != Material.AIR))
                                    usingKang.remove(p);
                            }
                        }
                    }
                }
            }

            @EventHandler
            public void onDamage(EntityDamageEvent event) {
                Entity e = event.getEntity();
                if ((e instanceof Player)) {
                    Player player = (Player) e;
                    User us = UserManager.getPlayer(player);
                    if (((event.getEntity() instanceof Player)) &&
                            (event.getCause() == EntityDamageEvent.DamageCause.FALL) &&
                            (player.getInventory().contains(Material.FIREWORK)) &&
                            (event.getDamage() >= 12.0D) && us.getKit() != null && us.getKit() instanceof Kangaroo)
                        event.setDamage(12.0D);
                }
            }


            @EventHandler
            public void onDrop(PlayerDropItemEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() != null && us.getKit() instanceof Kangaroo) {
                    if (e.getItemDrop().getItemStack().getType().equals(Material.FIREWORK)) {
                        e.setCancelled(true);
                    }
                }
            }

        };
    }


}
