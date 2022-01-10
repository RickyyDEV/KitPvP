package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.other.CacheSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Jumper implements Kit {
    @Override
    public @NonNull String name() {
        return "Jumper";
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::jumperPermission);
    }

    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::jumperDescription));
    }

    @Override
    public @NonNull ItemStack icone() {
        return CacheSystem.getItem("frog_head");
    }

    @Override
    public int id() {
        return 7;
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack potion = new ItemBuilder(Material.POTION).setName("&aPoção &7(Jumper)").addLore("&aClique com o direito!").setDamage(8203).build();
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
        itens.put(1, potion);
        itens.put(8, bussola);
        return itens;
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::jumperPreco);
    }

    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.MEDIANO;
    }

    @Override
    public Listener event() {
        return new Listener() {

            @EventHandler
            public void use(PlayerItemConsumeEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() != null && us.getKit() instanceof Jumper) {
                    if (e.getItem().getType().equals(Material.POTION)) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 90, 2));
                        p.setItemInHand(null);
                    }
                }
            }
        };
    }
}
