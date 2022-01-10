package ridev.com.br.api.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.other.PlayerAPI;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.HashMap;

public class ProfileInventory extends SimpleInventory {

    public static HashMap<User, User> users = new HashMap<User, User>();

    public ProfileInventory() {
        super("profile.inv", FancyText.colored("&7Perfil"), 9 * 3);
    }

    public void openInv(Player p, User toSee) {
        users.put(UserManager.getPlayer(p), toSee);
        inv.openInventory(p);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        User us;
        Player p = viewer.getPlayer();
        if (users.get(UserManager.getPlayer(p)) != null) {
            us = users.get(UserManager.getPlayer(p));
        } else {
            us = UserManager.getPlayer(p.getName());
        }
        ItemStack skull = PlayerAPI.getHead(us.getUsername());
        InventoryItem head = InventoryItem.of(
                        newMenuItemItemStack(skull,
                                "&7Informações sobre " + us.getUsername(),
                                "&r",
                                "&fNome: &e" + us.getUsername(),
                                "&fGrupo: " + us.getRole().getNameWithColor(),
                                "&fCoins: &6" + us.getCoins(),
                                "&r",
                                "&fXP: &a" + us.getXp(),
                                "&fAbates: &c" + us.getKills(),
                                "&fMortes: &c" + us.getMortes(),
                                "&r",
                                "&fRank:&7 " + us.getRank().getBeatifulName(),
                                "&fSímbolo: &7(&f" + us.getRank().getSymbol() + "&7)",
                                "&r")
                )
                .defaultCallback(a -> a.setCancelled(true));
        editor.setItem(13, head);
    }

    public static ItemStack newMenuItemItemStack(ItemStack ironFence, String s, String... strings) {
        final ItemMeta kitem = ironFence.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        ironFence.setItemMeta(kitem);

        return ironFence;
    }

    static ProfileInventory inv = new ProfileInventory().init();
}
