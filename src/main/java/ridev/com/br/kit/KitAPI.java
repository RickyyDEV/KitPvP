package ridev.com.br.kit;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import ridev.com.br.api.kit.KitLibrary;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;

public class KitAPI {


    @SneakyThrows
    public static void addKit(Player p, String kitname) {
        User us = UserManager.getPlayer(p);
        us.addKit(KitLibrary.getKit(kitname));
    }

    @SneakyThrows
    public static boolean hasKits(String kit, User p) {
        return p.getKits().contains(KitLibrary.getKit(kit));
    }

}
