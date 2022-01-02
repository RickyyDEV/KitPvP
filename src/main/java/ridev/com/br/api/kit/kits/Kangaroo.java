package ridev.com.br.api.kit.kits;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import ridev.com.br.api.combat.CombatLogAPI;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
public class Kangaroo implements Kit {

    public static List<Player> usingKang = new ArrayList<>();


    @Override
    public @NonNull List<String> description() {
        return new java.util.ArrayList<>(Arrays.asList(
                "&r",
                "&7Saia por ai voando e",
                "&7surpreendendo seus inimigos",
                "&7Com ataques surpresas e furtivos!",
                "&7Descoberto",
                "&r",
                " &eItens: ",
                "&71x Espada de pedra",
                "&r",
                " &eHabilidades: ",
                "&r",
                "&7Clicando com o botão direito no item do kangaroo",
                "&7Você ganha um impulso!",
                "&r")
        );
    }

    @Override
    public @NonNull ItemStack icone() {
        return new ItemStack(Material.FIREWORK);
    }

    @Override
    public int id() {
        return 5;
    }

    @Override
    public @NonNull String name() {
        return "Kangaroo";
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        itens.put(0, transform(Material.STONE_SWORD, "&aEspada", true, ""));
        itens.put(1, transform(Material.FIREWORK, "&aKangaroo (&7Direito)", false, "&7Clique aqui para", "&7dar um impulso!"));
        for (int i = 2; i < 36; i++) {
            if (i == 13) {
                itens.put(13, transform(Material.BOWL, 64));
            } else if (i == 14) {
                itens.put(14, transform(Material.RED_MUSHROOM, 64));
            } else if (i == 15) {
                itens.put(15, transform(Material.BROWN_MUSHROOM, 64));
            } else {
                itens.put(i, transform(Material.MUSHROOM_SOUP));
            }
        }
        itens.put(8, transform(Material.COMPASS, "&aProcurar jogadores", false));
        return itens;
    }

    @Override
    public int price() {
        return 6000;
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
                                p.sendMessage(FancyText.colored("&b&lKANGAROO &8➸ &7Você não pode usar o kit kangaroo quando estiver em batalha!"));
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

        };
    }


    private static ItemStack transform(Material item, int amount) {
        ItemStack i = new ItemStack(item);
        i.setAmount(amount);
        return i;
    }

    private static ItemStack transform(Material item) {
        return new ItemStack(item);
    }

    private static ItemStack transform(Material item, String name, boolean encantada, String... lore) {
        ItemStack i = new ItemStack(item);
        ItemMeta im = i.getItemMeta();
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        im.setDisplayName(FancyText.colored(name));
        if (encantada) {
            i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        i.setItemMeta(im);
        return i;
    }

}
