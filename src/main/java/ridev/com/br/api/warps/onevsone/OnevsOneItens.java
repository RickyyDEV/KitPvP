package ridev.com.br.api.warps.onevsone;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class OnevsOneItens {


    public static void setIntens(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);


        p.getInventory().setItem(3, transform(Material.BLAZE_ROD, "&aDesafie um jogador! &7(Botão direito)"));

        ItemStack ink = new ItemStack(Material.INK_SACK, 1, (short) 8);
        p.getInventory().setItem(5, transform(ink, "&aDuelo Rápido! &7(&cDesativado&7)"));
        p.setMaxHealth(0.5);
        p.setHealth(0.5);
        p.updateInventory();
        p.setFireTicks(0);
        for (final PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
    }

    public static void setIntensBatalha(Player p) {
        p.getInventory().clear();
        setitem(p, Material.STONE_SWORD, "&aEspada", 0);
        setitem(p, Material.MUSHROOM_SOUP, "&aSopa", 1);
        setitem(p, Material.MUSHROOM_SOUP, "&aSopa", 2);
        setitem(p, Material.MUSHROOM_SOUP, "&aSopa", 3);
        setitem(p, Material.MUSHROOM_SOUP, "&aSopa", 4);
        setitem(p, Material.MUSHROOM_SOUP, "&aSopa", 5);
        setitem(p, Material.MUSHROOM_SOUP, "&aSopa", 6);
        setitem(p, Material.MUSHROOM_SOUP, "&aSopa", 7);
        setitem(p, Material.MUSHROOM_SOUP, "&aSopa", 8);
        p.setMaxHealth(20.0);
        p.setHealth(20.0);
        p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        p.updateInventory();
    }

    public static ItemStack transform(Material item, String name, String... lore) {
        ItemStack i = new ItemStack(item);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(FancyText.colored(name));
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack transform(ItemStack item, String name, String... lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(FancyText.colored(name));
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        item.setItemMeta(im);
        return item;
    }

    public static void setitem(final Player p, final Material mat, final String nome, final int lugar) {
        final ItemStack item = new ItemStack(mat);
        final ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(FancyText.colored(nome));
        item.setItemMeta(itemmeta);
        p.getInventory().setItem(lugar, item);
        p.updateInventory();
    }
}
