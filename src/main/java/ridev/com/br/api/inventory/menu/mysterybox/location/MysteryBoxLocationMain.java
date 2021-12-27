package ridev.com.br.api.inventory.menu.mysterybox.location;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.bau.BoxManager;
import ridev.com.br.api.inventory.menu.mysterybox.MainMysteryBox;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class MysteryBoxLocationMain extends SimpleInventory {
    public MysteryBoxLocationMain() {
        super("main.mysterybox.location.inv", FancyText.colored("&7Bau Location"), 9 * 5);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem boxLocs = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("green"), "&aAdicionar de Baús", new String[]{"&7Clique aqui para", "&7Adicionar um baú na sua", "&7Localização"})).defaultCallback(a -> {
            MysteryBoxLocationSetEvent.name.remove(p);
            MysteryBoxLocationSetEvent.players.remove(p);
            p.closeInventory();
            p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Agora Nos Diga o nome da MysteryBox!"));
            MysteryBoxLocationSetEvent.players.add(p);
        });
        InventoryItem boxPlayerAdd = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("red"), "&aRemover Báu", new String[]{"&7Clique aqui para", "&7Remover um báu", "&7 Especifico"})).defaultCallback(a -> {
            if (BoxManager.getBoxes().isEmpty()) {
                new MysteryBoxLocationRemoverSimply().init().openInventory(p);
            } else {
                new MysteryBoxLocationRemoverPaginated().init().openInventory(p);
            }
        });

        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para Voltar"})).defaultCallback(a -> new MainMysteryBox().init().openInventory(p));

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
