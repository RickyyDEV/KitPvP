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
            sender.sendMessage(FancyText.colored("&cUtilize /aceitar <jogador>"));
        } else {
            if (!OnevsOne.getDuelo().containsValue(user)) {
                sender.sendMessage(FancyText.colored("&c&lERRO &8➸ &cVocê não tem ninguém para aceitar no momento!"));
            } else if (!OnevsOne.getDuelo().get(UserManager.getPlayer(args[0])).equals(user)) {
                sender.sendMessage(FancyText.colored("&c&lERRO &8➸ &cEste usuário não te desafiou!"));
            } else {
                OnevsOne.getDuelo().remove(user);
                OnevsOne.getDuelo().remove(OnevsOne.getDuelo().get(UserManager.getPlayer(args[0])));
                OnevsOne.getInRow().remove(OnevsOne.getDuelo().get(UserManager.getPlayer(args[0])));
                OnevsOne.getInRow().remove(user);
                OnevsOne.getInWait().add(OnevsOne.getDuelo().get(UserManager.getPlayer(args[0])));
                OnevsOne.getInWait().add(user);
                OnevsOne.getInDuel().remove(user);
                OnevsOne.getInDuel().remove(OnevsOne.getDuelo().get(UserManager.getPlayer(args[0])));
                OnevsOne.startDuel(user, OnevsOne.getDuelo().get(UserManager.getPlayer(args[0])));
            }
        }

    }
}
