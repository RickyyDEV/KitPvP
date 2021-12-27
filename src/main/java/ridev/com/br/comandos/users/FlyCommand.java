package ridev.com.br.comandos.users;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

public class FlyCommand extends Commands {
    public FlyCommand() {
        super("fly", "voar");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        User us = UserManager.getPlayer(p);
        if (ConfigValue.get(ConfigValue::flyPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::flyPermission))) {
            if (us.getWarp().equals(WarpType.LOBBY)) {
                if (args.length == 0) {
                    if (p.getAllowFlight()) {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.sendMessage(FancyText.colored(LangValue.get(LangValue::flyCanotFly)));
                    } else {
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        p.sendMessage(FancyText.colored(LangValue.get(LangValue::flyCanFly)));

                    }
                } else {
                    Player other = Bukkit.getPlayerExact(args[0]);
                    if (other != null) {
                        if (other.getAllowFlight()) {
                            other.setAllowFlight(false);
                            other.setFlying(false);
                            p.sendMessage(FancyText.colored(LangValue.get(LangValue::flyCanotFly)));
                        } else {
                            other.setAllowFlight(true);
                            other.setFlying(true);
                            p.sendMessage(FancyText.colored(LangValue.get(LangValue::flyCanFly)));
                        }
                    } else {
                        p.sendMessage(FancyText.colored(LangValue.get(LangValue::flyCanFly)));
                    }
                }
            } else {
                p.sendMessage(FancyText.colored(LangValue.get(LangValue::flyOnlyLobby)));
            }
        } else {
            p.sendMessage(FancyText.colored(LangValue.get(LangValue::flyNoPermission)));
        }
    }
}
