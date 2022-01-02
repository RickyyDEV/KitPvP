package ridev.com.br.api.inventory.loja.kits;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.inventory.ShopMenuInv;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitLibrary;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ShopKits extends PagedInventory {

    public ShopKits() {
        super("loja.kits", FancyText.colored("&7Loja de Kits"), 9 * 6);
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(21);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", "&7Clique aqui para Voltar")).defaultCallback(a -> new ShopMenuInv().init().openInventory(p));
        editor.setItem(40, voltar);
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        LinkedList<InventoryItemSupplier> itens = new LinkedList<>();
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        for (Kit kits : KitLibrary.getKits()) {
            List<String> lore = kits.description();

            lore.add(FancyText.colored(us.getKits().contains(kits) ? "&bAdquirido!" : "&aClique Para Comprar!"));
            lore.add(FancyText.colored("&r"));
            lore.add(FancyText.colored("&fRaridade: " + kits.rarity().getBeatifulName()));
            itens.add(() -> InventoryItem.of(
                    newMenuItemItemStack(kits.icone(), FancyText.colored(us.getKits().contains(kits) ? "&a" + kits.name() : "&c" + kits.name()), lore)).defaultCallback(a -> {

                if (!us.getKits().contains(kits)) {
                    if (us.getCoins() >= kits.price()) {
                        this.updateInventory(p);
                        p.closeInventory();
                        us.addKit(kits);
                        us.removeCoins(kits.price());
                        Sound.NOTE_PLING.play(p, 10, 10);
                        p.sendMessage(FancyText.colored("&9&lKIT &8➸ &7Você Acaba de &aComprar &7o Kit &c" + kits.name()));
                    } else {
                        p.closeInventory();
                        Sound.VILLAGER_NO.play(p, 10, 10);
                        p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você não possue Coins o suficiente!"));
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
