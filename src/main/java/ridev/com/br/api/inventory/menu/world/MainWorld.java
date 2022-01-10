package ridev.com.br.api.inventory.menu.world;

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

public class MainWorld extends SimpleInventory {
    public MainWorld() {
        super("main.world.inv", FancyText.colored("&7Menu de mundos"), 9 * 5);
    }

    public void openInv(Player p) {
        this.openInventory(p);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem carregar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("world_load"), "&aCarregar mundo", new String[]{"&7Clique aqui para carregar mundos!"})).defaultCallback(a -> new LoadWorldInv().init().openInventory(p));
        InventoryItem teletransportar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("world_teleport"), "&aTeletransportar", new String[]{"&7Clique aqui para se teletransportar", "&7para algum mundo carregado!"})).defaultCallback(a -> new TeleportWorldInv().init().openInventory(p));

        InventoryItem descarregar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("world_remove"), "&aDescarregar mundos", new String[]{"&7Clique aqui para descarregar um mundo!"})).defaultCallback(a -> new UnloadWorldInv().init().openInventory(p));

        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para voltar"})).defaultCallback(a -> new MainInventory().init().openInventory(p));

        editor.setItem(20, carregar);
        editor.setItem(22, teletransportar);
        editor.setItem(24, descarregar);

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
