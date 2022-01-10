package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import net.minecraft.server.v1_8_R3.EntityFishingHook;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fisherman implements Kit {
    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::fishermanDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::fishermanPreco);
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::fishermanPermission);
    }

    @Override
    public @NotNull ItemStack icone() {
        return new ItemStack(Material.FISHING_ROD);
    }

    @Override
    public int id() {
        return 4;
    }

    @Override
    public @NotNull String name() {
        return "Fisherman";
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack fisherman = new ItemBuilder(Material.FISHING_ROD).setName("&aVara de pescar &7(Fisherman)").addLore("&aClique com o direito!").build();
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
        itens.put(1, fisherman);
        itens.put(8, bussola);
        return itens;
    }


    @Override
    public @NotNull KitRarity rarity() {
        return KitRarity.RARO;
    }

    @Override
    public Listener event() {
        return new Listener() {
            @EventHandler
            public void fish(PlayerFishEvent e) {
                if (e.getCaught() instanceof Player) {
                    User us = UserManager.getPlayer(e.getPlayer());
                    User victim = UserManager.getPlayer((Player) e.getCaught());
                    if (us.getKit() != null && us.getKit() instanceof Fisherman && victim.getKit() != null) {
                        e.getHook().setPassenger(victim.getPlayer());
                        e.getCaught().teleport(e.getPlayer().getLocation());
                    }
                }
            }

            @EventHandler
            public void aoTacarVara(EntityDamageByEntityEvent e) {
                if (e.getEntity() instanceof Player && e.getDamager() instanceof EntityFishingHook) {
                    Projectile projectile = (Projectile) e.getDamager();
                    if (projectile.getShooter() instanceof Player) {
                        if (projectile.getShooter().equals(e.getEntity())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        };

    }

}
