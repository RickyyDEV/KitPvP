package ridev.com.br.comandos.admin;


import org.bukkit.entity.Player;
import ridev.com.br.Main;
import ridev.com.br.api.inventory.menu.MainInventory;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.text.FancyText;

public class KpCommand extends Commands {

    public KpCommand() {
        super("kitpvp", "kp");
    }


    @Override
    public void perform(Player sender, String label, String[] args) {

        if (ConfigValue.get(ConfigValue::kpPermission).isEmpty() || sender.hasPermission(ConfigValue.get(ConfigValue::kpPermission))) {
            new MainInventory().init().openInventory(sender);
        } else {
            sender.sendMessage(FancyText.colored("&6RiKitPvP &7[" + Main.getInstance().getDescription().getVersion() + "] &f- &7Criado por &5yRicardinBaumDEV&7."));
        }
    }
}