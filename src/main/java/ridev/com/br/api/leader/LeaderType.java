package ridev.com.br.api.leader;

import org.bukkit.inventory.ItemStack;
import ridev.com.br.utils.other.CacheSystem;

public enum LeaderType {
    KILLS, XP;


    public ItemStack getHead() {
        if (this.equals(KILLS)) return CacheSystem.getItem("kill_leader");
        if (this.equals(XP)) return CacheSystem.getItem("xp_leader");
        return null;
    }

    public String getName() {
        if (this.equals(KILLS)) return "Kills";
        if (this.equals(XP)) return "XP";
        return null;
    }
}
