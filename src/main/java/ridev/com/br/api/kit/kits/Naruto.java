package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.cooldown.CooldownAPI;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Naruto implements Kit {

    public static HashMap<User, Wolf> playersWolfs = new HashMap<>();


    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::narutoDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::narutoPreco);
    }

    @Override
    public @NonNull ItemStack icone() {
        return CacheSystem.getItem("naruto_head");
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::narutoPermission);
    }

    @Override
    public int id() {
        return 10;
    }

    @Override
    public @NonNull String name() {
        return "Naruto";
    }

    @Override
    public @NonNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack wolf = new ItemBuilder(Material.MONSTER_EGG).setName("&aRaposa &7(Direito)").addLore("&7Clique aqui para").addLore("&7spawnar a raposa de").addLore("&79 caudas!").setDamage(95).build();
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
        itens.put(1, wolf);
        itens.put(8, bussola);
        return itens;
    }


    @Override
    public @NonNull KitRarity rarity() {
        return KitRarity.RARO;
    }

    @Override
    public Listener event() {
        return new Listener() {
            @EventHandler
            public void aoSpawnar(PlayerInteractEvent e) {
                Player author = e.getPlayer();
                User authorUs = UserManager.getPlayer(author);
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    if (authorUs != null && authorUs.getKit() instanceof Naruto) {
                        if (author.getItemInHand().getType().equals(Material.MONSTER_EGG)) {
                            if (CooldownAPI.isInCooldown(author.getName(), "naruto")) {
                                author.sendMessage(FancyText.colored("&b&lNARUTO &8➸ &7Você tem que &cesperar " + CooldownAPI.getTimeLeft(author.getName(), "naruto") + "s para soltar a fera!"));
                            } else {
                                CooldownAPI cd = new CooldownAPI(author.getName(), "naruto", 20);
                                Wolf wolf = (Wolf) author.getWorld().spawnEntity(author.getLocation(), EntityType.WOLF);

                                // Just to make sure it's a normal wolf.
//                                wolf.setAdult();
                                wolf.setTarget(author.getPlayer());
                                wolf.setAngry(true);
//                                wolf.setTamed(false);
//                                wolf.setOwner(author);
//                                wolf.setSitting(true);

                                // We don't want extra wolves.
//                                wolf.setBreed(false);

                                // Clarify the owner.
                                wolf.setCustomName(FancyText.colored("&6Raposa de &c9 caudas &7de &e" + author.getName()));
                                wolf.setCustomNameVisible(true);

                                // Let's have a little bit of variation

                                // Misc.
                                wolf.setHealth(wolf.getMaxHealth());
                                wolf.setCanPickupItems(false);
//                                Wolf dog = (Wolf) author.getWorld().spawnCreature(e.getClickedBlock().getLocation().clone().add(0, 1.5, 0), EntityType.WOLF);
//                                dog.setOwner(author);
////                                dog.setTamed(true);
////                                dog.setCollarColor(DyeColor.RED);
//                                dog.setCustomName(FancyText.colored("&6Raposa de &c9 caudas &7de &e" + author.getName()));
//                                dog.setCustomNameVisible(true);
//                                dog.setCanPickupItems(false);
//                                dog.setAngry(false);
//                                dog.setTarget(author);
//                                if (CombatLogAPI.playerIsInCombat(author)) {
//                                    dog.setAngry(true);
//                                    dog.setTarget(CombatLogAPI.getAdversary(author));
//                                }

//                                playersWolfs.put(authorUs, wolf);

                                author.sendMessage(FancyText.colored("&b&lNARUTO &8➸ &7A raposa de 9 caudas &afoi spawnada&7!"));
                                cd.start();
                                new BukkitRunnable() {
                                    public void run() {
                                        playersWolfs.remove(authorUs);
                                        wolf.remove();
                                        author.sendMessage(FancyText.colored("&b&lNARUTO &8➸ &7A sua raposa de 9 caudas &cfoi removida&7!"));
                                    }
                                }.runTaskLater(Main.getInstance(), 20 * 20);

                            }
                        }
                    }
                }
            }

            @EventHandler
            public void drop(PlayerDropItemEvent e) {
                Player p = e.getPlayer();
                User us = UserManager.getPlayer(p);

                if (us.getKit() != null && us.getKit() instanceof Naruto) {
                    if (e.getItemDrop().getItemStack().getType().equals(Material.MONSTER_EGG)) e.setCancelled(true);
                }
            }

            @EventHandler
            public void atacar(EntityDamageByEntityEvent e) {
                if (e.getDamager() instanceof Player && e.getEntity() instanceof Wolf) {
                    Player author = (Player) e.getDamager();
                    User authorus = UserManager.getPlayer(author);

                    if (authorus.getKit() != null && authorus.getKit() instanceof Naruto) {
                        if (playersWolfs.containsKey(authorus) && playersWolfs.get(authorus).equals(e.getEntity()))
                            e.setCancelled(true);
                    }
                }
            }
        };
    }


}
