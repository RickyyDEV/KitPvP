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

public class CocoabeanInv extends SimpleInventory {
    public CocoabeanInv() {
        super("cocoa.inv", FancyText.colored("&7Recraft de cocoabeans"), 9 * 2);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        InventoryItem coca = InventoryItem.of(
                newMenuItemItemStack(new ItemStack(Material.INK_SACK, 64, (byte) 3), 64, ""));
        InventoryItem pot = InventoryItem.of(
                newMenuItem(Material.BOWL, 64, ""));
        editor.fillColumn(0, coca);
        editor.fillColumn(1, pot);
        editor.fillColumn(2, coca);
        editor.fillColumn(3, pot);
        editor.fillColumn(4, coca);
        editor.fillColumn(5, pot);
        editor.fillColumn(6, coca);
        editor.fillColumn(7, pot);
        editor.fillColumn(8, coca);
    }

    public static ItemStack newMenuItemItemStack(ItemStack ironFence, int amount, String s, String... strings) {
        ironFence.setAmount(amount);
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
