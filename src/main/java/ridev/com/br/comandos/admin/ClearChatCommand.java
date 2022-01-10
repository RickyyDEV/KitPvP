package ridev.com.br.comandos.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

public class ClearChatCommand extends Commands {

    public ClearChatCommand() {
        super("clearchat", "cc");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::clearchatPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::clearchatPermission))) {
            for (int i = 0; i < 200; i++) {
                Bukkit.broadcastMessage(" ");
            }
            Bukkit.broadcastMessage(FancyText.colored(LangValue.get(LangValue::clearChatFinalMessage).replace("%player%", p.getName())));
        } else {
            p.sendMessage(FancyText.colored(LangValue.get(LangValue::clearChatNoPermission)));
        }
    }
}
