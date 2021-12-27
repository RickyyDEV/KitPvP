package ridev.com.br.comandos.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

public class TpHereCommand extends Commands {
    public TpHereCommand() {
        super("tphere");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::tpherePermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::tpherePermission))) {
            if (args.length == 0) {
                p.sendMessage(FancyText.colored("&b&lTP &8➸ &7Utilize: &6/tphere <jogador>"));
            } else {
                if (args[0].equalsIgnoreCase(p.getName())) {
                    p.sendMessage(FancyText.colored(LangValue.get(LangValue::tphereYourSelf)));
                } else {
                    if (Bukkit.getPlayerExact(args[0]) != null) {
                        Bukkit.getPlayerExact(args[0]).teleport(p.getLocation());
                        p.sendMessage(FancyText.colored(LangValue.get(LangValue::tphereSucess)));
                    } else {
                        p.sendMessage(FancyText.colored(LangValue.get(LangValue::tphereNotFound)));
                    }
                }
            }
        } else {
            p.sendMessage(FancyText.colored(LangValue.get(LangValue::tphereNoPermission)));
        }
    }
}
