package ridev.com.br.api.user;

import com.google.common.collect.Maps;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.cargos.GroupManager;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.kits.PvP;
import ridev.com.br.api.rank.RankType;
import ridev.com.br.api.rank.Ranks;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.sql.BackendLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {

    private final BackendLibrary backendLibrary;

    public static Map<String, User> map;


    public UserManager(Main plugin) {
        this.backendLibrary = new BackendLibrary(plugin);
        map = Maps.newConcurrentMap();
        Bukkit.getPluginManager().registerEvents(this.createInternalListener(), plugin);
    }

    Listener createInternalListener() {
        return new Listener() {

            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event) {
                Player player = event.getPlayer();

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        User user = BackendLibrary.select(player.getName());
                        if (user == null) {
                            Map<BoxType, Integer> box = new HashMap<>();
                            box.put(BoxType.BASICO, 0);
                            box.put(BoxType.MEDIANO, 0);
                            box.put(BoxType.RARO, 0);
                            box.put(BoxType.SUPREMO, 0);
                            box.put(BoxType.MASTER, 0);
                            List<Kit> kitlist = new ArrayList<>();
                            kitlist.add(new PvP());
                            user = new User(player.getName(), "basico=0;mediano=0;raro=0;supremo=0;master=0;", box, kitlist, null, RankType.UNRANKED, GroupManager.getPlayerGroup(player), 0, 0, 0, 0, WarpType.LOBBY);
                            BackendLibrary.insert(user);
                        }
                        Ranks.checkRankPlayer(user);
                        map.put(player.getName(), user);
                    }
                }.runTask(Main.getInstance());
            }


            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                Player player = event.getPlayer();
                User user = map.get(player.getName());
                new BukkitRunnable() {
                    public void run() {
                        if (user != null) {
                            BackendLibrary.insert(user);
                            map.remove(player.getName());
                        }
                    }
                }.runTaskLater(Main.getInstance(), 20);
            }

        };
    }

    public static List<User> getAllUsers() {
        List<User> lista = new ArrayList<>();
        for (String users : map.keySet()) {
            lista.add(map.get(users));
        }
        return lista;
    }

    public static User getPlayer(Player player) {
        return map.get(player.getName());
    }

    public static User getPlayer(String name) {
        return map.get(name);
    }

    public static User getOfflineUser(String p) {
        return BackendLibrary.select(p);
    }


    private static SQLExecutor executor() {
        return new SQLExecutor(Main.getSqlConnector());
    }
}
