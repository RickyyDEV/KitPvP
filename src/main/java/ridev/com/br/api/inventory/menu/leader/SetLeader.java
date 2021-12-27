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
import ridev.com.br.api.leader.LeaderType;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class SetLeader extends SimpleInventory {
    public SetLeader() {
        super("set.leader.inv", FancyText.colored("&7Leader Board Setter"), 9 * 6);
    }


    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {

        Player p = viewer.getPlayer();
        InventoryItem setarLeaderKill = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("kill_leader"), "&aKills LeaderBoard", new String[]{"&7Clique Aqui para", "&7Adicionar uma LeaderBoard", "&7De Kills!"})).defaultCallback(a -> {
            p.closeInventory();
            p.sendMessage(FancyText.colored("&6&lLEADER &8➸ &7Agora me diga o nome da LeaderBoard!"));
            LeaderEvent.players.remove(p);
            LeaderEvent.players.put(p, LeaderType.KILLS);
        });
        InventoryItem setarLeaderXp = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("xp_leader"), "&aXP LeaderBoard", new String[]{"&7Clique Aqui para", "&7Adicionar uma LeaderBoard", "&7De XP!"})).defaultCallback(a -> {
            p.sendMessage(FancyText.colored("&6&lLEADER &8➸ &7Agora me diga o nome da LeaderBoard!"));
            p.closeInventory();
            LeaderEvent.players.remove(p);
            LeaderEvent.players.put(p, LeaderType.XP);
        });


        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para Voltar"})).defaultCallback(a -> new MainLeader().init().openInventory(p));

        editor.setItem(21, setarLeaderKill);
        editor.setItem(23, setarLeaderXp);
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
