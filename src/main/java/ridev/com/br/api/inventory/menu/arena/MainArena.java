package ridev.com.br.api.inventory.menu.arena;

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

public class MainArena extends SimpleInventory {
    public MainArena() {
        super("main.arena.inv", FancyText.colored("&7Menu De Arenas"), 9 * 5);
    }

    public void openInv(Player p) {
        this.openInventory(p);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem setarArena = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("green"), "&aSetar Arenas", new String[]{"&7Clique aqui para", "&7setar Arenas!"})).defaultCallback(a -> new SetArenas().init().openInventory(p));
        InventoryItem removerArena = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("red"), "&cRemover Arenas", new String[]{"&7Clique Aqui para", "&7remover Arenas"})).defaultCallback(a -> new RemoveArenas().init().openInventory(p));
        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para Voltar"})).defaultCallback(a -> new MainInventory().init().openInventory(p));
        editor.setItem(21, setarArena);
        editor.setItem(23, removerArena);
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
