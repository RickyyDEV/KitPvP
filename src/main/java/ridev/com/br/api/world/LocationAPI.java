package ridev.com.br.api.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationAPI {


    public static String serlialize(Location location) {
        return location.getWorld().getName() + ";" +
                location.getX() + ";" +
                location.getY() + ";" +
                location.getZ() + ";" +
                location.getYaw() + ";" +
                location.getPitch();
    }


    public static Location deserialize(String location) {
        String[] string = location.split(";");
        World world = Bukkit.getWorld(string[0]);
        double X = Double.parseDouble(string[1]);
        double Y = Double.parseDouble(string[2]);
        double Z = Double.parseDouble(string[3]);
        float Yaw = Float.parseFloat(string[4]);
        float Pitch = Float.parseFloat(string[5]);
        return new Location(world, X, Y, Z, Yaw, Pitch);
    }


}
