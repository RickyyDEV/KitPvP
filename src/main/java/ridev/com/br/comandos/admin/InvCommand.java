package ridev.com.br.comandos.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

public class InvCommand extends Commands {

    public InvCommand() {
        super("inv", "inventory");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::invPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::invPermission))) {
            if (args.length > 0) {
                Player toSee = Bukkit.getPlayerExact(args[0]);
                if (toSee != null) {
                    Inventory inv = Bukkit.createInventory(p, 9 * 4, FancyText.colored("&7Inventário de " + toSee.getName()));
                    inv.setContents(toSee.getInventory().getContents());

                    p.openInventory(inv);
                    p.sendMessage(FancyText.colored(LangValue.get(LangValue::seeingInventoryInv)).replace("%player%", p.getName()));
                } else {
                    p.sendMessage(FancyText.colored("&b&lINVENTORY &8➸ &7Este usuário &cNão existe, ou não está online&7!"));
                }
            } else {
                p.sendMessage(FancyText.colored(LangValue.get(LangValue::invNotFound)));
            }
        } else {
            p.sendMessage(FancyText.colored(LangValue.get(LangValue::invNoPermission)));
        }
    }
}
