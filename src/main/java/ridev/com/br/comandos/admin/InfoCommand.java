package ridev.com.br.comandos.admin;

import org.bukkit.entity.Player;
import ridev.com.br.api.inventory.ServerInfoInventory;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

public class InfoCommand extends Commands {

    public InfoCommand() {
        super("info", "serverinfo", "ssinfo");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::infoPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::infoPermission))) {
            new ServerInfoInventory().init().openInventory(p);
        } else {

            p.sendMessage(FancyText.colored(LangValue.get(LangValue::infoNoPermission)));
        }
    }
}
