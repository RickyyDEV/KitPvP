package ridev.com.br.api.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.cooldown.CooldownAPI;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Ninja implements Kit {

    static HashMap<User, User> cacheTeleport = new HashMap<>();


    @Override
    public @NotNull List<String> description() {
        return new java.util.ArrayList<>(Arrays.asList(
                "&r",
                "&7Torne-se um verdadeiro ninja!",
                "&7Se teletransporte para trás de seus",
                "&7Adversários, para derrotalos sem nem ser",
                "&7Descoberto",
                "&r",
                " &eItens: ",
                "&71x Espada de pedra",
                "&r",
                " &eHabilidades: ",
                "&r",
                "&7Agaixando você ativa o poder de se",
                "&7Teletransportar para trás de seus adversários",
                "&r")
        );
    }

    @Override
    public @NotNull ItemStack icone() {
        return new ItemStack(Material.EMERALD);
    }

    @Override
    public int id() {
        return 5;
    }

    @Override
    public @NotNull String name() {
        return "Ninja";
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        itens.put(0, transform(Material.STONE_SWORD, "&aEspada", true, ""));
        for (int i = 1; i < 36; i++) {
            if (i == 13) {
                itens.put(13, transform(Material.BOWL, 64));
            } else if (i == 14) {
                itens.put(14, transform(Material.RED_MUSHROOM, 64));
            } else if (i == 15) {
                itens.put(15, transform(Material.BROWN_MUSHROOM, 64));
            } else {
                itens.put(i, transform(Material.MUSHROOM_SOUP));
            }
        }
        itens.put(8, transform(Material.COMPASS, "&aProcurar jogadores", false));
        return itens;
    }

    @Override
    public int price() {
        return 2000;
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
                User us = UserManager.getPlayer(e.getPlayer());
                if (us.getKit() != null && us.getKit() instanceof Ninja) {
                    if (CooldownAPI.isInCooldown(e.getPlayer().getName(), "ninja")) {
                        e.getPlayer().sendMessage(FancyText.colored("&b&lNINJA &8➸ &7Você &cdeve aguardar &6" + CooldownAPI.getTimeLeft(e.getPlayer().getName(), "ninja") + "s &cpara usar o kit novamente&7!"));
                    } else {
                        if (cacheTeleport.containsKey(us)) {
                            if (cacheTeleport.get(us) != null) {
                                if (cacheTeleport.get(us).getKit() != null) {
                                    us.getPlayer().teleport(cacheTeleport.get(us).getPlayer().getLocation().clone().subtract(0, 0, 1));
                                    e.getPlayer().sendMessage(FancyText.colored("&b&lNINJA &8➸ &7Você foi &ateletransportado para " + cacheTeleport.get(us).getUsername() + "&7!"));
                                    CooldownAPI cd = new CooldownAPI(e.getPlayer().getName(), "ninja", 5);
                                    cd.start();
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

    private static ItemStack transform(Material item, int amount) {
        ItemStack i = new ItemStack(item);
        i.setAmount(amount);
        return i;
    }

    private static ItemStack transform(Material item) {
        return new ItemStack(item);
    }

    private static ItemStack transform(Material item, String name, boolean encantada, String... lore) {
        ItemStack i = new ItemStack(item);
        ItemMeta im = i.getItemMeta();
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        im.setDisplayName(FancyText.colored(name));
        if (encantada) {
            i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        i.setItemMeta(im);
        return i;
    }
}
