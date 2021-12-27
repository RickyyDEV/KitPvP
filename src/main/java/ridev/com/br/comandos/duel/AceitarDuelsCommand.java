package ridev.com.br.comandos.duel;

import org.bukkit.entity.Player;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.onevsone.OnevsOne;
import ridev.com.br.comandos.Commands;
import ridev.com.br.utils.text.FancyText;

public class AceitarDuelsCommand extends Commands {

    public AceitarDuelsCommand() {
        super("aceitar1v1", "accduel");
    }

    @Override
    public void perform(Player sender, String label, String[] args) {
        User user = UserManager.getPlayer(sender);
        if (args.length == 0) {
            sender.sendMessage(FancyText.colored("&cUtilize /aceitar1v1 <jogador>"));
        } else {
            User toAcept = UserManager.getPlayer(args[0]);
            if (!OnevsOne.getDuelo().get(toAcept).equals(user)) {
                sender.sendMessage(FancyText.colored("&c&lERRO &8➸ &cEste usuário não te desafiou!"));
            } else {
                OnevsOne.startDuel(user, toAcept);
            }
        }

    }
}
