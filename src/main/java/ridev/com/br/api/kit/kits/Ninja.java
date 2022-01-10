package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.cooldown.CooldownAPI;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ninja implements Kit {

    static HashMap<User, User> cacheTeleport = new HashMap<>();


    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::ninjaDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::ninjaPreco);
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::ninjaPermission);
    }

    @Override
    public @NotNull ItemStack icone() {
        return new ItemStack(Material.EMERALD);
    }

    @Override
    public int id() {
        return 11;
    }

    @Override
    public @NotNull String name() {
        return "Ninja";
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
        return KitRarity.MEDIANO;
    }

    @Override
    public @NotNull Listener event() {
        return new Listener() {
            @EventHandler
            public void aoAgaixar(PlayerToggleSneakEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(e.getPlayer());
                if (us.getKit() != null && us.getKit() instanceof Ninja) {
                    if (e.getPlayer().isSneaking()) {
                        if (CooldownAPI.isInCooldown(e.getPlayer().getName(), "ninja")) {
                            e.getPlayer().sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::ninjaWaitCooldown).replace("%time%", String.valueOf(CooldownAPI.getTimeLeft(p.getName(), "ninja")))));
                        } else {
                            if (cacheTeleport.containsKey(us)) {
                                if (cacheTeleport.get(us) != null) {
                                    if (cacheTeleport.get(us).getKit() != null) {
                                        us.getPlayer().teleport(cacheTeleport.get(us).getPlayer().getLocation().clone().subtract(0, 0, 1));
                                        e.getPlayer().sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::ninjaTeleport).replace("%player%", cacheTeleport.get(us).getUsername())));
                                        CooldownAPI cd = new CooldownAPI(e.getPlayer().getName(), "ninja", 5);
                                        cd.start();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @EventHandler
            public void aoBater(EntityDamageByEntityEvent e) {
                if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
                    User author = UserManager.getPlayer((Player) e.getDamager());
                    User victim = UserManager.getPlayer((Player) e.getEntity());
                    if (author.getKit() instanceof Ninja) {
                        cacheTeleport.put(author, victim);
                    }
                }
            }

        };
    }

}
