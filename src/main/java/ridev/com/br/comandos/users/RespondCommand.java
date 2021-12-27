package ridev.com.br.comandos.users;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.text.FancyText;

public class RespondCommand extends Commands {
    public RespondCommand() {
        super("responder", "r", "respond");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::respondPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::respondPermission))) {
            User us = UserManager.getPlayer(p);
            if (TellCommand.cacheTell.containsKey(us)) {
                if (TellCommand.cacheTell.get(us).getPlayer() != null) {
                    Player toTell = Bukkit.getPlayerExact(args[0]);
                    StringBuilder message = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        message.append(" ").append(args[i]);
                    }
                    String messageToTell = "&8De " + p.getName() + "&8: " + "&6" + message;
                    String messageToAuthor = "&8Para " + toTell.getName() + "&8: " + "&6" + message;
                    p.sendMessage(FancyText.colored(messageToAuthor));
                    toTell.sendMessage(FancyText.colored(messageToTell));
                } else {
                    TellCommand.cacheTell.remove(us);
                }
            } else {
                p.sendMessage(FancyText.colored("&b&lTELL &8➸ &7Você &cnão possue ninguém para responder&7!"));
            }
        } else {
            p.sendMessage(FancyText.colored("&cVocê não possui permissão para executar este Comando!"));
        }
    }
}
