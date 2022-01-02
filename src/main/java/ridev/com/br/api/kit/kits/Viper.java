package ridev.com.br.api.kit.kits;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.kit.KitWinEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Data
public class Viper implements Kit {

    @Override
    public @NotNull List<String> description() {

        return new java.util.ArrayList<>(Arrays.asList(
                "&r",
                "&7Seja Traiçoeiro igual uma cobra!",
                "&7Injete veneno em seus adversários!",
                "&r",
                " &eItens: ",
                "&71x Espada De Pedra",
                "&r",
                " &eHabilidades: ",
                "&r",
                "&7Tenha 50% de Chance envenenar o seu oponente!",
                "&r"
        ));
    }

    @Override
    public @NotNull ItemStack icone() {
        return transform(Material.SPIDER_EYE);
    }

    @Override
    public int id() {
        return 2;
    }

    @Override
    public @NotNull String name() {
        return "Viper";
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack is = transform(Material.STONE_SWORD, "&aEspada", true, "");

        itens.put(0, is);
        for (int i = 1; i < 36; i++) {
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
        return 3000;
    }

    @Override
    public @NotNull KitRarity rarity() {
        return KitRarity.COMUM;
    }

    @Override
    public @NotNull Listener event() {
        return new Listener() {

            @EventHandler
            public void aoBater(EntityDamageByEntityEvent e) {
                if (((e.getDamager() instanceof Player)) && ((e.getEntity() instanceof Player))) {
                    Player p = (Player) e.getEntity();
                    Player d = (Player) e.getDamager();
                    User us = UserManager.getPlayer(d);
                    if (us.getKit() != null && (us.getKit() instanceof Viper)) {
                        Random r = new Random();
                        int rand = r.nextInt(100);
                        if (rand >= 50)
                            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0));
                    }
                }
            }

            @EventHandler
            public void aoquebrar(PlayerItemDamageEvent e) {
                if (!e.getItem().hasItemMeta()) return;
                if (e.getItem().getType().equals(Material.STONE_SWORD)) {
                    e.setCancelled(true);
                }
            }


            @EventHandler
            public void damage(EntityDamageByEntityEvent ev) {
                if ((ev.getDamager() instanceof Player) && (ev.getEntity() instanceof Player)) {
                    Player pqbateu = (Player) ev.getDamager();
                    Player pqsofreu = (Player) ev.getEntity();
                    User author = UserManager.getPlayer(pqbateu);
                    User victim = UserManager.getPlayer(pqsofreu);
                    if (author.getKit() != null && victim.getKit() != null) {
                        if ((pqsofreu.getHealth() - ev.getFinalDamage()) <= 0.5) {
                            ev.setCancelled(true);
                            Bukkit.getPluginManager().callEvent(new KitWinEvent(author, victim));

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
        i.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        if (encantada) im.setDisplayName(FancyText.colored(name));
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        im.setDisplayName(FancyText.colored(name));
        if (encantada) {
            i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        i.setItemMeta(im);
        return i;
    }
}
