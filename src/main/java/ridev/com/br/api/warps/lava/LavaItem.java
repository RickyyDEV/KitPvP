package ridev.com.br.api.warps.lava;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.HashMap;

public class LavaItem {
    static HashMap<Player, Boolean> pinv = new HashMap<Player, Boolean>();
    public static void setItens(Player p) {

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for (int i = 0; i < 36; i++) {
            if(i == 13) {
                p.getInventory().setItem(13, transform(Material.BOWL, "&aPotes", 64));
            } else if(i == 14){
                p.getInventory().setItem(14, transform(Material.RED_MUSHROOM, "&aCogumelo Vermelho", 64));
            } else if(i == 15) {
                p.getInventory().setItem(15, transform(Material.BROWN_MUSHROOM, "&aCogumelo Marrom", 64));
            } else {
                p.getInventory().setItem(i, transform(Material.MUSHROOM_SOUP, "&aSopa", 1));
            }
        }
        pinv.put(p, true);
    }


    public static boolean hasItens(Player p) {
        if(pinv.get(p) != null) {
            return pinv.get(p);
        } else {
            return false;
        }
    }

    private static ItemStack transform(Material item, String name, int amount, String... lore) {
        ItemStack i = new ItemStack(item);
        i.setAmount(amount);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(FancyText.colored(name));
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        i.setItemMeta(im);
        return i;
    }
}
