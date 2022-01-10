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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TabListRunnable {

    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP TabList");

    public static List<Player> players = new ArrayList<>();

    public TabListRunnable(Main plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 0, 5);
        LOGGER.log(Level.INFO, "TabList Carregada Com sucesso!");
    }


    public void update() {
        for (User p : UserManager.getAllUsers()) {
            setTabList(p);
        }
    }


    public static void setTabList(User p) {
        if (p != null) {
            if (p.getPlayer() != null) {
                String total2 = String.valueOf(Bukkit.getServer().getOnlinePlayers().size());
                String kills2 = String.valueOf(p.getKills());
                String mortes2 = String.valueOf(p.getMortes());
                String coins2 = String.valueOf(p.getCoins());
                String xp2 = String.valueOf(p.getXp());
                String rankName2 = p.getRank().getBeatifulName();
                String rankSybol2 = p.getRank().getSymbol();
                String link = LangValue.get(LangValue::link);
                StringBuilder tabhead2 = new StringBuilder();
                for (String s : LangValue.get(LangValue::tabTitle)) {
                    assert rankName2 != null;
                    assert rankSybol2 != null;
                    tabhead2.append(FancyText.colored(s.replaceAll("\n", "") + "\n").replace("%player%", p.getUsername()).replace("%playerstotal%", total2).replace("%ping%", String.valueOf(((CraftPlayer) p.getPlayer()).getHandle().ping)).replace("%rank_symbol%", rankSybol2).replace("%tag%", p.getRole().getPrefix()).replace("%rank%", rankName2).replace("%xp%", xp2).replace("%kills%", kills2).replace("%deaths%", mortes2).replace("%coins%", coins2).replace("%link%", link));
                }
                StringBuilder tabfooter2 = new StringBuilder();
                for (String s : LangValue.get(LangValue::tabFooter)) {
                    assert rankName2 != null;
                    assert rankSybol2 != null;
                    tabfooter2.append(FancyText.colored(s.replaceAll("\n", "") + "\n").replace("%player%", p.getUsername()).replace("%playerstotal%", total2).replace("%ping%", String.valueOf(((CraftPlayer) p.getPlayer()).getHandle().ping)).replace("%rank_symbol%", rankSybol2).replace("%tag%", p.getRole().getNameWithColor()).replace("%rank%", rankName2).replace("%xp%", xp2).replace("%kills%", kills2).replace("%deaths%", mortes2).replace("%coins%", coins2).replace("%link%", link));
                }
                MineReflect.setTabList(p.getPlayer(), tabhead2.toString(), tabfooter2.toString());
            }
        }
    }
}
