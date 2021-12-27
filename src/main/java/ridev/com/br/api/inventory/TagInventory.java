package ridev.com.br.api.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.border.Border;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.cargos.Group;
import ridev.com.br.api.cargos.GroupManager;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.scoreboard.ScoreboardManager;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TagInventory extends PagedInventory {
    public TagInventory() {
        super("tag.inventory", FancyText.colored("&7Seletor de tags"), 9 * 5);

    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(8);
        configuration.border(Border.of(1, 1, 1, 1));
        configuration.emptyPageSlot(1);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        User us = UserManager.getPlayer(viewer.getPlayer());
        if (GroupManager.getPlayerHavesPermission(us).isEmpty()) {
            InventoryItem semArena = InventoryItem.of(
                    newMenuItemItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "&cSem tags", "&7Você não possue nenhuma outra tag", "&7Para poder alterar!")).defaultCallback(a -> a.setCancelled(true));
            editor.setItem(22, semArena);
        }
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

    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        LinkedList<InventoryItemSupplier> itens = new LinkedList<>();
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        if (!GroupManager.getPlayerHavesPermission(us).isEmpty()) {
            for (Group gp : GroupManager.getPlayerHavesPermission(us)) {
                ItemStack ink = new ItemStack(Material.INK_SACK, 1, (short) 10);
                itens.add(() -> InventoryItem.of(
                        newMenuItemItemStack(ink, gp.getNameWithColor(), "&r", "&7Clique aqui para selecionar esta tag!", "&r")).defaultCallback(a -> {
                    p.closeInventory();
                    for (Player p2 : Bukkit.getOnlinePlayers()) {
                        ScoreboardManager.setScore(p2);
                        User user = UserManager.getPlayer(p2);
                        if (user.equals(us)) {
                            MineReflect.sendNameTag(p2, FancyText.colored(gp.getPrefix() + ""), FancyText.colored(" &7(" + us.getRank().getSymbol() + "&7)"), gp.getPriority());
                        } else {
                            MineReflect.sendNameTag(p2, FancyText.colored(user.getRole().getPrefix() + ""), FancyText.colored(" &7(" + user.getRank().getSymbol() + "&7)"), user.getRole().getPriority());
                        }
                    }
                    us.setRole(gp);
                    p.sendMessage(FancyText.colored("&e&lTAG &8➸&7Você alterou sua tag para " + gp.getNameWithColor()));
                    ScoreboardManager.reloadScoreboard(p);
                }));
            }
        }
        return itens;

    }
}
