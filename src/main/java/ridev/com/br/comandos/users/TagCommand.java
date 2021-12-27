package ridev.com.br.comandos.users;

import org.bukkit.entity.Player;
import ridev.com.br.api.inventory.TagInventory;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.text.FancyText;

public class TagCommand extends Commands {
    public TagCommand() {
        super("tag", "roles");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::tagPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::tagPermission))) {
            new TagInventory().init().openInventory(p);
        } else {
            p.sendMessage(FancyText.colored("&cVocê não possui permissão para executar este Comando!"));
        }
    }
}
