package ridev.com.br.eventos;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.cargos.GroupManager;
import ridev.com.br.api.killstreak.KillStreak;
import ridev.com.br.api.lobby.LobbyManager;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.language.LangValue;
import ridev.com.br.sql.Backend;
import ridev.com.br.utils.apis.Mine;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.scoreboard.ScoreboardManager;
import ridev.com.br.utils.text.FancyText;
import ridev.com.br.utils.title.TitleSchema;

import java.util.Map;
import java.util.Objects;

public class aoEntrar implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        //->
        //                            PARTE DE CRIAÇAO DA DATABASE
        //<--
        //-
        //                            PARTE DE CRIAÇAO DE MATERIAIS
        //<--
        Mine.clearInventory(p);
        for (Map.Entry<Integer, ItemStack> itens : LobbyManager.lobby().getItens().entrySet()) {
            p.getInventory().setItem(itens.getKey(), itens.getValue());
        }
        //->
        //                            OUTROS
        //<--
        //

        TitleSchema.sendSpawnTitle(p);
        if (!LangValue.get(LangValue::boasVindas).isEmpty() && LangValue.get(LangValue::boasVindas) != null) {
            for (String s : LangValue.get(LangValue::boasVindas)) {
                p.sendMessage(FancyText.colored(s).replace("%player%", p.getName()));
            }
        }
        p.setMaxHealth(0.5);
        p.setHealth(0.5);
        Protecao.setImortal(p, true);
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        p.setFireTicks(0);
        p.setGameMode(GameMode.SURVIVAL);
        new BukkitRunnable() {
            @Override
            public void run() {

                User us = UserManager.getPlayer(p);
                us.setWarp(WarpType.LOBBY);
                if (us.getRole() != GroupManager.getPlayerGroup(p)) {
                    Backend.executor().updateQuery("UPDATE jogadores SET role = ? WHERE nome = ?", s -> {
                        s.set(1, Objects.requireNonNull(GroupManager.getPlayerGroup(p)).getRoleName());
                        s.set(2, p.getName());
                    });
                }
                if (us.getRole().isBroadcast()) {
                    for (Player p3 : Bukkit.getServer().getOnlinePlayers()) {
                        p3.sendMessage(FancyText.colored("&7[&a+&7] &6O Jogador " + us.getRole().getPrefix() + us.getRole().getRoleColor() + p.getName() + " &6Entrou neste Lobby!"));
                    }
                }
                for (Player p2 : Bukkit.getOnlinePlayers()) {
                    ScoreboardManager.setScore(p2);
                    User user = UserManager.getPlayer(p2);
                    MineReflect.sendNameTag(p2, FancyText.colored(user.getRole().getPrefix() + ""), FancyText.colored(" &7(" + user.getRank().getSymbol() + "&7)"), user.getRole().getPriority());
                }

            }

        }.runTaskLater(Main.getInstance(), 1);
        //->
        //                            AVISOS INICIAIS
        //<--
        if (!LobbyManager.lobbyIsSet()) {
            if (p.hasPermission("rikitpvp.admin")) {
                p.sendMessage(FancyText.colored("&eRiKitPvP &8➸ &7A localização do Lobby não foi demarcada, por favor utilize /kp E selecione o menu de Warps!"));
            }
        } else {
            p.teleport(LobbyManager.lobby().getLocation());
        }

        if (p.getName().equalsIgnoreCase("matadorbrsinhoo") || p.getName().equalsIgnoreCase("yRicardinBaum")) {
            p.sendMessage(FancyText.colored("&eRiKitPvP &8➸ &aEste Servidor utiliza o RiKitPvP Como plugin de kitpvp!"));
        }

        KillStreak.killstreak.put(p, 0);

    }


}
