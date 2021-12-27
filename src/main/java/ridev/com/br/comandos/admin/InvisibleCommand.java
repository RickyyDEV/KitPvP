package ridev.com.br.comandos.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;

public class InvisibleCommand extends Commands {

    public InvisibleCommand() {
        super("invisible", "invis");
    }

    HashMap<Player, Boolean> pl = new HashMap<>();

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::invisiblePermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::invisiblePermission))) {
            if (pl.get(p) == null || !pl.get(p)) {
                for (Player p2 : Bukkit.getOnlinePlayers()) {
                    p2.hidePlayer(p);
                }
                pl.put(p, true);
                p.sendMessage(FancyText.colored(LangValue.get(LangValue::invisibleIsInvisible)));

            } else {
                for (Player p2 : Bukkit.getOnlinePlayers()) {
                    p2.showPlayer(p);
                }
                pl.put(p, false);
                p.sendMessage(FancyText.colored(LangValue.get(LangValue::invisibleIsVisible)));
            }
        } else {
            p.sendMessage(FancyText.colored(LangValue.get(LangValue::invisibleNoPermission)));
        }
    }
}
