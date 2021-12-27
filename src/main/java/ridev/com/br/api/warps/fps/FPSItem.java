package ridev.com.br.api.warps.fps;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.HashMap;

public class FPSItem {
    public static HashMap<Player, Boolean> pinv = new HashMap<>();

    public static void setItens(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.getInventory().setBoots(transform(Material.IRON_BOOTS, "&eKitPvP", ""));
        p.getInventory().setChestplate(transform(Material.IRON_CHESTPLATE, "&eKitPvP", ""));
        p.getInventory().setHelmet(transform(Material.IRON_HELMET, "&eKitPvP", ""));
        p.getInventory().setLeggings(transform(Material.IRON_LEGGINGS, "&eKitPvP", ""));
        p.getInventory().setItem(0, transform(Material.IRON_SWORD, "&aEspada", true, ""));
        for (int i = 1; i < 36; i++) {
            if (i == 13) {
                p.getInventory().setItem(13, transform(Material.BOWL, 64));
            } else if (i == 14) {
                p.getInventory().setItem(14, transform(Material.RED_MUSHROOM, 64));
            } else if (i == 15) {
                p.getInventory().setItem(15, transform(Material.BROWN_MUSHROOM, 64));
            } else {
                p.getInventory().setItem(i, transform(Material.MUSHROOM_SOUP));
            }
        }
        pinv.put(p, true);
    }


    private static ItemStack transform(Material item, String name, String... lore) {
        ItemStack i = new ItemStack(item);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(FancyText.colored(name));
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        i.setItemMeta(im);
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


    public static boolean hasItens(Player p) {
        return pinv.containsKey(p);
    }

    private static ItemStack transform(Material item, int amount) {
        ItemStack i = new ItemStack(item);
        i.setAmount(amount);
        return i;
    }
}
