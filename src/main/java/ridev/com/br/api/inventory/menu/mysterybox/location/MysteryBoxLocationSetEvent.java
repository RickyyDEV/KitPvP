package ridev.com.br.api.inventory.menu.mysterybox.location;

import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.api.bau.Box;
import ridev.com.br.api.builder.BuildAPI;
import ridev.com.br.Main;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MysteryBoxLocationSetEvent implements Listener {

    @Getter
    static List<Player> players = new ArrayList<>();

    @Getter
    static HashMap<Player, String> name = new HashMap<>();


    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (players.contains(p)) {
            if (!name.containsKey(p)) {
                e.setCancelled(true);
                new BukkitRunnable() {
                    public void run() {
                        name.put(p, e.getMessage());
                        p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Ótimo! Agora quebre o bloco para que eu possa seta-lo!"));
                        p.setGameMode(GameMode.CREATIVE);
                        BuildAPI.addBuilder(p);
                    }
                }.runTask(Main.getInstance());

            }
        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (players.contains(p)) {
            if (name.containsKey(p)) {
                e.setCancelled(true);
                if (e.getBlock().getType().equals(Material.CHEST)) {
                    Box bx = new Box(name.get(p), e.getBlock());
                    bx.createMysteryBox();
                    bx.saveInConfig();
                    Sound.NOTE_PLING.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7MysteryBox " + bx.getName() + " &7criada com sucesso!"));
                    p.setGameMode(GameMode.SURVIVAL);
                    BuildAPI.removeBuilder(p);
                    name.remove(p);
                    players.remove(p);
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7O bloco que você quebrou não era um báu! Tente novamente!"));
                }
            }
        }
    }
}
