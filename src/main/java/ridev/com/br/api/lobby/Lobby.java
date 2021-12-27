package ridev.com.br.api.lobby;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.Main;
import ridev.com.br.utils.files.Files;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
public class Lobby {

    private final Location location;
    private HashMap<Integer, ItemStack> itens;

    public Lobby(Location loc) {
        this.location = loc;
    }

    public void save() {
        LobbyManager.getLobby().put(WarpType.LOBBY, this);
    }

    @SneakyThrows
    public void saveInConfig() {
        Main.getLoc().set("lobby.spawn", LocationAPI.serlialize(this.location));
        Files.saveLocConfig();
    }

    public void remove() {
        LobbyManager.getLobby().remove(WarpType.LOBBY);
    }

    @SneakyThrows
    public void removeInConfig() {
        Main.getLoc().set("lobby", null);
        Files.saveLocConfig();
    }

}
