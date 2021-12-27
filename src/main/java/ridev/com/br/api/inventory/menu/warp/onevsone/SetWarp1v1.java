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
import ridev.com.br.api.inventory.menu.warp.WarpSetInv;
import ridev.com.br.api.warps.Warp;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class SetWarp1v1 extends SimpleInventory {
    public SetWarp1v1() {
        super("warp.set.1v1", FancyText.colored("&71v1 Setter"), 9 * 5);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem setSpawn = InventoryItem.of(
                newMenuItem(Material.BLAZE_ROD, "&aSetar Spawn", "&7Clique aqui para", "&7Setar o Spawn do 1v1!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null) {
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).setSpawn(p.getLocation());
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).saveInConfig();
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).save();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn Do &61v1&7 setado com sucesso!"));
            } else {
                Warp warp = new Warp(WarpType.ONEVSONE);
                warp.setSpawn(p.getLocation());
                warp.saveInConfig();
                warp.save();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn Do &61v1&7 setado com sucesso!"));
            }
        });
        InventoryItem setFirstSpawn = InventoryItem.of(
                newMenuItem(Material.BLAZE_ROD, "&aSetar Player Spawn 1", "&7Clique aqui para", "&7Setar o Spawn 1 do 1v1!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null) {
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).setFirstSpawn(p.getLocation());
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).saveInConfig();
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).save();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn Do &61v1&7 setado com sucesso!"));
            } else {
                Warp warp = new Warp(WarpType.ONEVSONE);
                warp.setFirstSpawn(p.getLocation());
                warp.saveInConfig();
                warp.save();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn Do &61v1&7 setado com sucesso!"));
            }
        });
        InventoryItem setSecondSpawn = InventoryItem.of(
                newMenuItem(Material.BLAZE_ROD, "&aSetar Spawn 2", "&7Clique aqui para", "&7Setar o Spawn 2 do 1v1!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) != null) {
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).setSecondSpawn(p.getLocation());
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).saveInConfig();
                WarpLibrary.getWarps().get(WarpType.ONEVSONE).save();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn Do &61v1&7 setado com sucesso!"));
            } else {
                Warp warp = new Warp(WarpType.ONEVSONE);
                warp.setSecondSpawn(p.getLocation());
                warp.saveInConfig();
                warp.save();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn Do &61v1&7 setado com sucesso!"));
            }
        });
        if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) == null || WarpLibrary.getWarps().get(WarpType.ONEVSONE).getSpawn() == null) {
            editor.setItem(20, setSpawn);
        }
        if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) == null || WarpLibrary.getWarps().get(WarpType.ONEVSONE).getFirstSpawn() == null) {
            editor.setItem(22, setFirstSpawn);
        }
        if (WarpLibrary.getWarps().get(WarpType.ONEVSONE) == null || WarpLibrary.getWarps().get(WarpType.ONEVSONE).getSecondSpawn() == null) {
            editor.setItem(24, setSecondSpawn);
        }

        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para Voltar"})).defaultCallback(a -> new WarpSetInv().init().openInventory(p));
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
