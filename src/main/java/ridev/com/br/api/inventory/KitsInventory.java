package ridev.com.br.api.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.arena.Arena;
import ridev.com.br.api.arena.ArenaLibrary;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitLibrary;
import ridev.com.br.api.kit.kits.Naruto;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KitsInventory extends PagedInventory {


    public KitsInventory() {
        super("kits.inventory", FancyText.colored("&7Seus kits"), 9 * 6);
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(28);
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        LinkedList<InventoryItemSupplier> itens = new LinkedList<>();
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        for (Kit kits : KitLibrary.getKits()) {

            ItemStack icon = kits.icone();
            List<String> lore = kits.description();
            if (kits instanceof Naruto && !p.getName().equalsIgnoreCase("yRicardinBaum")) {
                icon = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
                lore.add("&cEm desenvolvimento!");
            } else {
                if (!us.getKits().contains(kits)) {
                    icon = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
                    lore.add(FancyText.colored("&cNão adquirido."));
                } else {
                    lore.add(FancyText.colored("&bClique para jogar!"));
                }
                lore.add(FancyText.colored("&r"));
                lore.add(FancyText.colored("&fRaridade: " + kits.rarity().getBeatifulName()));
            }
            ItemStack finalIcon = icon;
            itens.add(() -> InventoryItem.of(
                    newMenuItemItemStack(finalIcon, "&a" + kits.name(), lore)).defaultCallback(a -> {
                if (kits instanceof Naruto && !p.getName().equalsIgnoreCase("yRicardinBaum")) {
                    p.closeInventory();
                    p.sendMessage(FancyText.colored("&cKit em desenvolvimento..."));
                } else {
                    if (us.getKits().contains(kits)) {
                        if (ArenaLibrary.arenasIsSetted().isEmpty()) {
                            p.sendMessage(FancyText.colored("&cNenhum spawn da arena setada ainda!"));
                        } else {
                            Arena.sendPlayer(us, kits);
                        }
                    }
                }
            }));
        }
        return itens;
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

    public static ItemStack newMenuItemItemStack(ItemStack ironFence, String s, List<String> strings) {
        final ItemMeta kitem = ironFence.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(FancyText.colored(strings));
        ironFence.setItemMeta(kitem);

        return ironFence;
    }
}
