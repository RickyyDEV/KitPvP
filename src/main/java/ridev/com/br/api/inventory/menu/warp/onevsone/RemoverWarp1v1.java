package ridev.com.br.api.inventory.menu.warp.onevsone;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.inventory.menu.warp.WarpRemoverInv;
import ridev.com.br.api.warps.Warp;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class RemoverWarp1v1 extends SimpleInventory {
    public RemoverWarp1v1() {
        super("warp.remover.1v1", FancyText.colored("&7Remover 1v1"), 9 * 5);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem setSpawn = InventoryItem.of(
                newMenuItem(Material.BLAZE_ROD, "&cRemover Spawn", "&7Clique aqui para", "&7Remover o spawn do 1v1!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null) {
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).removeSpawnInConfig();
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).setSpawn(null);
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).save();
                if (WarpLibrary.getWarps().get(WarpType.ONEVSONE).getFirstSpawn() == null && WarpLibrary.getWarps().get(WarpType.ONEVSONE).getSecondSpawn() == null)
                    WarpLibrary.getWarps().remove(WarpType.ONEVSONE);
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &61v1&7 removido com sucesso!"));
            } else {
                Warp warp = new Warp(WarpType.ONEVSONE);
                warp.removeSpawnInConfig();
                warp.setSpawn(null);
                warp.save();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &61v1&7 removido com sucesso!"));
            }
        });
        InventoryItem setFirstSpawn = InventoryItem.of(
                newMenuItem(Material.BLAZE_ROD, "&cRemover player spawn 1", "&7Clique aqui para", "&7Remover o spawn 1 do 1v1!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null) {
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).removeFirstSpawnInConfig();
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).setFirstSpawn(null);
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).save();
                if (WarpLibrary.getWarps().get(WarpType.ONEVSONE).getSpawn() == null && WarpLibrary.getWarps().get(WarpType.ONEVSONE).getSecondSpawn() == null)
                    WarpLibrary.getWarps().remove(WarpType.ONEVSONE);
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &61v1&7 removido com sucesso!"));
            } else {
                Warp warp = new Warp(WarpType.ONEVSONE);
                warp.removeFirstSpawnInConfig();
                warp.setFirstSpawn(null);
                warp.save();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &61v1&7 removido com sucesso!"));
            }
        });
        InventoryItem setSecondSpawn = InventoryItem.of(
                newMenuItem(Material.BLAZE_ROD, "&cRemover spawn 2", "&7Clique aqui para", "&7remover o spawn 2 do 1v1!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null) {
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).removeSecondSpawnInConfig();
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).setSecondSpawn(null);
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).save();
                if (WarpLibrary.getWarps().get(WarpType.ONEVSONE).getFirstSpawn() == null && WarpLibrary.getWarps().get(WarpType.ONEVSONE).getSpawn() == null)
                    WarpLibrary.getWarps().remove(WarpType.ONEVSONE);
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &61v1&7 removido com sucesso!"));
            } else {
                Warp warp = new Warp(WarpType.ONEVSONE);
                warp.removeSecondSpawnInConfig();
                warp.setSecondSpawn(null);
                warp.save();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &61v1&7 removido com sucesso!"));
            }
        });

        if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null && WarpLibrary.getWarps().get(WarpType.ONEVSONE).getSpawn() != null) {
            editor.setItem(20, setSpawn);
        }
        if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null && WarpLibrary.getWarps().get(WarpType.ONEVSONE).getFirstSpawn() != null) {
            editor.setItem(22, setFirstSpawn);
        }
        if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null && WarpLibrary.getWarps().get(WarpType.ONEVSONE).getSecondSpawn() != null) {
            editor.setItem(24, setSecondSpawn);
        }
        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para voltar"})).defaultCallback(a -> new WarpRemoverInv().init().openInventory(p));
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
