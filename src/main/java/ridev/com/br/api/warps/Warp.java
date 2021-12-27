package ridev.com.br.api.warps;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Location;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.Main;
import ridev.com.br.utils.files.Files;

@Builder
@Data
@AllArgsConstructor
public class Warp {
    private WarpType type;
    private boolean isDuel;
    private boolean itensBeforeFall;
    private Location spawn;
    private Location firstSpawn;
    private Location secondSpawn;


    public Warp(WarpType type) {
        this.type = type;
        this.isDuel = type.isDuel();
        this.itensBeforeFall = type.setItensBeforeFall();

    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }


    public void save() {
        Warp warp = Warp.builder()
                .type(this.type)
                .isDuel(isDuel)
                .spawn(this.spawn)
                .firstSpawn(this.firstSpawn)
                .secondSpawn(this.secondSpawn)
                .itensBeforeFall(this.itensBeforeFall)
                .build();
        WarpLibrary.getWarps().put(this.type, warp);
    }

    public void remove() {
        WarpLibrary.getWarps().remove(this.type);
    }

    @SneakyThrows
    public void saveInConfig() {
        if (this.spawn != null) {
            Main.getLoc().set("warps." + this.type.getName() + ".spawn", LocationAPI.serlialize(this.spawn));
        }
        //           -----------------------------
        if (this.firstSpawn != null) {
            Main.getLoc().set("warps." + this.type.getName() + ".duel", LocationAPI.serlialize(this.firstSpawn));
        }
        if (this.secondSpawn != null) {
            // -------------------------------------------
            Main.getLoc().set("warps." + this.type.getName() + ".duel2", LocationAPI.serlialize(this.secondSpawn));
        }
        if (this.itensBeforeFall) {
            Main.getLoc().set("warps." + this.type.getName() + ".itensBeforeFall", this.itensBeforeFall);
        }
        Files.saveLocConfig();
    }

    @SneakyThrows
    public void removeInConfig() {
        Main.getLoc().set("warps." + this.type.getName().toLowerCase(), null);
        Files.saveLocConfig();

    }

    @SneakyThrows
    public void removeSpawnInConfig() {
        Main.getLoc().set("warps." + this.type.getName().toLowerCase() + ".spawn", null);
        Files.saveLocConfig();

    }

    @SneakyThrows
    public void removeFirstSpawnInConfig() {
        Main.getLoc().set("warps." + this.type.getName().toLowerCase() + ".duel1", null);
        Files.saveLocConfig();

    }

    @SneakyThrows
    public void removeSecondSpawnInConfig() {
        Main.getLoc().set("warps." + this.type.getName().toLowerCase() + ".duel2", null);
        Files.saveLocConfig();

    }
}
