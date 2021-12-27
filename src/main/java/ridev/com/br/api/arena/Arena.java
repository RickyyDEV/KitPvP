package ridev.com.br.api.arena;


import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ridev.com.br.Main;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.user.User;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.files.Files;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.Map;

@Data
public class Arena {

    private Location location;
    private final ArenaType type;


    public Arena(ArenaType type) {
        this.type = type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void save() {
        ArenaLibrary.getArenas().put(this.type, this);
    }

    public void remove() {
        ArenaLibrary.getArenas().remove(this.type);
    }

    @SneakyThrows
    public void saveInConfig() {
        Main.getLoc().set("arenas." + this.type.getName(), LocationAPI.serlialize(this.location));
        Files.saveLocConfig();
    }

    @SneakyThrows
    public void removeInConfig() {
        Main.getLoc().set("arenas." + this.type.getName(), null);
        Files.saveLocConfig();
    }


    public static void sendPlayer(User us, Kit kit) {
        us.getPlayer().getInventory().clear();
        us.getPlayer().teleport(ArenaLibrary.getRandomArena().getLocation());
        for (Map.Entry<Integer, ItemStack> itens : kit.itens().entrySet()) {
            us.getPlayer().getInventory().setItem(itens.getKey(), itens.getValue());
        }
        us.setWarp(WarpType.ARENA);
        Sound.NOTE_PLING.play(us.getPlayer(), 10, 10);
        String title = LangValue.get(LangValue::arenaTitle).replace("%kit%", kit.name()).replace("%player%", us.getUsername());
        String subtitle = LangValue.get(LangValue::arenaSubtitle).replace("%kit%", kit.name()).replace("%player%", us.getUsername());
        MineReflect.sendTitle(us.getPlayer(), FancyText.colored("&eViper"), FancyText.colored("&7Você Entrou na arena!"), 10, 10, 10);
        us.setKit(kit);

        Player p = us.getPlayer();
        p.setMaxHealth(20);
        p.setHealth(20);
        Protecao.setImortal(p, false);

    }
}
