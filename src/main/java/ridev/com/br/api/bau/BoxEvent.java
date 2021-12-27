package ridev.com.br.api.bau;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.builder.BuildAPI;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.Main;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;

@Data
public class BoxEvent implements Listener {
    // CHAT EVENT
    @Getter
    private static HashMap<Player, Boolean> playersChat = Maps.newHashMap();
    @Getter
    private static HashMap<Player, User> user = Maps.newHashMap();
    @Getter
    private static HashMap<Player, BoxType> type = Maps.newHashMap();


    @Getter
    private static HashMap<Player, Boolean> players = Maps.newHashMap();
    @Getter
    private static HashMap<Player, String> names = Maps.newHashMap();

    @EventHandler
    public void chestSetEvent(BlockBreakEvent e) {
        if (players.get(e.getPlayer()) != null && players.get(e.getPlayer())) {
            Block bloco = e.getBlock();
            Player p1 = e.getPlayer();
            String name = names.get(e.getPlayer());
            if (bloco.getType().equals(Material.CHEST) && !BoxManager.isMysteryBox(bloco)) {
                e.setCancelled(true);
                Box box = new Box(name, bloco);
                box.createMysteryBox();
                box.saveInConfig();
                BuildAPI.removeBuilder(p1);
                p1.setGameMode(GameMode.SURVIVAL);
                players.remove(p1);
                names.remove(p1);
            } else {
                p1.sendMessage(FancyText.colored("&c&lERRO &8➸ &cVocê não pode setar este bloco como bau!"));
            }
        }
    }


    @EventHandler
    public void chestClickEvent(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.CHEST)) {
                if (BoxManager.isMysteryBox(e.getClickedBlock())) {
                    e.setCancelled(true);
                    Player p = e.getPlayer();
                    BoxInventory.openInv(p, BoxManager.getBox(e.getClickedBlock()));
                }
            }
        }
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (playersChat.containsKey(p)) {
            e.setCancelled(true);
            if (!user.containsKey(p)) {
                if (UserManager.getPlayer(e.getMessage()) != null || UserManager.getOfflineUser(e.getMessage()) != null) {
                    user.put(p, UserManager.getOfflineUser(e.getMessage()));
                    Sound.CHICKEN_EGG_POP.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &aDigite O Tipo do Baú!"));
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &cEste Jogador Não Encontra-se no meu banco de dados!"));
                    playersChat.remove(p);
                }
            } else if (playersChat.containsKey(p) && user.containsKey(p) && !type.containsKey(p)) {
                if (e.getMessage().equalsIgnoreCase("master") || e.getMessage().equalsIgnoreCase("basico") || e.getMessage().equalsIgnoreCase("mediano") || e.getMessage().equalsIgnoreCase("raro") || e.getMessage().equalsIgnoreCase("supremo")) {

                    type.put(p, BoxType.transform(e.getMessage()));
                    Sound.CHICKEN_EGG_POP.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &aDigite a Quantidade!"));
                } else {
                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &cEste Bau Citado não existe!"));
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    user.remove(p);
                    playersChat.remove(p);
                }
            } else if (type.containsKey(p)) {
                if (e.getMessage().matches("-?\\d+")) {
                    if (Integer.parseInt(e.getMessage()) > 30) {
                        type.remove(p);
                        user.remove(p);
                        playersChat.remove(p);
                        p.sendMessage(FancyText.colored("&eKitPvP &8➸ &cPara segurança do servidor, Você não pode adicionar mais de 30 Baús por vez!"));

                    } else {
                        if (Integer.parseInt(e.getMessage()) > 0) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    String quantity = e.getMessage();
                                    Sound.NOTE_PLING.play(p, 10, 10);
                                    User us = user.get(p);
                                    BoxType typebox = type.get(p);
                                    us.addBoxes(type.get(p), Integer.parseInt(quantity));
                                    p.sendMessage(FancyText.colored("&eKitPvP &7Você Adicionou " + quantity + " Baús do tipo " + typebox.toString() + " Na conta do Jogador " + us.getUsername()));
                                    new BoxInventory().init().updateInventory(p);
                                    p.sendMessage(us.getBoxes().toString());
                                    type.remove(p);
                                    user.remove(p);
                                    playersChat.remove(p);
                                }
                            }.runTaskLater(Main.getInstance(), 1);

                        }
                    }
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &cO valor que você citou não é um número!"));
                    type.remove(p);
                    user.remove(p);
                    playersChat.remove(p);
                }
            }
        }
    }
}
