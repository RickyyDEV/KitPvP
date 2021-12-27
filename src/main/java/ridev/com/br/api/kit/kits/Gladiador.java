package ridev.com.br.api.kit.kits;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitLibrary;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.kit.KitWinEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.*;

public class Gladiador implements Kit {

    static HashMap<User, Location> spawn = new HashMap<>();

    static List<User> inGladiator = new ArrayList<>();

    static HashMap<User, User> inDuel = new HashMap<>();

    static List<User> inWait = new ArrayList<>();

    static HashMap<User, List<Block>> gladBlocks = new HashMap<>();

    @Override
    public List<String> description() {


        return new java.util.ArrayList<>(Arrays.asList(
                "&r",
                "&7Sinta-se um verdadeiro gladiador!",
                "&7Batalhe com adversarios isoladamente!",
                "&7Para que ele não tenha para onde correr!",
                "&r",
                " &eItens: ",
                "&71x Espada de pedra",
                "&71x Prisão de ferro(Gladidor)",
                "&r",
                " &eHabilidades: ",
                "&r",
                "&7Ao clicar com o botão direito no adversário,",
                "&7você puxa ele para uma arena de vidro!",
                "&r")
        );
    }

    @Override
    public ItemStack icone() {
        return transform(Material.IRON_FENCE);
    }

    @Override
    public int id() {
        return 3;
    }

    @Override
    public String name() {
        return "Gladiador";
    }

    @Override
    public HashMap<Integer, ItemStack> itens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        itens.put(0, transform(Material.STONE_SWORD, "&aEspada", true, ""));
        itens.put(1, transform(Material.IRON_FENCE, "&aGladiador (&7Direito)", false, "&7Clique aqui para", "&7Enviar um player para", "&7a Jaula!"));
        for (int i = 2; i < 36; i++) {
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
        return itens;
    }

    @Override
    public int price() {
        return 5000;
    }

    @Override
    public KitRarity rarity() {
        return KitRarity.RARO;
    }

    @Override
    public Listener event() {
        return new Listener() {
            @EventHandler
            public void aoClicar(PlayerInteractAtEntityEvent e) {
                if (e.getRightClicked() instanceof Player) {
                    Player p = e.getPlayer();
                    User author = UserManager.getPlayer(e.getPlayer());
                    User clicked = UserManager.getPlayer((Player) e.getRightClicked());
                    if (author.getWarp().equals(WarpType.ARENA)) {
                        if (author.getPlayer().getItemInHand().getType().equals(Material.IRON_FENCE)) {
                            if (author.getKit().equals(KitLibrary.getKit("gladiador"))) {
                                if (inGladiator.contains(author)) {
                                    author.getPlayer().sendMessage(FancyText.colored("&c&lGLADIADOR &8➸ &7Você &cja está em um duelo de gladiadores&7!"));
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


                                    author.getPlayer().teleport(loc2);
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
                        if ((pqsofreu.getHealth() - ev.getFinalDamage()) <= 0.5) {
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
                if (us.getKit() != null && inWait != null && inWait.contains(us)) {
                    e.setTo(us.getPlayer().getLocation());
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
