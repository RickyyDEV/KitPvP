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
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.apis.Extra;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class RankInventory extends SimpleInventory {
    private User toSee;

    public RankInventory() {
        super("rank.inv", FancyText.colored("&7Informações de rank"), 9 * 5);
    }

    public void openInv(Player p, User toSee) {
        this.toSee = toSee;
        this.openInventory(p);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        User us;
        if (this.toSee != null) {
            us = this.toSee;
        } else {
            us = UserManager.getPlayer(p);
        }

        String proxRank = us.getRank().getNextRank() != null ? us.getRank().getNextRank().getBeatifulName() : "Nenhum";
        int xpNecessario = us.getRank().getNextRank() != null ? us.getRank().getNextRank().getXp() : 0;
        String progressBar = us.getRank().getNextRank() != null ? Extra.getProgressBar(us.getXp(), us.getRank().getNextRank().getXp(), FancyText.colored("&a"), FancyText.colored("&7"), "=") : "Caminho completo!";
        InventoryItem rank = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("machine_head"), "&6Informações de " + us.getUsername() + ": ",
                        "&r",
                        "&fXP: &e" + us.getXp(),
                        "&fPróximo rank: " + proxRank,
                        "&f XP necessário: " + xpNecessario,
                        "&f Caminho: " + progressBar
                )).defaultCallback(a -> a.setCancelled(true)
        );


        editor.setItem(22, rank);

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
