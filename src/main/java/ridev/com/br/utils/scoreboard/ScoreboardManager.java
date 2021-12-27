package ridev.com.br.utils.scoreboard;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ridev.com.br.Main;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.other.ModuleLogger;
import ridev.com.br.utils.text.FancyText;

import java.util.Map;
import java.util.logging.Level;

public final class ScoreboardManager {
    public static final Map<Player, FastBoard> scores = Maps.newConcurrentMap();
    Map<Player, Integer> title = Maps.newConcurrentMap();
    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP ScoreBoards");

    public ScoreboardManager(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this.createInternalListener(), plugin);
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 0, 5);
        LOGGER.log(Level.INFO, "Scoreboard Carregada Com sucesso!");
    }

    public void update() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (scores.containsKey(p)) {
                if (!title.containsKey(p)) title.put(p, 0);
                if (title.get(p) == LangValue.get(LangValue::scoreTitulo).size()) title.put(p, 0);
                FastBoard board = scores.get(p);
                board.updateTitle(FancyText.colored(LangValue.get(LangValue::scoreTitulo).get(title.get(p))));
                title.put(p, title.get(p) + 1);
            }
        }

    }

    public static void setScore(Player p) {
        User user = UserManager.getPlayer(p.getName());
        String total = String.valueOf(Bukkit.getServer().getOnlinePlayers().size());
        String kills = String.valueOf(user.getKills());
        String mortes = String.valueOf(user.getMortes());
        String coins = String.valueOf(user.getCoins());
        String xp = String.valueOf(user.getXp());
        String rankName = user.getRank().getBeatifulName();
        String rankSybol = user.getRank().getSymbol();

        FastBoard board = new FastBoard(p);
        board.updateTitle("");
        int i = 0;
        for (String s : LangValue.get(LangValue::scoreLines)) {
            if (i < LangValue.get(LangValue::scoreLines).size()) {
                board.updateLine(i, FancyText.colored(s.replace("%rank%", rankName).replace("%rank_symbol%", rankSybol).replace("%player%", p.getName()).replace("%tag%", user.getRole().getNameWithColor()).replace("%xp%", xp).replace("%kills%", kills).replace("%deaths%", mortes).replace("%playerstotal%", total).replace("%coins%", coins)));
                i++;
            }
        }
        scores.put(p, board);
    }

    public static void reloadScoreboard(Player p) {
        removeScoreboard(p);
        setScore(p);
    }


    public static void removeScoreboard(Player player) {
        FastBoard fastBoard = scores.get(player);
        if (fastBoard != null) {
            fastBoard.delete();
            scores.remove(player);
        }
    }


    Listener createInternalListener() {
        return new Listener() {

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                Player player = event.getPlayer();

                scores.remove(player);
            }

        };
    }


}
