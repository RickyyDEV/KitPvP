package ridev.com.br.api.inventory.menu.leader;

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
import ridev.com.br.api.leader.LeaderManager;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class MainLeader extends SimpleInventory {
    public MainLeader() {
        super("main.leader.inv", FancyText.colored("&7Menu De Leader Boards"), 9 * 5);
    }

    public void openInv(Player p) {
        this.openInventory(p);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem setar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("green"), "&aSetar Leaderbords", new String[]{"&7Clique aqui para", "&7setar Leaderboards!"})).defaultCallback(a -> new SetLeader().init().openInventory(p));
        InventoryItem remover = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("red"), "&cRemover LeaderBoards", new String[]{"&7Clique Aqui para", "&7remover LeaderBoards"})).defaultCallback(a -> {
            if (LeaderManager.getLeaders().isEmpty()) {
                new RemoveLeaderSimply().init().openInventory(p);
            } else {
                new RemoveLeaderPagination().init().openInventory(p);
            }
        });
        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para Voltar"})).defaultCallback(a -> new MainInventory().init().openInventory(p));
        editor.setItem(21, setar);
        editor.setItem(23, remover);
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
