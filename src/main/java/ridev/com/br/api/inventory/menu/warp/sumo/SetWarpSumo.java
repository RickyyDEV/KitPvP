package ridev.com.br.api.inventory.menu.warp.sumo;

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

public class SetWarpSumo extends SimpleInventory {
    public SetWarpSumo() {
        super("warp.set.sumo", FancyText.colored("&7Criar sumô"), 9 * 5);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem setSpawn = InventoryItem.of(
                newMenuItem(Material.GOLDEN_APPLE, "&aSetar spawn", "&7Clique aqui para", "&7setar o spawn do sumô!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.SUMO) != null) {
                WarpLibrary.getWarps().get(WarpType.SUMO).setSpawn(p.getLocation());
                WarpLibrary.getWarps().get(WarpType.SUMO).save();
                WarpLibrary.getWarps().get(WarpType.SUMO).saveInConfig();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &6Sumô&7 setado com sucesso!"));
            } else {
                p.closeInventory();
                Warp warp = new Warp(WarpType.SUMO);
                warp.setSpawn(p.getLocation());
                warp.save();
                warp.saveInConfig();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &6Sumô&7 setado com sucesso!"));
            }
        });
        InventoryItem setFirstSpawn = InventoryItem.of(
                newMenuItem(Material.GOLDEN_APPLE, "&aSetar player spawn 1", "&7Clique aqui para", "&7setar o spawn 1 do sumô!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.SUMO) != null) {
                WarpLibrary.getWarps().get(WarpType.SUMO).setFirstSpawn(p.getLocation());
                WarpLibrary.getWarps().get(WarpType.SUMO).save();
                WarpLibrary.getWarps().get(WarpType.SUMO).saveInConfig();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &6Sumô &7setado com sucesso!"));
            } else {
                Warp warp = new Warp(WarpType.SUMO);
                warp.setFirstSpawn(p.getLocation());
                warp.save();
                warp.saveInConfig();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &6Sumô&7 setado com sucesso!"));
            }
        });
        InventoryItem setSecondSpawn = InventoryItem.of(
                newMenuItem(Material.GOLDEN_APPLE, "&aSetar spawn 2", "&7Clique aqui para", "&7setar o spawn 2 do sumô!")).defaultCallback(a -> {
            if (WarpLibrary.getWarps().get(WarpType.SUMO) != null) {
                WarpLibrary.getWarps().get(WarpType.SUMO).setSecondSpawn(p.getLocation());
                WarpLibrary.getWarps().get(WarpType.SUMO).save();
                WarpLibrary.getWarps().get(WarpType.SUMO).saveInConfig();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &6Sumô&7 setado com sucesso!"));
            } else {
                Warp warp = new Warp(WarpType.SUMO);
                warp.setSecondSpawn(p.getLocation());
                warp.save();
                warp.saveInConfig();
                p.closeInventory();
                p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Spawn do &6Sumô&7 setado com sucesso!"));
            }
        });
        if (WarpLibrary.getWarps().get(WarpType.SUMO) == null || WarpLibrary.getWarps().get(WarpType.SUMO).getSpawn() == null) {
            editor.setItem(20, setSpawn);
        }
        if (WarpLibrary.getWarps().get(WarpType.SUMO) == null || WarpLibrary.getWarps().get(WarpType.SUMO).getFirstSpawn() == null) {
            editor.setItem(22, setFirstSpawn);
        }
        if (WarpLibrary.getWarps().get(WarpType.SUMO) == null || WarpLibrary.getWarps().get(WarpType.SUMO).getSecondSpawn() == null) {
            editor.setItem(24, setSecondSpawn);
        }

        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para voltar"})).defaultCallback(a -> new WarpSetInv().init().openInventory(p));
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
