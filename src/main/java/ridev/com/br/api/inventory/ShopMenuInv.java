package ridev.com.br.api.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.inventory.loja.box.BoxShop;
import ridev.com.br.api.inventory.loja.kits.ShopKits;
import ridev.com.br.api.kit.KitLibrary;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class ShopMenuInv extends SimpleInventory {
    public ShopMenuInv() {
        super("shop.menu.inv", FancyText.colored("&aLoja"), 9 * 6);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        int kits_size = us.getKits().size();
        InventoryItem kits = InventoryItem.of(
                newMenuItem(Material.DIAMOND_SWORD, "&aKits", "&r", "&7Clique para comprar kits!", "&r", "&fDesbloquados: &7" + kits_size + "/" + KitLibrary.getKits().size())).defaultCallback(a -> {
            new ShopKits().init().openInventory(p);
        });

        InventoryItem box = InventoryItem.of(
                newMenuItem(Material.ENDER_CHEST, "&6Caixas misteriosas", "&r", "&7Clique para Comprar Caixas Misteriosas!", "&r")).defaultCallback(a -> {
            new BoxShop().init().openInventory(p);
        });

        InventoryItem animations = InventoryItem.of(
                newMenuItem(Material.PAINTING, "&aAnimações", "&r", "&7Adicione particulas de acordo", "&7com as suas ações!", "&r")).defaultCallback(a -> {
            p.sendMessage(FancyText.colored("&cEm desenvolvimento..."));
        });


        editor.setItem(13, kits);
        editor.setItem(31, box);
        editor.setItem(40, animations);
    }


    public static ItemStack newMenuItem(Material ironFence, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }
}
