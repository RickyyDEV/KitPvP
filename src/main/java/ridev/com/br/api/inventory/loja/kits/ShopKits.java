package ridev.com.br.api.inventory.loja.kits;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.inventory.ShopMenuInv;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitLibrary;
import ridev.com.br.api.kit.kits.Naruto;
import ridev.com.br.api.permission.Permission;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.LinkedList;
import java.util.List;

public class ShopKits extends PagedInventory {

    public ShopKits() {
        super("loja.kits", FancyText.colored("&7Loja de kits"), 9 * 6);
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(28);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        ItemStack items = new ItemBuilder(CacheSystem.getItem("back")).setName("&cVoltar").addLore("&7Clique aqui para Voltar").build();
        InventoryItem voltar = InventoryItem.of(items).defaultCallback(a -> new ShopMenuInv().init().openInventory(p));
        editor.setItem(49, voltar);
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        LinkedList<InventoryItemSupplier> itens = new LinkedList<>();
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        for (Kit kits : KitLibrary.getKits()) {
            List<String> lore = kits.description();

            if (kits instanceof Naruto && !p.getName().equalsIgnoreCase("yRicardinBaum")) {
                lore.add("&cEm desenvolvimento!");

            } else {
                if (us.getKits().contains(kits)) {
                    lore.add("&bAdquirido!");
                } else {
                    lore.add("&aClique para comprar!");
                    lore.add("&ePreço: &a" + kits.price());
                }
                lore.add(FancyText.colored("&r"));
                lore.add(FancyText.colored("&fRaridade: " + kits.rarity().getBeatifulName()));
            }
            ItemStack item = new ItemBuilder(kits.icone()).setName(us.getKits().contains(kits) ? "&a" + kits.name() : "&c" + kits.name()).addLore(lore).build();

            itens.add(() -> InventoryItem.of(item).defaultCallback(a -> {
                if (kits instanceof Naruto && !p.getName().equalsIgnoreCase("yRicardinBaum")) {
                    p.closeInventory();
                    p.sendMessage(FancyText.colored("&cKit em desenvolvimento..."));
                } else {
                    if (!us.getKits().contains(kits)) {
                        if (us.getCoins() >= kits.price()) {
                            this.updateInventory(p);
                            p.closeInventory();
                            us.addKit(kits);
                            us.removeCoins(kits.price());
                            if (KitLanguage.get(KitLanguage::saveType).equalsIgnoreCase("permission")) {
                                Permission.getInstance().addPermission(p, kits.permission());
                            }
                            Sound.NOTE_PLING.play(p, 10, 10);
                            p.sendMessage(FancyText.colored("&9&lKIT &8➸ &7Você acaba de &acomprar &7o kit &c" + kits.name()));
                        } else {
                            p.closeInventory();
                            Sound.VILLAGER_NO.play(p, 10, 10);
                            p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você &cnão possue coins o suficiente&7!"));
                        }
                    }
                }
            }));
        }
        return itens;
    }
}
