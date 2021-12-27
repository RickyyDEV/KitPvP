package ridev.com.br.api.kit.kits;

import net.minecraft.server.v1_8_R3.EntityFishingHook;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Fisherman implements Kit {
    @Override
    public List<String> description() {
        return new java.util.ArrayList<>(Arrays.asList(
                "&r",
                "&7Pesque vítimas na guerra!",
                "&7Engane-as e traga-as mais perte de você!",
                "&7Para assim poder derrota-las!",
                "&r",
                " &eItens: ",
                "&71x Espada de pedra",
                "&71x Vara de pesca",
                "&r",
                " &eHabilidades: ",
                "&r",
                "&7Ao fisgar um player, você tem o poder de puxa-lo",
                "&7ate sua localização",
                "&r")
        );
    }

    @Override
    public ItemStack icone() {
        return new ItemStack(Material.FISHING_ROD);
    }

    @Override
    public int id() {
        return 4;
    }

    @Override
    public String name() {
        return "Fisherman";
    }

    @Override
    public HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        itens.put(0, transform(Material.STONE_SWORD, "&aEspada", true, ""));
        itens.put(1, transform(Material.FISHING_ROD, "&aFisherman (&7Direito)", false, "&7Clique aqui para", "&7Fisgar um player"));
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
        return itens;
    }

    @Override
    public int price() {
        return 5500;
    }

    @Override
    public KitRarity rarity() {
        return KitRarity.RARO;
    }

    @Override
    public Listener event() {
        return new Listener() {
            @EventHandler
            public void fish(PlayerFishEvent e) {
                if (e.getCaught() instanceof Player) {
                    User us = UserManager.getPlayer(e.getPlayer());
                    User victim = UserManager.getPlayer((Player) e.getCaught());
                    if (us.getKit() != null && us.getKit() instanceof Fisherman && victim.getKit() != null) {
                        e.getHook().setPassenger(victim.getPlayer());
                        e.getCaught().teleport(e.getPlayer().getLocation());
                    }
                }
            }

            @EventHandler
            public void aoTacarVara(EntityDamageByEntityEvent e) {
                if (e.getEntity() instanceof Player && e.getDamager() instanceof EntityFishingHook) {
                    Projectile projectile = (Projectile) e.getDamager();
                    if (projectile.getShooter() instanceof Player) {
                        if (projectile.getShooter().equals(e.getEntity())) {
                            e.setCancelled(true);
                        }
                    }
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
