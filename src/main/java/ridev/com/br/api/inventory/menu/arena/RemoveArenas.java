package ridev.com.br.api.inventory.menu.arena;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.arena.ArenaLibrary;
import ridev.com.br.api.arena.ArenaType;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class RemoveArenas extends SimpleInventory {
    public RemoveArenas() {
        super("remover.arenas.inv", FancyText.colored("&7Removedor de arenas"), 9 * 6);
    }


    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();

        if (ArenaLibrary.arenasIsSetted().isEmpty()) {
            ItemStack noArena = new ItemBuilder(Material.STAINED_GLASS_PANE).addDyeColor(DyeColor.RED).setName("&cSem Arenas").addLore("&7Nenhuma arena foi setada").addLore("&7Para adicionar alguma, acesse o menu de").addLore("&7adicionar arenas").build();

            InventoryItem semArena = InventoryItem.of(
                    noArena).defaultCallback(a -> a.setCancelled(true));
            editor.setItem(22, semArena);
        } else {
            int slot = 0;
            for (ArenaType type : ArenaLibrary.arenasIsSetted()) {
                InventoryItem setarArena = InventoryItem.of(
                        newMenuItemItemStack(CacheSystem.getItem("arena_icon"), "&c" + type.getBeatifulName(), new String[]{"&7Clique aqui para", "&7remover esta arena!"})).defaultCallback(a -> {
                    Sound.NOTE_PLING.play(p, 10, 10);
                    p.closeInventory();
                    ArenaLibrary.getArena(type).removeInConfig();
                    ArenaLibrary.getArena(type).remove();
                });
                if (slot > 6 && slot < 9) slot = 9;
                editor.setItem(10 + slot, setarArena);
                slot++;
            }
        }
        InventoryItem voltar = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("back"), "&cVoltar", new String[]{"&7Clique aqui para Voltar"})).defaultCallback(a -> new MainArena().init().openInventory(p));
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
