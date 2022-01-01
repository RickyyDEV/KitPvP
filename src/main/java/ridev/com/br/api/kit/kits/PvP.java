package ridev.com.br.api.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PvP implements Kit {
    @Override
    public List<String> description() {

        return new java.util.ArrayList<>(Arrays.asList(
                "&r",
                "&7O velho kit pvp! Porém com cara nova!",
                "&7Batalhe com adversarios com armas perigosas!",
                "&r",
                " &eItens: ",
                "&71x Espada de ferro",
                "&r",
                " &eHabilidades: ",
                "&r",
                "&7Você não possue habilidades...",
                "&7Mas possue uma grande vontade de lutar!",
                "&r")
        );
    }

    @Override
    public ItemStack icone() {
        return new ItemStack(Material.IRON_SWORD);
    }

    @Override
    public int id() {
        return 1;
    }

    @Override
    public String name() {
        return "PvP";
    }

    @Override
    public HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        itens.put(0, transform(Material.STONE_SWORD, "&aEspada", true, ""));
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
        return 0;
    }

    @Override
    public KitRarity rarity() {
        return KitRarity.COMUM;
    }

    @Override
    public Listener event() {
        return new Listener() {
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
