package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PvP implements Kit {
    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::pvpDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::pvpPreco);
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::pvpPermission);
    }

    @Override
    public @NotNull ItemStack icone() {
        return new ItemStack(Material.IRON_SWORD);
    }

    @Override
    public int id() {
        return 13;
    }

    @Override
    public @NotNull String name() {
        return "PvP";
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack bussola = new ItemBuilder(Material.COMPASS).setName("&aProcurar jogadores").addLore("&aClique com o direito!").build();
        for (int i = 1; i < 36; i++) {
            if (i == 13) {
                itens.put(13, pote);
            } else if (i == 14) {
                itens.put(14, coguVermelho);
            } else if (i == 15) {
                itens.put(15, coguMarrom);
            } else {
                itens.put(i, sopa);
            }
        }
        itens.put(0, espada);
        itens.put(8, bussola);
        return itens;
    }


    @Override
    public @NotNull KitRarity rarity() {
        return KitRarity.COMUM;
    }

    @Override
    public @NotNull Listener event() {
        return new Listener() {
        };
    }

}
