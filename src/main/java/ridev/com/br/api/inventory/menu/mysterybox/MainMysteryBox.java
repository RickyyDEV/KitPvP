package ridev.com.br.api.inventory.menu.mysterybox;

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
import ridev.com.br.api.inventory.menu.mysterybox.addbox.AddBoxEvent;
import ridev.com.br.api.inventory.menu.mysterybox.location.MysteryBoxLocationMain;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class MainMysteryBox extends SimpleInventory {
    public MainMysteryBox() {
        super("main.mysterybox.inv", FancyText.colored("Menu De Baus"), 9 * 5);
    }

    public void openInv(Player p) {
        this.openInventory(p);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        InventoryItem boxLocs = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("world_head"), "&aLocalização de Baús", new String[]{"&7Clique aqui para", "&7Ver o menu de localização", "&7das Mystery Box"})).defaultCallback(a -> new MysteryBoxLocationMain().init().openInventory(p));
        InventoryItem boxPlayerAdd = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("other_box"), "&aAdicionar Báu", new String[]{"&7Clique aqui para", "&7Adicionar um báu", "&7a conta de um player"})).defaultCallback(a -> AddBoxEvent.getPlayers().add(us));

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
