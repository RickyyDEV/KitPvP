package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ridev.com.br.api.cooldown.CooldownAPI;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Troller implements Kit {
    @Override
    public @NonNull List<String> description() {
        return new java.util.ArrayList<>(Arrays.asList(
                "&r",
                "&7Quer ter a vantagem de trolar todos na partida?",
                "&7Esse kit é pra você!",
                "&7Troque de lugar com seus oponentes!",
                "&r",
                " &eItens: ",
                "&71x Espada de Pedra",
                "&71x Bola de Neve (Troller)",
                "&r",
                " &eHabilidades: ",
                "&r",
                "&7Troque de lugar com seu oponente acertando-o",
                "&7com a bola de neve!",
                "&r"
        ));
    }

    @Override
    public @NonNull ItemStack icone() {
        return CacheSystem.getItem("troll_head");
    }

    @Override
    public int id() {
        return 6;
    }

    @Override
    public @NonNull String name() {
        return "Troller";
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        itens.put(0, transform(Material.STONE_SWORD, "&aEspada", true, ""));
        itens.put(1, transform(Material.SNOW_BALL, "&aTroller", false, "&aClique com o direito!"));
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
        return 3000;
    }

    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.COMUM;
    }

    @Override
    public Listener event() {
        return new Listener() {

            @EventHandler
            public void onSendBall(EntityDamageByEntityEvent e) {
                if (e.getDamager() instanceof Snowball) {
                    Snowball bola = (Snowball) e.getDamager();
                    Player author = (Player) bola.getShooter();
                    User authorUs = UserManager.getPlayer(author);
                    if (authorUs.getKit() != null && authorUs.getKit() instanceof Troller) {
                        if (!CooldownAPI.isInCooldown(author.getName(), "troller")) {
                            Player victim = (Player) e.getEntity();
                            if (victim != null) {
                                User victimUs = UserManager.getPlayer(victim);
                                if (victimUs.getKit() != null) {
                                    Location victimLocation = victim.getLocation();
                                    Location authorLocation = author.getLocation();
                                    victim.teleport(authorLocation);
                                    author.teleport(victimLocation);

                                    author.sendMessage(FancyText.colored("&b&lTROLLER &8➸ &7Você trocou de lugar com " + victim.getName()));
                                }
                            }
                        } else {
                            author.sendMessage(FancyText.colored("&b&lTROLLER &8➸ &7Você &cdeve esperar " + CooldownAPI.getTimeLeft(author.getName(), "troller") + "s para utilizar este kit novamente&7!"));
                        }
                    }
                }
            }

            @EventHandler
            public void interact(PlayerInteractEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() instanceof Troller) {
                    if (p.getItemInHand().getType().equals(Material.SNOW_BALL)) {
                        p.getInventory().setItem(1, transform(Material.SNOW_BALL, "&aTroller", false, "&aClique com o direito!"));
                        if (CooldownAPI.isInCooldown(p.getName(), "troller")) {
                            p.sendMessage(FancyText.colored("&b&lTROLLER &8➸ &7Você &cdeve esperar " + CooldownAPI.getTimeLeft(p.getName(), "troller") + "s para utilizar este kit novamente&7!"));
                        } else {
                            CooldownAPI cd = new CooldownAPI(p.getName(), "troller", 4);
                            cd.start();
                        }
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
        i.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        if (encantada) im.setDisplayName(FancyText.colored(name));
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        im.setDisplayName(FancyText.colored(name));
        if (encantada) {
            i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        i.setItemMeta(im);
        return i;
    }
}
