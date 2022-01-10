package ridev.com.br.api.warps;

import ridev.com.br.Main;
import ridev.com.br.api.world.LocationAPI;

public class WarpStarter {


    public WarpStarter(Main plugin) {
        loadAllWarps();

    }


    public void loadAllWarps() {
        if (Main.getLoc().get("warps") != null) {
            for (String s : Main.getLoc().getConfigurationSection("warps").getKeys(false)) {
                if (Main.getLoc().get("warps." + s) != null) {
                    WarpType type = WarpType.transform(s);
                    assert type != null;
                    Warp warp = new Warp(type);
                    if (Main.getLoc().getString("warps." + s + ".spawn") != null) {
                        warp.setSpawn(LocationAPI.deserialize(Main.getLoc().getString("warps." + s + ".spawn")));
                    }
                    if (Main.getLoc().getString("warps." + s + ".duel") != null)
                        warp.setFirstSpawn(LocationAPI.deserialize(Main.getLoc().getString("warps." + s + ".duel")));
                    if (Main.getLoc().getString("warps." + s + ".duel2") != null)
                        warp.setSecondSpawn(LocationAPI.deserialize(Main.getLoc().getString("warps." + s + ".duel2")));
                    if (Main.getLoc().get("warps." + s + ".itensBeforeFall") != null) {
                        warp.setItensBeforeFall(Main.getLoc().getBoolean("warps." + s + ".itensBeforeFall"));
                    }
                    warp.save();
                }
            }
        }
    }
}
