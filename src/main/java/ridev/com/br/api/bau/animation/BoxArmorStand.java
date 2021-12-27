package ridev.com.br.api.bau.animation;

import lombok.Data;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.metadata.FixedMetadataValue;
import ridev.com.br.Main;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.text.FancyText;


@Data
@Getter
public class BoxArmorStand {
    private String name;
    private Location location;
    private ArmorStand armor;
    private boolean spawned;

    public BoxArmorStand(Location loc, String displayName) {
        this.location = loc;
        this.name = displayName;
        this.spawned = false;
    }


    public void spawn() {
        this.armor = this.location.getWorld().spawn(this.location, ArmorStand.class);
        armor.setCustomName(FancyText.colored(this.name));
        armor.setCustomNameVisible(true);
        armor.setMetadata("COMMONS_ARMOR", new FixedMetadataValue(Main.getInstance(), true));
        armor.setVisible(false);
        armor.setArms(false);
        armor.setGravity(false);
        armor.setSmall(true);
        armor.setBasePlate(false);
        armor.setCanPickupItems(false);
        armor.setHelmet(CacheSystem.getItem("mystery_present"));
        this.spawned = true;
    }

    public void remove() {
        this.armor.remove();
        this.spawned = false;
    }


}
