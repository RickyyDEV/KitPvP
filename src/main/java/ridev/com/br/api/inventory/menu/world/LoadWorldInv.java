package ridev.com.br.api.inventory.menu.world;

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
import ridev.com.br.api.world.WorldAPI;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadWorldInv extends PagedInventory {
    public LoadWorldInv() {
        super("load.world.inv", FancyText.colored("&7Carregar mundos"), 9 * 6);
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(21);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", "&7Clique aqui para voltar")).defaultCallback(a -> new MainWorld().init().openInventory(p));
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


    public static ItemStack newMenuItemItemStack(ItemStack ironFence, String s, String... strings) {
        final ItemMeta kitem = ironFence.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        ironFence.setItemMeta(kitem);

        return ironFence;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        List<InventoryItemSupplier> itens = new ArrayList<>();
        Player p = viewer.getPlayer();
        if (!WorldAPI.getUnloadedWorlds().isEmpty()) {
            for (String s : WorldAPI.getUnloadedWorlds()) {
                itens.add(() -> InventoryItem.of(
                        newMenuItem(Material.LEAVES, "&a" + s, "&7Clique aqui para carregar o mundo&6 " + s)).defaultCallback(a -> {
                    p.closeInventory();
                    try {
                        WorldAPI.loadWorld(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        p.sendMessage(FancyText.colored("&d&lWORLD &8➸ &7Mundo &6" + s + "&7 carregado com sucesso!"));
                    }
                }));
            }

        }
        return itens;
    }
}
