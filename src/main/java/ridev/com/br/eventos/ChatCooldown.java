package ridev.com.br.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ridev.com.br.api.bau.BoxEvent;
import ridev.com.br.api.cooldown.CooldownAPI;
import ridev.com.br.api.inventory.menu.coins.CoinsEvent;
import ridev.com.br.api.inventory.menu.leader.LeaderEvent;
import ridev.com.br.api.inventory.menu.mysterybox.addbox.AddBoxEvent;
import ridev.com.br.api.inventory.menu.xp.XpEvent;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

public class ChatCooldown implements Listener {

    @EventHandler
    public void aoEnviar(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        User user = UserManager.getPlayer(p);
        if (!XpEvent.getPlayers()
                .contains(p) && !AddBoxEvent.getPlayers().contains(user) && !CoinsEvent.getPlayers().contains(p) && !BoxEvent.getPlayersChat().containsKey(p) && !LeaderEvent.getPlayers().containsKey(p)) {
            if (ConfigValue.get(ConfigValue::cooldownEnable)) {
                if (!p.hasPermission(ConfigValue.get(ConfigValue::cooldownBypass))) {
                    if (!CooldownAPI.isInCooldown(p.getName(), "chat")) {
                        CooldownAPI cold = new CooldownAPI(p.getName(), "chat", ConfigValue.get(ConfigValue::cooldownTime));
                        cold.start();
                    } else {
                        p.sendMessage(FancyText.colored(LangValue.get(LangValue::cooldown)).replace("%time%", CooldownAPI.getTimeLeft(p.getName(), "chat") + "s").replace("%player%", p.getName()));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}

