package ridev.com.br.comandos.duel;

import org.bukkit.entity.Player;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.sumo.Sumo;
import ridev.com.br.comandos.Commands;
import ridev.com.br.utils.text.FancyText;

public class AceitarSumoCommand extends Commands {

    public AceitarSumoCommand() {
        super("aceitarsumo", "accsumo");
    }

    @Override
    public void perform(Player sender, String label, String[] args) {
        User user = UserManager.getPlayer(sender);
        if (args.length == 0) {
            sender.sendMessage(FancyText.colored("&cUtilize /aceitarsumo <jogador>"));
        } else {
            String p2 = args[0];
            User us = UserManager.getPlayer(p2);
            if (us != null) {
                if (!Sumo.getDuelo().containsKey(us)) {
                    sender.sendMessage(FancyText.colored("&c&lERRO &8➸ &cVocê não tem ninguém para aceitar no momento!"));
                } else if (!Sumo.getDuelo().get(us).equals(user)) {
                    sender.sendMessage(FancyText.colored("&c&lERRO &8➸ &cEste usuário não te desafiou!"));
                } else {
                    Sumo.startDuel(user, us);
                }
            } else {
                sender.sendMessage(FancyText.colored("&c&lERRO &8➸ &cEste usuário não te desafiou!"));
            }
        }

    }
}
