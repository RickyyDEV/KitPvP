package ridev.com.br.api.inventory.signinv;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class CogumelosInv extends SimpleInventory {
    public CogumelosInv() {
        super("cogu.inv", FancyText.colored("&7Recraft de cogumelos"), 9 * 3);
    }


    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        InventoryItem redMush = InventoryItem.of(
                newMenuItem(Material.RED_MUSHROOM, 64, ""));
        InventoryItem brownMush = InventoryItem.of(
                newMenuItem(Material.BROWN_MUSHROOM, 64, ""));
        InventoryItem pot = InventoryItem.of(
                newMenuItem(Material.BOWL, 64, ""));

        editor.fillColumn(0, redMush);
        editor.fillColumn(1, brownMush);
        editor.fillColumn(2, pot);
        editor.fillColumn(3, redMush);
        editor.fillColumn(4, brownMush);
        editor.fillColumn(5, pot);
        editor.fillColumn(6, redMush);
        editor.fillColumn(7, brownMush);
        editor.fillColumn(8, pot);
    }

    public static ItemStack newMenuItemItemStack(ItemStack ironFence, String s, String... strings) {
        final ItemMeta kitem = ironFence.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        ironFence.setItemMeta(kitem);

        return ironFence;
    }

    public static ItemStack newMenuItem(Material ironFence, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }

    public static ItemStack newMenuItem(Material ironFence, int amount, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        item.setAmount(amount);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }


}
