package ridev.com.br.comandos.users;

import org.bukkit.entity.Player;
import ridev.com.br.api.inventory.ProfileInventory;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.text.FancyText;

public class ProfileCommand extends Commands {

    public ProfileCommand() {
        super("profile", "perfil");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::profilePermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::profilePermission))) {
            if (args.length == 0) {
                ProfileInventory.users.remove(UserManager.getPlayer(p));
                new ProfileInventory().init().openInventory(p);
            } else {
                if (args[0].equalsIgnoreCase(p.getName())) {
                    new ProfileInventory().init().openInventory(p);
                }
                User us = UserManager.getPlayer(args[0]);
                if (us != null) {
                    ProfileInventory.users.remove(UserManager.getPlayer(p));
                    new ProfileInventory().openInv(p, us);
                } else {
                    User us2 = UserManager.getOfflineUser(args[0]);
                    if (us2 != null) {
                        ProfileInventory.users.remove(UserManager.getPlayer(p));
                        new ProfileInventory().openInv(p, us2);
                    } else {
                        p.sendMessage(FancyText.colored("&b&lPROFILE &8➸ &7Usuário &cnão encontrado&7!"));
                    }
                }
            }
        } else {
            p.sendMessage(FancyText.colored("&cVocê não possui permissão para executar este Comando!"));
        }
    }
}
