package ridev.com.br.api.killstreak;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;

public class KillStreak {


    public static HashMap<Player, Integer> killstreak = new HashMap<>();


    public static void verifyPlayer(Player us, boolean perdeu) {
        if (perdeu) {
            if (killstreak.get(us) != null) {
                if (killstreak.get(us) >= 5) {
                    Bukkit.broadcastMessage(FancyText.colored(LangValue.get(LangValue::ksLost).replace("%player%", us.getName()).replace("%kills%", String.valueOf(killstreak.get(us)))));
                }
            }
        }
        if (!perdeu) {
            if (killstreak.get(us) % 5 == 0) {
                Bukkit.broadcastMessage(FancyText.colored(LangValue.get(LangValue::ksPassed).replace("%player%", us.getName()).replace("%kills%", String.valueOf(killstreak.get(us)))));
            }

        }
    }


    public static void addKill(Player us) {
        killstreak.put(us, killstreak.get(us) + 1);
    }


}
