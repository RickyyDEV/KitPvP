package ridev.com.br.api.combat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.text.FancyText;

import java.text.DecimalFormat;
import java.util.HashMap;

public class CombatLogAPI implements Listener {
    public static HashMap<Player, Player> pcombat = new HashMap<>();

    @EventHandler
    public void aoBater(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player dando = (Player) e.getDamager();
            Player recebendo = (Player) e.getEntity();
            if (!Protecao.isImortal(dando) && !Protecao.isImortal(recebendo)) {
                if (!pcombat.containsKey(dando)) {
                    String vidad = new DecimalFormat("0").format(dando.getHealth());
                    String vidar = new DecimalFormat("0").format(recebendo.getHealth());
                    MineReflect.sendActionBar(dando, FancyText.colored("&c✧  &fVocê entrou em combate com &e" + recebendo.getName() + "&a/ &c❤ " + vidar + "&f/" + recebendo.getMaxHealth()));
                    MineReflect.sendActionBar(recebendo, FancyText.colored("&c✧  &fVocê entrou em combate com &e" + dando.getName() + "&a/ &c❤ &7" + vidad + "&f/" + dando.getMaxHealth()));
                    pcombat.put(dando, recebendo);
                    pcombat.put(recebendo, dando);
                    new BukkitRunnable() {
                        public void run() {
                            pcombat.remove(dando);
                            pcombat.remove(recebendo);
                        }
                    }.runTaskLater(Main.getInstance(), 200);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void sair(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        CombatLogAPI.pcombat.remove(p);
    }

    public static boolean playerIsInCombat(Player p) {
        return pcombat.containsKey(p);
    }

    public static Player getAdversary(Player p) {
        return pcombat.get(p);
    }

    public static void removePlayerCombat(Player p) {
        pcombat.remove(p);
    }
}
