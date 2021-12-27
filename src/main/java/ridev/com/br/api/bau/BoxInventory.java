package ridev.com.br.api.bau;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.bau.animation.BoxAnimation;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.inventory.loja.box.BoxShop;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.Map;

public class BoxInventory extends SimpleInventory {
    private static Box caixabox;


    public BoxInventory() {
        super("box.inventory", FancyText.colored("&7Minhas Caixas Misteriosas"), 9 * 6);
    }

    public static void openInv(Player p, Box box) {
        caixabox = box;
        inv.openInventory(p);
    }


    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player viewerPlayer = viewer.getPlayer();
        User us = UserManager.getPlayer(viewerPlayer);
        if (us.getAllBoxesCount() == 0) {
            InventoryItem web = InventoryItem.of(
                    newMenuItem(Material.WEB, "&cVazio", "")).defaultCallback(a -> a.setCancelled(true));
            editor.setItem(22, web);
        } else {
            for (Map.Entry<BoxType, Integer> box : us.getBoxes().entrySet()) {

                BoxType type = box.getKey();
                String[] description = type.getDescription();
                int boxSize = box.getValue();
                String beatifulName = type.getBeatifulName(boxSize);
                int slot = type.getSlot();

//                if (boxSize > 0) {
                InventoryItem caixa = InventoryItem.of(
                        newMenuItem(Material.ENDER_CHEST, boxSize, beatifulName, description)).defaultCallback(a -> {
                    if (boxSize > 0) {
                        if (!caixabox.isUsing()) {
                            BoxAnimation.startAnimation(caixabox, type, viewerPlayer);
                            viewerPlayer.closeInventory();
                        } else {
                            viewerPlayer.sendMessage(FancyText.colored("&b&lBOX &8➸ &cEsta caixa já está sendo usada."));

                        }
                    } else a.setCancelled(true);
                });
                editor.setItem(slot, caixa);
//                }
            }
        }
        InventoryItem emerald = InventoryItem.of(
                newMenuItem(Material.EMERALD, "&aLoja de Caixas Misteriosas", "&7Sem nenhuma caixa?", "&7Clique aqui para ir", "&7para a loja de caixas!")).defaultCallback(a -> new BoxShop().init().openInventory(viewerPlayer));
        editor.setItem(49, emerald);
    }

    static BoxInventory inv = new BoxInventory().init();

    public static ItemStack newMenuItem(Material ironFence, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }

    public static ItemStack newMenuItem(Material ironFence, int amount, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        item.setAmount(amount);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }


}
