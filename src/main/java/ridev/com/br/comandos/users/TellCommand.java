package ridev.com.br.comandos.users;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;

public class TellCommand extends Commands {
    public TellCommand() {
        super("tell", "msg");
    }

    public static HashMap<User, User> cacheTell = new HashMap<>();


    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::tellPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::tellPermission))) {
            User us = UserManager.getPlayer(p);
            if (args.length < 2) {
                p.sendMessage(FancyText.colored("&b&lTELL &8➸ &7Utilize: &6/tell <jogador> <mensagem>"));
            } else {
                if (args[0].equalsIgnoreCase(p.getName())) {
                    p.sendMessage(FancyText.colored("&b&lTELL &8➸ &7Você &cnão pode enviar um tell para você mesmo&7!"));
                } else {
                    if (Bukkit.getPlayerExact(args[0]) != null) {
                        Player toTell = Bukkit.getPlayerExact(args[0]);
                        User toTellUs = UserManager.getPlayer(toTell);
                        StringBuilder message = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            String finalmsg = args[i] + " ";
                            message.append(finalmsg);
                        }
                        String messageToTell = "&8De " + p.getName() + "&8: " + "&6" + message;
                        String messageToAuthor = "&8Para " + toTell.getName() + "&8: " + "&6" + message;
                        p.sendMessage(FancyText.colored(messageToAuthor));
                        cacheTell.put(us, toTellUs);
                        toTell.sendMessage(FancyText.colored(messageToTell));
                    } else {
                        p.sendMessage(FancyText.colored("&b&lTELL &8➸ &7O usuário " + args[0] + "&não existe&7!"));
                    }
                }
            }
        } else {
            p.sendMessage(FancyText.colored("&cVocê não possui permissão para executar este Comando!"));
        }
    }
}
