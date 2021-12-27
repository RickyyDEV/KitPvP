package ridev.com.br.api.inventory.menu.mysterybox.addbox;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.text.FancyText;
import ridev.com.br.utils.other.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddBoxEvent implements Listener {

    @Getter
    static List<User> players = new ArrayList<>();

    @Getter
    static HashMap<User, User> users = new HashMap<>();

    @Getter
    static HashMap<User, BoxType> box = new HashMap<>();


    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        User us = UserManager.getPlayer(e.getPlayer());
        if (players.contains(us)) {
            e.setCancelled(true);
            if (box.containsKey(us)) {
                Sound.NOTE_PLING.play(p, 10, 10);
                if (e.getMessage().matches("-?\\d+")) {
                    int quantity = Integer.parseInt(e.getMessage());
                    users.get(us).addBoxes(box.get(us), quantity);
                    Sound.NOTE_PLING.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você acaba de Adicionar &b" + quantity + " &7Caixas " + box.get(us).getBeatifulName()) + " &7Na conta do jogador &d" + users.get(us).getUsername());
                    box.remove(us);
                    users.remove(us);
                    players.remove(us);
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Este Valor &cnão é um número&7!"));
                }
            } else if (users.containsKey(us)) {
                BoxType boxe = BoxType.transform(e.getMessage());
                if (box != null) {
                    p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Agora me diga a &aquantidade de caixas&7!"));
                    box.put(us, boxe);
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Este Tipo de caixa &cnão existe&7! &eLembrando que são: Basico, Mediano, Raro, Supremo, Master"));
                }
            } else {
                User user = UserManager.getPlayer(e.getMessage());
                if (user != null) {
                    p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Agora me Diga o &atipo da caixa&7!"));
                    users.put(us, user);
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Este usuário &cnão está em meu banco de dados&7! &eTente novamente!"));
                }
            }
        }
    }

}
