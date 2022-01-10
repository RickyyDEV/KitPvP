package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.kit.KitWinEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Stomper implements Kit {
    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::stomperDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::stomperPreco);
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::stomperPermission);
    }

    @Override
    public @NonNull ItemStack icone() {
        return new ItemStack(Material.FEATHER);
    }

    @Override
    public int id() {
        return 14;
    }

    @Override
    public @NonNull String name() {
        return "Stomper";
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
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
    public @NonNull KitRarity rarity() {
        return KitRarity.SUPREMO;
    }

    @Override
    public Listener event() {
        return new Listener() {
            @EventHandler
            public void fall(EntityDamageEvent e) {
                if (e.getEntity() instanceof Player) {
                    if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        Player p = (Player) e.getEntity();
                        User author = UserManager.getPlayer(p);
                        if (author != null) {
                            if (author.getKit() != null && author.getKit() instanceof Stomper) {
                                e.setDamage(5.0);
                                for (Entity s : p.getNearbyEntities(3.5D, 1.0D, 3.5D)) {
                                    if (s instanceof Player) {
                                        Player toStomp = (Player) s;
                                        User toStompUs = UserManager.getPlayer(toStomp);

                                        if (toStompUs != null && toStompUs.getKit() != null) {
                                            if (toStomp.isSneaking()) return;
                                            toStomp.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::stomperStompado).replace("%player%", p.getName())));
                                            Bukkit.getPluginManager().callEvent(new KitWinEvent(author, toStompUs));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


        };
    }


}
