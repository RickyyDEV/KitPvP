package ridev.com.br.comandos.users;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.inventory.RankInventory;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.text.FancyText;

public class RankCommand extends Commands {

    public RankCommand() {
        super("rank", "ranque");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::rankPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::rankPermission))) {
            if (args.length == 0) {
                new RankInventory().init().openInventory(p);
            } else {
                User us = UserManager.getPlayer(args[0]);
                if (us != null) {
                    new BukkitRunnable() {
                        public void run() {
                            new RankInventory().openInv(p, us);
                        }
                    }.runTask(Main.getInstance());
                } else {
                    User us2 = UserManager.getOfflineUser(args[0]);
                    if (us2 != null) {
                        new BukkitRunnable() {
                            public void run() {
                                new RankInventory().openInv(p, us2);
                            }
                        }.runTask(Main.getInstance());
                    } else {
                        p.sendMessage(FancyText.colored("&b&lPROFILE &8➸ &7Usuário &cNão encontrado&7!"));
                    }
                }
            }
        } else {
            p.sendMessage(FancyText.colored("&cVocê não possui permissão para executar este Comando!"));
        }
    }
}
