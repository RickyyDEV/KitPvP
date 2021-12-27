package ridev.com.br.eventos;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import ridev.com.br.utils.apis.MineReflect;

import java.lang.reflect.Constructor;

public class KnockbackListener implements Listener {

    private Constructor<?> packetVelocity;

    public double getHorMultiplier() {
        return 1.1D;
    }

    public double getVerMultiplier() {
        return 1.1D;
    }

    public KnockbackListener() {
        try {
            Class<?> packetVelocityClass = PacketPlayOutEntityVelocity.class;

            this.packetVelocity = packetVelocityClass
                    .getConstructor(Integer.TYPE, Double.TYPE, Double.TYPE, Double.TYPE);
        } catch (SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerVelocity(PlayerVelocityEvent event) {
        Player player = event.getPlayer();
        EntityDamageEvent lastDamage = player.getLastDamageCause();
        if ((!(lastDamage instanceof EntityDamageByEntityEvent))) {
            return;
        }
        if (((EntityDamageByEntityEvent) lastDamage).getDamager() instanceof Player) {
            event.setCancelled(true);
        }
        
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (damaged.getNoDamageTicks() > damaged.getMaximumNoDamageTicks() / 2D) {
            return;
        }

        double horMultiplier = getHorMultiplier();
        double verMultiplier = getVerMultiplier();
        double sprintMultiplier = damager.isSprinting() ? 0.8D : 0.5D;
        double kbMultiplier = damager.getItemInHand() == null ? 0
                : damager.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK) * 0.2D;
        @SuppressWarnings("deprecation")
        double airMultiplier = damaged.isOnGround() ? 1 : 0.5;

        Vector knockback = damaged.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();
        knockback.setX((knockback.getX() * sprintMultiplier + kbMultiplier) * horMultiplier);
        knockback.setY(0.35D * airMultiplier * verMultiplier);
        knockback.setZ((knockback.getZ() * sprintMultiplier + kbMultiplier) * horMultiplier);

        try {
            Object entityPlayer = damaged.getClass().getMethod("getHandle").invoke(damaged);
            Object packet = this.packetVelocity.newInstance(damaged.getEntityId(), knockback.getX(), knockback.getY(),
                    knockback.getZ());
            MineReflect.sendPacket(packet, damaged);
//            this.sendPacket.invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
