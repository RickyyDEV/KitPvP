package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ridev.com.br.api.cooldown.CooldownAPI;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.apis.Mine;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Trevo implements Kit {
    @Override
    public @NonNull String name() {
        return "Trevo";
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::trevoPermission);
    }

    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::trevoDescription));
    }

    @Override
    public @NonNull ItemStack icone() {
        return new ItemStack(Material.GOLDEN_APPLE);
    }

    @Override
    public int id() {
        return 15;
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
    public int price() {
        return KitLanguage.get(KitLanguage::trevoPreco);
    }

    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.MEDIANO;
    }

    @Override
    public Listener event() {
        return new Listener() {


            @EventHandler
            public void aoBater(EntityDamageByEntityEvent e) {
                if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                    Player author = (Player) e.getDamager();
                    Player victim = (Player) e.getEntity();
                    User authorUs = UserManager.getPlayer(author);

                    if (authorUs.getKit() != null && authorUs.getKit() instanceof Trevo) {

                        if (!CooldownAPI.isInCooldown(author.getName(), "trevo")) {
                            CooldownAPI cd = new CooldownAPI(author.getName(), "trevo", 3);
                            int random = Mine.getRandomInt(0, 100);
                            if (random >= 80) {
                                if (author.getInventory().firstEmpty() != -1 || author.getInventory().containsAtLeast(new ItemStack(Material.GOLDEN_APPLE), 1)) {
                                    author.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                                } else {
                                    author.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE));
                                }
                                author.sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::trevoGift)));
                                cd.start();
                            }
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
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        im.setDisplayName(FancyText.colored(name));
        if (encantada) {
            i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        i.setItemMeta(im);
        return i;
    }
}
