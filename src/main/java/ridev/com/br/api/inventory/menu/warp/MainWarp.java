package ridev.com.br.api.inventory.menu.warp;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.inventory.menu.MainInventory;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class MainWarp extends SimpleInventory {
    public MainWarp() {
        super("main.warp.inv", FancyText.colored("&7Menu De Warps"), 9 * 5);
    }

    public void openInv(Player p) {
        this.openInventory(p);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem boxLocs = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("green"), "&aSetar Warps", new String[]{"&7Clique aqui para", "&7Setar um Warps Na sua", "&7Localização"})).defaultCallback(a -> new WarpSetInv().init().openInventory(p));
        InventoryItem boxPlayerAdd = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("red"), "&aRemover Warps", new String[]{"&7Clique aqui para", "&7Remover uma Warp", "&7 Especifica"})).defaultCallback(a -> new WarpRemoverInv().init().openInventory(p));

        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para Voltar"})).defaultCallback(a -> new MainInventory().init().openInventory(p));

        editor.setItem(20, boxLocs);
        editor.setItem(24, boxPlayerAdd);
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
