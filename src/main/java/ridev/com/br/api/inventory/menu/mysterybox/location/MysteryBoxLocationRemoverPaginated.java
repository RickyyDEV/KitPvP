package ridev.com.br.api.inventory.menu.mysterybox.location;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.bau.Box;
import ridev.com.br.api.bau.BoxManager;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;
import ridev.com.br.utils.other.Sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MysteryBoxLocationRemoverPaginated extends PagedInventory {
    public MysteryBoxLocationRemoverPaginated() {
        super("inv.mysterybox.location.add.pagination", FancyText.colored("&7Bau Remover"), 9 * 6);
    }


    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(21);
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        List<InventoryItemSupplier> itemSuppliers = new ArrayList<>();
        Player p = viewer.getPlayer();

        for (Map.Entry<String, Box> data : BoxManager.getBoxes().entrySet()) {
            Box box = data.getValue();
            itemSuppliers.add(() -> InventoryItem.of(
                    newMenuItem(Material.ENDER_CHEST, "&c" + box.getName(), "&7Clique Aqui para", "&7Remover esta Mystery Box!")).defaultCallback(a -> {
                p.closeInventory();
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Mystery Box &6" + box.getName() + " &7Removida Com sucesso!"));
                box.removeInConfig();
                box.removeMysteryBox();
            }));
        }
        return itemSuppliers;
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();

        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para Voltar"})).defaultCallback(a -> new MysteryBoxLocationMain().init().openInventory(p));
        editor.setItem(40, voltar);
    }


    public static ItemStack newMenuItem(Material ironFence, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }


    public static ItemStack newMenuItemItemStack(ItemStack ironFence, String s, String[] strings) {
        final ItemMeta kitem = ironFence.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        ironFence.setItemMeta(kitem);

        return ironFence;
    }
}
