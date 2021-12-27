package ridev.com.br.utils.title;

import org.bukkit.entity.Player;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

public class TitleSchema {


    public static void sendSpawnTitle(Player p) {
        String titlet = LangValue.get(LangValue::spawnTitle).replace("%player%", p.getName());
        String sub = LangValue.get(LangValue::spawnSubtitle).replace("%player%", p.getName());
        MineReflect.sendTitle(p, FancyText.colored(titlet), FancyText.colored(sub), 20, 20, 20);
        Sound.NOTE_PLING.play(p, 10, 10);
    }


    public static void sendDuelsTitle(Player p1, Player p2) {
        String sub = "&a" + p1.getName() + " &7vs&a " + p2.getName();
        MineReflect.sendTitle(p1, "", FancyText.colored(sub), 20, 20, 20);
        MineReflect.sendTitle(p2, "", FancyText.colored(sub), 20, 20, 20);
        Sound.NOTE_PLING.play(p2, 10, 10);
        Sound.NOTE_PLING.play(p1, 10, 10);
    }

}
