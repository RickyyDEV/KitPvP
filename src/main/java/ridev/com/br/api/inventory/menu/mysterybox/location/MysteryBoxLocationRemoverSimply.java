package ridev.com.br.api.inventory.menu.mysterybox.location;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class MysteryBoxLocationRemoverSimply extends SimpleInventory {
    public MysteryBoxLocationRemoverSimply() {
        super("inv.mysterybox.location.add.simply", FancyText.colored("&7Removedor de baús"), 9 * 6);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem semArena = InventoryItem.of(
                newMenuItemItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "&cSem Mystery Box", new String[]{"&7Nenhuma Mystery Box foi setada", "&7Para adicionar alguma, acesse o menu de", "&7Adicionar Mystery Box"})).defaultCallback(a -> a.setCancelled(true));
        editor.setItem(22, semArena);

        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para voltar"})).defaultCallback(a -> new MysteryBoxLocationMain().init().openInventory(p));

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
