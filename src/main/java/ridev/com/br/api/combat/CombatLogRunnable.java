package ridev.com.br.api.combat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.Main;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.other.ModuleLogger;
import ridev.com.br.utils.text.FancyText;

import java.text.DecimalFormat;
import java.util.logging.Level;

public class CombatLogRunnable {
    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP CombatLog");

    public CombatLogRunnable(Main plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 0, 10);
        LOGGER.log(Level.INFO, "CombatLog system carregado com sucesso!");
    }

    public void update() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (CombatLogAPI.playerIsInCombat(p)) {
                String vidad = new DecimalFormat("0.0").format(CombatLogAPI.getAdversary(p).getHealth());
                MineReflect.sendActionBar(p, FancyText.colored("&c✧  &fVocê entrou em combate com &e" + CombatLogAPI.getAdversary(p).getName() + "&a/ &c❤ &c" + vidad));
            } else {
                MineReflect.sendActionBar(p, FancyText.colored("&d✧ &aVocê não está em combate!"));
            }
        }
    }
}
