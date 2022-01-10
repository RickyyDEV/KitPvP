package ridev.com.br.api.inventory.menu.leader;

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
import ridev.com.br.api.leader.Leader;
import ridev.com.br.api.leader.LeaderManager;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class RemoveLeaderPagination extends PagedInventory {

    public RemoveLeaderPagination() {
        super(
                "remover.leader.inv.paginated",
                FancyText.colored("&7Removedor de leaderboards"),
                9 * 6
        );
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(21);
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {
        List<InventoryItemSupplier> itemSuppliers = new ArrayList<>();
        Player p = viewer.getPlayer();
        for (Map.Entry<String, Leader> data : LeaderManager.getLeaders().entrySet()) {
            Leader lead = data.getValue();
            itemSuppliers.add(() -> InventoryItem.of(
                    newMenuItem(Material.NETHER_STAR, "&c" + lead.getName(), "", "&7Tipo: &e" + lead.getType().getName(), "", "&7Clique aqui para", "&7Remover este leaderBoard!")).defaultCallback(a -> {
                p.closeInventory();
                lead.removeInConfig();
                lead.removeLeader();
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&6&lLEADER &8➸ &7LeaderBoard " + lead.getName() + " &7foi removida com sucesso!"));
            }));
        }

        return itemSuppliers;
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", "&7Clique aqui para voltar")).defaultCallback(a -> new MainLeader().init().openInventory(p));
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