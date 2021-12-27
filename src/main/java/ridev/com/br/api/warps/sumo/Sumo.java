package ridev.com.br.api.warps.sumo;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.Main;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.OnClickInPlayer;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;
import ridev.com.br.utils.text.JSONMessage;
import ridev.com.br.utils.title.TitleSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Sumo implements Listener {
    @Getter
    static final HashMap<User, User> duelo = new HashMap<>();
    @Getter
    static final List<User> inRow = new ArrayList<>();
    @Getter
    static final List<User> inWait = new ArrayList<>();
    @Getter
    static final HashMap<User, User> inDuel = new HashMap<>();


    @EventHandler
    public void interact(@NotNull PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            User player = UserManager.getPlayer(e.getPlayer());
            User clicked = UserManager.getPlayer((Player) e.getRightClicked());
            if (player.getWarp().equals(WarpType.SUMO) && clicked.getWarp().equals(WarpType.SUMO)) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD)) {
                        Bukkit.getPluginManager().callEvent(new OnClickInPlayer(player, clicked));
                    }
                }
            }
        }
    }


    @EventHandler
    public void partidaRapida(PlayerInteractEvent e) {
        User us = UserManager.getPlayer(e.getPlayer());
        if (us.getWarp().equals(WarpType.SUMO)) {
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getType().equals(Material.INK_SACK)) {
                        if (inRow.contains(us)) {
                            inRow.remove(us);
                            ItemStack ink = new ItemStack(Material.INK_SACK, 1, (short) 8);
                            us.getPlayer().getInventory().setItem(5, transform(ink, FancyText.colored("&aSumo Rápido! &7(&cDesativado&7)")));
                            us.getPlayer().sendMessage(FancyText.colored("&d&lSUMO &8➸ &7Você Saiu Das partidas rápidas!"));
                        } else {
                            if (!inRow.isEmpty()) {
                                inWait.add(us);
                                inWait.add(inRow.get(0));
                                startDuel(us, inRow.get(0));
                                inRow.get(0).getPlayer().sendMessage(FancyText.colored("&d&lSUMO &8➸ &7Partida Encontrada..."));
                                us.getPlayer().sendMessage(FancyText.colored("&d&lSUMO &8➸ &7Partida Encontrada..."));
                            } else {
                                inRow.add(us);
                                ItemStack ink = new ItemStack(Material.INK_SACK, 1, (short) 10);
                                us.getPlayer().getInventory().setItem(5, transform(ink, FancyText.colored("&aDuelo Rápido! &7(&aAtivado&7)")));
                                us.getPlayer().sendMessage(FancyText.colored("&d&lSUMO &8➸ &7Você Entrou para as partidas rápidas!"));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void clicked(@NotNull OnClickInPlayer e) {
        // SE O PLAYER CLICADO JA TIVER CHAMADO ALGUEM
        if (e.getAuthor().getWarp().equals(WarpType.SUMO) && e.getClicked().getWarp().equals(WarpType.SUMO)) {
            if ((duelo.get(e.getAuthor()) != null) && duelo.get(e.getAuthor()).equals(e.getClicked())) {
                e.getAuthor().getPlayer().sendMessage(FancyText.colored("&d&lSUMO &8➸ &7Você já chamou este jogador! Aguarde aceitar!"));
            } else {
                if (duelo.containsKey(e.getClicked())) {
                    // VERIFICAR  SE O PLAYER CLICADO FOI OQUE DESAFIOU
                    if (duelo.get(e.getClicked()).equals(e.getAuthor())) {
                        duelo.remove(e.getAuthor());
                        duelo.remove(e.getClicked());
                        inRow.remove(e.getClicked());
                        inRow.remove(e.getAuthor());
                        inWait.add(e.getClicked());
                        inWait.add(e.getAuthor());
                        inDuel.remove(e.getAuthor());
                        inDuel.remove(e.getClicked());
                        startDuel(e.getAuthor(), e.getClicked());
                        // ACAO DE IR PARA AS ARENAS AQUI!
                    } else {
                        // CASO O PLAYER CLICADO NAO SEJA O QUE DESAFIOU!
                        JSONMessage msgtopla = new JSONMessage("\n &d&lSUMO &8➸ &7Você Foi desafiado para um duelo\n&f Pelo jogador " + e.getAuthor().getUsername());
                        JSONMessage.ChatExtra extra = new JSONMessage.ChatExtra("\n&e&lCLIQUE AQUI");
                        extra.addHoverEvent(JSONMessage.HoverEventType.SHOW_TEXT, "&aClique Aqui para Aceitar!");
                        extra.addClickEvent(JSONMessage.ClickEventType.RUN_COMMAND, "/aceitar1v1 " + e.getAuthor().getUsername());
                        msgtopla.addExtra(extra);
                        msgtopla.addExtra(new JSONMessage.ChatExtra("&f Para Aceitar O Convite!"));
                        msgtopla.addExtra(new JSONMessage.ChatExtra("&f Para Aceitar O Convite!"));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "tellraw " + e.getClicked().getUsername() + " " + msgtopla);
                        e.getAuthor().getPlayer().sendMessage(FancyText.colored("&d&lSUMO &8➸ &fVocê Desafiou o Jogador " + e.getClicked().getUsername() + "\n&fAguarde Até que ele aceite!"));
                        duelo.put(e.getAuthor(), e.getClicked());
                    }
                } else {

                    // CASO O PLAYER CLICADO NAO TENHA CONVIDADO NINGUEM!

                    JSONMessage msgtopla = new JSONMessage("\n &d&lSUMO &8➸ &fVocê Foi desafiado para um duelo\n&f Pelo jogador " + e.getAuthor().getUsername());
                    JSONMessage.ChatExtra extra = new JSONMessage.ChatExtra("\n&e&lCLIQUE AQUI");
                    extra.addHoverEvent(JSONMessage.HoverEventType.SHOW_TEXT, "&aClique Aqui para Aceitar!");
                    extra.addClickEvent(JSONMessage.ClickEventType.RUN_COMMAND, "/aceitar1v1 " + e.getAuthor().getUsername());
                    msgtopla.addExtra(extra);
                    msgtopla.addExtra(new JSONMessage.ChatExtra("&f Para Aceitar O Convite!"));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "tellraw " + e.getClicked().getUsername() + " " + msgtopla);
                    e.getAuthor().getPlayer().sendMessage(FancyText.colored("&d&lSUMO &8➸ &fVocê Desafiou o Jogador " + e.getClicked().getUsername() + "\n&fAguarde Até que ele aceite!"));
                    duelo.put(e.getAuthor(), e.getClicked());
                }
            }
        }
    }

    @EventHandler
    public void mecher(PlayerMoveEvent e) {
        User us = UserManager.getPlayer(e.getPlayer());
        if (inWait.contains(us)) {
            e.setTo(e.getPlayer().getLocation());
        }
    }

    @EventHandler
    public void aoBater(EntityDamageByEntityEvent e) {
        if ((e.getDamager() instanceof Player) && (e.getEntity() instanceof Player)) {
            User author = UserManager.getPlayer((Player) e.getDamager());
            User victim = UserManager.getPlayer((Player) e.getEntity());
            if (inDuel.containsKey(author) && inDuel.containsKey(victim)) {
                e.setDamage(0);
            }
        }
    }

    public static void startDuel(User user1, User user2) {

        if (user1.getWarp().equals(WarpType.SUMO) && user2.getWarp().equals(WarpType.SUMO)) {
            user1.getPlayer().setMaxHealth(20);
            user1.getPlayer().setHealth(20);
            user2.getPlayer().setMaxHealth(20);
            user2.getPlayer().setHealth(20);
            user1.getPlayer().teleport(WarpLibrary.getWarp(WarpType.SUMO).getFirstSpawn());
            user2.getPlayer().teleport(WarpLibrary.getWarp(WarpType.SUMO).getSecondSpawn());
            user1.getPlayer().getInventory().clear();
            user2.getPlayer().getInventory().clear();

            TitleSchema.sendDuelsTitle(user1.getPlayer(), user2.getPlayer());
            new BukkitRunnable() {
                public void run() {
                    Sound.NOTE_PLING.play(user1.getPlayer(), 10, 10);
                    Sound.NOTE_PLING.play(user2.getPlayer(), 10, 10);
                    MineReflect.sendTitle(user1.getPlayer(), FancyText.colored("&e3..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                    MineReflect.sendTitle(user2.getPlayer(), FancyText.colored("&e3..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                }
            }.runTaskLater(Main.getInstance(), 20);
            new BukkitRunnable() {
                public void run() {
                    Sound.NOTE_PLING.play(user1.getPlayer(), 10, 10);
                    Sound.NOTE_PLING.play(user2.getPlayer(), 10, 10);
                    MineReflect.sendTitle(user1.getPlayer(), FancyText.colored("&e2..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                    MineReflect.sendTitle(user2.getPlayer(), FancyText.colored("&e2..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                }
            }.runTaskLater(Main.getInstance(), 40);
            new BukkitRunnable() {
                public void run() {
                    Sound.NOTE_PLING.play(user1.getPlayer(), 10, 10);
                    Sound.NOTE_PLING.play(user2.getPlayer(), 10, 10);
                    MineReflect.sendTitle(user1.getPlayer(), FancyText.colored("&e1..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                    MineReflect.sendTitle(user2.getPlayer(), FancyText.colored("&e1..."), FancyText.colored("&7Iniciando em..."), 10, 10, 10);
                }
            }.runTaskLater(Main.getInstance(), 60);
            new BukkitRunnable() {
                public void run() {
                    Sound.FIRE_IGNITE.play(user1.getPlayer(), 10, 10);
                    Sound.FIRE_IGNITE.play(user2.getPlayer(), 10, 10);
                    MineReflect.sendTitle(user1.getPlayer(), FancyText.colored("&e0..."), FancyText.colored("&cLutem!"), 10, 10, 10);
                    MineReflect.sendTitle(user2.getPlayer(), FancyText.colored("&e0..."), FancyText.colored("&cLutem!"), 10, 10, 10);
                    inWait.remove(user1);
                    inWait.remove(user2);
                    inDuel.put(user1, user2);
                    inDuel.put(user2, user1);

                    user1.getPlayer().setMaxHealth(20);
                    user1.getPlayer().setHealth(user1.getPlayer().getMaxHealth());
                    Protecao.setImortal(user1.getPlayer(), false);
                    Protecao.setImortal(user2.getPlayer(), false);
                    for (Player plr : Bukkit.getServer().getOnlinePlayers()) {
                        user1.getPlayer().hidePlayer(plr);
                        user2.getPlayer().hidePlayer(plr);
                        user2.getPlayer().showPlayer(user1.getPlayer());
                        user1.getPlayer().showPlayer(user2.getPlayer());
                    }
                }
            }.runTaskLater(Main.getInstance(), 80);
        }
    }


    public static ItemStack transform(ItemStack item, String name, String... lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(FancyText.colored(name));
        im.setLore(Arrays.asList(FancyText.colored(lore)));
        item.setItemMeta(im);
        return item;
    }

    @EventHandler
    public void aoEntrarEmAgua(PlayerMoveEvent e) {
        Player sofreu = e.getPlayer();
        User loser = UserManager.getPlayer(sofreu);
        if (loser.getWarp().equals(WarpType.SUMO)) {
            if (inDuel.containsKey(loser) || inDuel.containsValue(loser)) {
                User winner = inDuel.get(loser);

                Block block = sofreu.getLocation().getBlock();
                if (block.getType().equals(Material.WATER) || block.getType().equals(Material.STATIONARY_WATER)) {
                    Bukkit.getPluginManager().callEvent(new OnPlayerWinSumo(winner, loser));
                }
            }
        }
    }

}
