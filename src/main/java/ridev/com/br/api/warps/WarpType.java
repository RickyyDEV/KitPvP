package ridev.com.br.api.warps;

import org.bukkit.Material;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;

public enum WarpType {
    ONEVSONE, SUMO, FPS, LAVA, LOBBY, ARENA;


    public String getName() {
        if (this.equals(ONEVSONE)) return "1v1";
        if (this.equals(SUMO)) return "Sumo";
        if (this.equals(FPS)) return "FPS";
        if (this.equals(LAVA)) return "Lava";
        if (this.equals(LOBBY)) return "Lobby";
        return null;
    }

    public Material getItem() {
        if (this.equals(ONEVSONE)) return Material.BLAZE_ROD;
        if (this.equals(SUMO)) return Material.GOLDEN_APPLE;
        if (this.equals(FPS)) return Material.STONE_SWORD;
        if (this.equals(LAVA)) return Material.LAVA_BUCKET;
        if (this.equals(LOBBY)) return Material.BOOKSHELF;
        return null;

    }

    public static WarpType transform(String type) {
        if (type.equalsIgnoreCase("1v1")) return ONEVSONE;
        if (type.equalsIgnoreCase("sumo")) return SUMO;
        if (type.equalsIgnoreCase("fps")) return FPS;
        if (type.equalsIgnoreCase("lava")) return LAVA;
        return null;
    }

    public boolean isDuel() {
        if (this.equals(SUMO) || this.equals(ONEVSONE)) return true;
        return !this.equals(FPS) && !this.equals(LAVA);
    }

    public boolean setItensBeforeFall() {
        if (this.equals(SUMO)) return false;
        if (this.equals(FPS)) return true;
        if (this.equals(LAVA)) return false;
        if (this.equals(ONEVSONE)) return false;
        return false;
    }

    public int getCount() {
        int i = 0;
        for (User us : UserManager.getAllUsers()) {
            if (us.getWarp().equals(this)) i++;
        }
        return i;
    }

}
