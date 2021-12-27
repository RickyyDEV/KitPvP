package ridev.com.br.api.model.gladiator;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GladiatorModel {
    private final String gladID;
    private final Player player1;
    private final Player player2;

    public GladiatorModel(String gladId, Player player1, Player player2) {
        this.gladID = gladId;
        this.player1 = player1;
        this.player2 = player2;
    }


    public void createJaule() {


    }

    private static List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<Block>();
        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));
        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));
        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));
        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

//    private static List<Location> locationsFromTwoPoints(Location loc1, Location loc2) {
//        List<Block> blocks = new ArrayList<Block>();
//        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
//        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));
//        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
//        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));
//        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
//        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));
//        for (int x = bottomBlockX; x <= topBlockX; x++) {
//            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
//                for (int y = bottomBlockY; y <= topBlockY; y++) {
//                    Block block = loc1.getWorld().getBlockAt(x, y, z);
//                    blocks.add(block);
//                }
//            }
//        }
//        return blocks;
//    }

//    public boolean inRegion(Location l) {
//        if (!l.getWorld().equals(p1.world))
//            return false;
//        return ((l.getX() >= p1.x && l.getX() <= p2.x) &&
//                (l.getZ() >= p1.z && l.getZ() <= p2.z) &&
//                (l.getY() >= p1.y && l.getY() <= p2.y));
//    }
}
