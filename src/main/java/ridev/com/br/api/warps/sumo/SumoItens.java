package ridev.com.br.api.warps.sumo;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class SumoItens {
    public static void setIntens(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);


        p.getInventory().setItem(3, transform(Material.BLAZE_ROD, "&aDesafie um jogador! &7(Botão direito)"));

        ItemStack ink = new ItemStack(Material.INK_SACK, 1, (short) 8);
        p.getInventory().setItem(5, transform(ink, "&aDuelo Rápido! &7(&cDesativado&7)"));
        p.setMaxHealth(0.6);
        p.setHealth(0.5);
        p.updateInventory();
        p.setFireTicks(0);
        for (final PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
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
}
