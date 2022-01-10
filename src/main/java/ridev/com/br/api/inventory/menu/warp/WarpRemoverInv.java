package ridev.com.br.api.inventory.menu.warp;

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
import ridev.com.br.api.inventory.menu.warp.onevsone.RemoverWarp1v1;
import ridev.com.br.api.inventory.menu.warp.sumo.RemoverWarpSumo;
import ridev.com.br.api.lobby.LobbyManager;
import ridev.com.br.api.warps.Warp;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class WarpRemoverInv extends SimpleInventory {
    public WarpRemoverInv() {
        super("warp.remover.inv", FancyText.colored("&7Removedor de warps"), 9 * 5);
    }


    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        if (WarpLibrary.getWarpSetted().isEmpty()) {
            InventoryItem semArena = InventoryItem.of(
                    newMenuItemItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "&cSem warps", "&7Nenhuma warp foi Setada", "&7Para adicionar alguma, acesse o menu de", "&7setar warps")).defaultCallback(a -> a.setCancelled(true));
            editor.setItem(22, semArena);
        } else {
            int slot = 0;
            for (WarpType warps : WarpLibrary.getWarpSetted()) {

                InventoryItem warpLocs = InventoryItem.of(
                        newMenuItem(warps.getItem(), "&a" + warps.getName(), "&7Clique aqui para", "&7setar uma warp na sua", "&7localização")).defaultCallback(a -> {
                    if (warps.equals(WarpType.LOBBY)) {
                        LobbyManager.removeLobby();
                        p.closeInventory();
                        Sound.NOTE_PLING.play(p, 10, 10);
                        p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Você removeu a warp &6" + warps.getName() + " &7com sucesso!"));
                    } else {
                        if (warps.isDuel()) {
                            if (warps.equals(WarpType.ONEVSONE)) new RemoverWarp1v1().init().openInventory(p);
                            if (warps.equals(WarpType.SUMO)) new RemoverWarpSumo().init().openInventory(p);
                        } else {
                            p.closeInventory();
                            Warp warp = new Warp(warps);
                            warp.removeInConfig();
                            warp.setSpawn(null);
                            WarpLibrary.getWarps().remove(warps);
                            Sound.NOTE_PLING.play(p, 10, 10);
                            p.sendMessage(FancyText.colored("&9&lWARPS &8➸ &7Você removeu a warp &6" + warps.getName() + " &7com sucesso!"));
                        }
                    }
                });
                editor.setItem(20 + slot, warpLocs);
                slot++;

            }
        }

        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", "&7Clique aqui para voltar")).defaultCallback(a -> new MainWarp().init().openInventory(p));
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

}
