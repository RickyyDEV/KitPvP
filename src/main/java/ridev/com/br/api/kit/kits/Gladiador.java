package ridev.com.br.api.kit.kits;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.Main;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.kit.KitWinEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.language.KitLanguage;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gladiador implements Kit {

    static HashMap<User, Location> spawn = new HashMap<>();

    static List<User> inGladiator = new ArrayList<>();

    static HashMap<User, User> inDuel = new HashMap<>();

    static List<User> inWait = new ArrayList<>();

    static HashMap<User, List<Block>> gladBlocks = new HashMap<>();

    @Override
    public @NonNull List<String> description() {
        return new ArrayList<>(KitLanguage.get(KitLanguage::gladiadorDescription));
    }

    @Override
    public int price() {
        return KitLanguage.get(KitLanguage::gladiadorPreco);
    }

    @Override
    public @NotNull ItemStack icone() {
        return new ItemStack(Material.IRON_FENCE);
    }

    @Override
    public @NonNull String permission() {
        return KitLanguage.get(KitLanguage::gladiadorPermission);
    }

    @Override
    public int id() {
        return 6;
    }

    @Override
    public @NotNull String name() {
        return "Gladiador";
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        ItemStack pote = new ItemBuilder(Material.BOWL).setName("&aSopa").setQuantity(64).build();
        ItemStack coguVermelho = new ItemBuilder(Material.RED_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack coguMarrom = new ItemBuilder(Material.BROWN_MUSHROOM).setName("&aSopa").setQuantity(64).build();
        ItemStack sopa = new ItemBuilder(Material.MUSHROOM_SOUP).setName("&aSopa").setQuantity(1).build();
        ItemStack espada = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setName("&aEspada").build();
        ItemStack glad = new ItemBuilder(Material.IRON_FENCE).setName("&aGrade &7(Gladiador)").addLore("&aClique com o direito!").build();
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
        itens.put(1, glad);
        itens.put(8, bussola);
        return itens;
    }

    @Override
    public @NotNull KitRarity rarity() {
        return KitRarity.RARO;
    }

    @Override
    public @NotNull Listener event() {
        return new Listener() {
            @EventHandler
            public void aoClicar(PlayerInteractAtEntityEvent e) {
                if (e.getRightClicked() instanceof Player) {
                    Player p = e.getPlayer();
                    User author = UserManager.getPlayer(e.getPlayer());
                    User clicked = UserManager.getPlayer((Player) e.getRightClicked());
                    if (author.getWarp().equals(WarpType.ARENA)) {
                        if (author.getPlayer().getItemInHand().getType().equals(Material.IRON_FENCE)) {
                            if (author.getKit() instanceof Gladiador) {
                                if (inGladiator.contains(author)) {
                                    author.getPlayer().sendMessage(FancyText.colored(KitLanguage.get(KitLanguage::gladiadorInGlad)));
                                } else {
                                    spawn.put(author, author.getPlayer().getLocation());
                                    spawn.put(clicked, clicked.getPlayer().getLocation());
                                    gladBlocks.clear();

                                    final Location highestbBlock = author.getPlayer().getWorld().getHighestBlockAt(author.getPlayer().getLocation()).getLocation();

                                    final Location loc = new Location(author.getPlayer().getWorld(), highestbBlock.getX(), highestbBlock.getY() + 100, highestbBlock.getZ());
                                    final Location loc2 = new Location(author.getPlayer().getWorld(), highestbBlock.getBlockX() + 8, highestbBlock.getBlockY() + 100, highestbBlock.getBlockZ() + 8);
                                    final Location loc3 = new Location(author.getPlayer().getWorld(), highestbBlock.getBlockX() - 8, highestbBlock.getBlockY() + 100, highestbBlock.getBlockZ() - 8);
                                    final List<Block> cuboid = new ArrayList<>();
                                    for (int bX = -10; bX <= 10; ++bX) {
                                        for (int bZ = -10; bZ <= 10; ++bZ) {
                                            for (int bY = -1; bY <= 10; ++bY) {
                                                if (bY == 10) {
                                                    cuboid.add(loc.clone().add(bX, bY, bZ).getBlock());
                                                } else if (bY == -1) {
                                                    cuboid.add(loc.clone().add(bX, bY, bZ).getBlock());
                                                } else if (bX == -10 || bZ == -10 || bX == 10 || bZ == 10) {
                                                    cuboid.add(loc.clone().add(bX, bY, bZ).getBlock());
                                                }
                                            }
                                        }
                                    }

                                    gladBlocks.put(author, cuboid);
                                    gladBlocks.put(clicked, cuboid);

                                    for (final Block loc4 : cuboid) {
                                        loc4.setType(Material.GLASS);
                                    }

                                    loc2.setYaw(135.0F);
                                    author.getPlayer().teleport(loc2.clone());
                                    loc3.setYaw(-45.0F);
                                    clicked.getPlayer().teleport(loc3);
                                    author.getPlayer().getLocation().setYaw(author.getPlayer().getLocation().getYaw() + 180);
                                    clicked.getPlayer().getLocation().setYaw(clicked.getPlayer().getLocation().getYaw() + 180);
                                    inWait.add(author);
                                    inWait.add(clicked);
                                    new BukkitRunnable() {
                                        public void run() {
                                            MineReflect.sendTitle(author.getPlayer(), FancyText.colored("&e3..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                                            MineReflect.sendTitle(clicked.getPlayer(), FancyText.colored("&e3..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                                        }
                                    }.runTaskLater(Main.getInstance(), 20);

                                    new BukkitRunnable() {
                                        public void run() {
                                            MineReflect.sendTitle(author.getPlayer(), FancyText.colored("&e2..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                                            MineReflect.sendTitle(clicked.getPlayer(), FancyText.colored("&e2..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                                        }
                                    }.runTaskLater(Main.getInstance(), 40);

                                    new BukkitRunnable() {
                                        public void run() {
                                            MineReflect.sendTitle(author.getPlayer(), FancyText.colored("&e1..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                                            MineReflect.sendTitle(clicked.getPlayer(), FancyText.colored("&e1..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                                        }
                                    }.runTaskLater(Main.getInstance(), 60);

                                    new BukkitRunnable() {
                                        public void run() {
                                            Sound.FIRE_IGNITE.play(author.getPlayer(), 10, 10);
                                            Sound.FIRE_IGNITE.play(clicked.getPlayer(), 10, 10);
                                            MineReflect.sendTitle(author.getPlayer(), FancyText.colored("&e0..."), FancyText.colored("&cLutem!"), 10, 10, 10);
                                            MineReflect.sendTitle(clicked.getPlayer(), FancyText.colored("&e0..."), FancyText.colored("&cLutem!"), 10, 10, 10);
                                            inWait.remove(author);
                                            inWait.remove(clicked);
                                            inDuel.put(author, clicked);
                                            inDuel.put(clicked, author);
                                        }
                                    }.runTaskLater(Main.getInstance(), 70);
                                }
                            }
                        }
                    }
                }
            }

            @EventHandler
            public void damage(EntityDamageByEntityEvent ev) {
                if ((ev.getDamager() instanceof Player) && (ev.getEntity() instanceof Player)) {
                    Player pqbateu = (Player) ev.getDamager();
                    Player pqsofreu = (Player) ev.getEntity();
                    User author = UserManager.getPlayer(pqbateu);
                    User victim = UserManager.getPlayer(pqsofreu);
                    if (author.getKit() != null && victim.getKit() != null && inDuel.containsKey(author) && inDuel.containsKey(victim)) {
                        if ((pqsofreu.getHealth() - ev.getFinalDamage()) < 0.5) {
                            author.getPlayer().teleport(spawn.get(author));
                            ev.setCancelled(true);
                            for (Map.Entry<User, List<Block>> bl : gladBlocks.entrySet()) {
                                if (bl.getKey().equals(author)) {
                                    for (Block bls : bl.getValue()) {
                                        bls.setType(Material.AIR);
                                    }
                                }
                            }
                            gladBlocks.remove(author);
                            gladBlocks.remove(victim);
                            Bukkit.getPluginManager().callEvent(new KitWinEvent(author, victim));
                        }
                    }
                }
            }


            @EventHandler
            public void seMexer(PlayerMoveEvent e) {
                User us = UserManager.getPlayer(e.getPlayer());
                if (us != null) {
                    if (us.getKit() != null && inWait != null && inWait.contains(us)) {
                        e.setTo(us.getPlayer().getLocation());
                    }
                }
            }
        };


    }

}
