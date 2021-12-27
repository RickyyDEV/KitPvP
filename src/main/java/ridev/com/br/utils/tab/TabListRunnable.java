package ridev.com.br.utils.tab;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ridev.com.br.Main;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.other.ModuleLogger;
import ridev.com.br.utils.text.FancyText;

import java.util.logging.Level;

public class TabListRunnable {

    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP TabList");

    public TabListRunnable(Main plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 0, 5);
        LOGGER.log(Level.INFO, "TabList Carregada Com sucesso!");
    }


    public void update() {
        for (Player p2 : Bukkit.getOnlinePlayers()) {
            User user = UserManager.getPlayer(p2.getName());
            String total2 = String.valueOf(Bukkit.getServer().getOnlinePlayers().size());
            String kills2 = String.valueOf(user.getKills());
            String mortes2 = String.valueOf(user.getMortes());
            String coins2 = String.valueOf(user.getCoins());
            String xp2 = String.valueOf(user.getXp());
            String rankName2 = user.getRank().getBeatifulName();
            String rankSybol2 = user.getRank().getSymbol();
            String link = LangValue.get(LangValue::link);
            StringBuilder tabhead2 = new StringBuilder();
            for (String s : LangValue.get(LangValue::tabTitle)) {
                tabhead2.append(FancyText.colored(s.replaceAll("\n", "") + "\n").replace("%player%", p2.getName()).replace("%playerstotal%", total2).replace("%ping%", String.valueOf(((CraftPlayer) p2).getHandle().ping)).replace("%rank_symbol%", rankSybol2).replace("%tag%", user.getRole().getPrefix()).replace("%rank%", rankName2).replace("%xp%", xp2).replace("%kills%", kills2).replace("%deaths%", mortes2).replace("%coins%", coins2).replace("%link%", link));
            }
            StringBuilder tabfooter2 = new StringBuilder();
            for (String s : LangValue.get(LangValue::tabFooter)) {
                tabfooter2.append(FancyText.colored(s.replaceAll("\n", "") + "\n").replace("%player%", p2.getName()).replace("%playerstotal%", total2).replace("%ping%", String.valueOf(((CraftPlayer) p2).getHandle().ping)).replace("%rank_symbol%", rankSybol2).replace("%tag%", user.getRole().getNameWithColor()).replace("%rank%", rankName2).replace("%xp%", xp2).replace("%kills%", kills2).replace("%deaths%", mortes2).replace("%coins%", coins2).replace("%link%", link));
            }
            MineReflect.setTabList(p2, tabhead2.toString(), tabfooter2.toString());
        }
    }
}
