package ridev.com.br.api.inventory.menu.world;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.Material;
import org.bukkit.World;
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

public class TeleportWorldInv extends PagedInventory {
    public TeleportWorldInv() {
        super("teleport.world.inv", FancyText.colored("&7World Teleporter"), 9 * 6);
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
        if (!WorldAPI.getLoadedWorlds().isEmpty()) {
            for (World s : WorldAPI.getLoadedWorlds()) {
                if (!p.getLocation().getWorld().equals(s)) {
                    itens.add(() -> InventoryItem.of(
                            newMenuItem(Material.LEAVES, "&a" + s.getName(), "&7Clique aqui para se teletransportar para o mundo&6 " + s.getName())).defaultCallback(a -> {
                        p.closeInventory();
                        try {
                            p.teleport(s.getSpawnLocation());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            p.sendMessage(FancyText.colored("&d&lWORLD &8➸ &7Você foi teletransportado para o mundo &6" + s.getName() + "&7!"));
                        }
                    }));
                }
            }

        }
        return itens;
    }
}
