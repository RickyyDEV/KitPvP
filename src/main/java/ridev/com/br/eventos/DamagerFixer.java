package ridev.com.br.eventos;

import org.bukkit.event.entity.*;
import org.bukkit.potion.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import java.util.*;

public class DamagerFixer implements Listener
{
    private static int[] $SWITCH_TABLE$org$bukkit$entity$EntityType;
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageEvent(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            final Player p = (Player)event.getDamager();
            final ItemStack sword = p.getItemInHand();
            double damage = event.getDamage();
            final double danoEspada = this.getDamage(sword.getType());
            final boolean isMore = damage > 2.0;
            if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                for (final PotionEffect effect : p.getActivePotionEffects()) {
                    if (effect.getType().equals((Object)PotionEffectType.INCREASE_DAMAGE)) {
                        double minus;
                        if (this.isCrital(p)) {
                            minus = (danoEspada + danoEspada / 2.0) * 1.3 * (effect.getAmplifier() + 1);
                        }
                        else {
                            minus = danoEspada * 1.3 * (effect.getAmplifier() + 1);
                        }
                        damage -= minus;
                        damage += 2 * (effect.getAmplifier() + 1);
                        break;
                    }
                }
            }
            if (!sword.getEnchantments().isEmpty()) {
                if (sword.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS) && this.isArthropod(event.getEntityType())) {
                    damage -= 1.5 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
                    damage += sword.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
                }
                if (sword.containsEnchantment(Enchantment.DAMAGE_UNDEAD) && this.isUndead(event.getEntityType())) {
                    damage -= 1.5 * sword.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
                    damage += sword.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
                }
                if (sword.containsEnchantment(Enchantment.DAMAGE_ALL)) {
                    damage -= 1.25 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                    damage += sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                }
            }
            if (this.isCrital(p)) {
                damage -= danoEspada / 2.0;
                ++damage;
            }
            if (isMore) {
                damage -= 2.0;
            }
            event.setDamage(damage);
        }
    }
    
    private boolean isCrital(final Player p) {
        return p.getFallDistance() > 0.0f && !p.isOnGround() && !p.hasPotionEffect(PotionEffectType.BLINDNESS);
    }
    
    private boolean isArthropod(final EntityType type) {
        switch ($SWITCH_TABLE$org$bukkit$entity$EntityType()[type.ordinal()]) {
            case 3:
            case 23:
            case 24: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean isUndead(final EntityType type) {
        switch ($SWITCH_TABLE$org$bukkit$entity$EntityType()[type.ordinal()]) {
            case 12:
            case 17:
            case 37:
            case 54: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private double getDamage(final Material type) {
        double damage = 1.0;
        if (type.toString().contains("DIAMOND_")) {
            damage = 8.0;
        }
        else if (type.toString().contains("IRON_")) {
            damage = 7.0;
        }
        else if (type.toString().contains("STONE_")) {
            damage = 6.0;
        }
        else if (type.toString().contains("WOOD_")) {
            damage = 5.0;
        }
        else if (type.toString().contains("GOLD_")) {
            damage = 5.0;
        }
        if (!type.toString().contains("_SWORD")) {
            --damage;
            if (!type.toString().contains("_AXE")) {
                --damage;
                if (!type.toString().contains("_PICKAXE")) {
                    --damage;
                    if (!type.toString().contains("_SPADE")) {
                        damage = 1.0;
                    }
                }
            }
        }
        return damage;
    }
    
    static int[] $SWITCH_TABLE$org$bukkit$entity$EntityType() {
        final int[] var10000 = DamagerFixer.$SWITCH_TABLE$org$bukkit$entity$EntityType;
        if (var10000 != null) {
            return var10000;
        }
        final int[] var10001 = new int[EntityType.values().length];
        try {
            var10001[EntityType.ARROW.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            var10001[EntityType.BAT.ordinal()] = 40;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            var10001[EntityType.BLAZE.ordinal()] = 36;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            var10001[EntityType.BOAT.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            var10001[EntityType.CAVE_SPIDER.ordinal()] = 34;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            var10001[EntityType.CHICKEN.ordinal()] = 45;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            var10001[EntityType.COMPLEX_PART.ordinal()] = 61;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            var10001[EntityType.COW.ordinal()] = 44;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            var10001[EntityType.CREEPER.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            var10001[EntityType.DROPPED_ITEM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            var10001[EntityType.EGG.ordinal()] = 56;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            var10001[EntityType.ENDERMAN.ordinal()] = 33;
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        try {
            var10001[EntityType.ENDER_CRYSTAL.ordinal()] = 54;
        }
        catch (NoSuchFieldError noSuchFieldError13) {}
        try {
            var10001[EntityType.ENDER_DRAGON.ordinal()] = 38;
        }
        catch (NoSuchFieldError noSuchFieldError14) {}
        try {
            var10001[EntityType.ENDER_PEARL.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError15) {}
        try {
            var10001[EntityType.ENDER_SIGNAL.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError16) {}
        try {
            var10001[EntityType.EXPERIENCE_ORB.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError17) {}
        try {
            var10001[EntityType.FALLING_BLOCK.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError18) {}
        try {
            var10001[EntityType.FIREBALL.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError19) {}
        try {
            var10001[EntityType.FIREWORK.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError20) {}
        try {
            var10001[EntityType.FISHING_HOOK.ordinal()] = 57;
        }
        catch (NoSuchFieldError noSuchFieldError21) {}
        try {
            var10001[EntityType.GHAST.ordinal()] = 31;
        }
        catch (NoSuchFieldError noSuchFieldError22) {}
        try {
            var10001[EntityType.GIANT.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError23) {}
        try {
            var10001[EntityType.HORSE.ordinal()] = 52;
        }
        catch (NoSuchFieldError noSuchFieldError24) {}
        try {
            var10001[EntityType.IRON_GOLEM.ordinal()] = 51;
        }
        catch (NoSuchFieldError noSuchFieldError25) {}
        try {
            var10001[EntityType.ITEM_FRAME.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError26) {}
        try {
            var10001[EntityType.LEASH_HITCH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError27) {}
        try {
            var10001[EntityType.LIGHTNING.ordinal()] = 58;
        }
        catch (NoSuchFieldError noSuchFieldError28) {}
        try {
            var10001[EntityType.MAGMA_CUBE.ordinal()] = 37;
        }
        catch (NoSuchFieldError noSuchFieldError29) {}
        try {
            var10001[EntityType.MINECART.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError30) {}
        try {
            var10001[EntityType.MINECART_CHEST.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError31) {}
        try {
            var10001[EntityType.MINECART_COMMAND.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError32) {}
        try {
            var10001[EntityType.MINECART_FURNACE.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError33) {}
        try {
            var10001[EntityType.MINECART_HOPPER.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError34) {}
        try {
            var10001[EntityType.MINECART_MOB_SPAWNER.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError35) {}
        try {
            var10001[EntityType.MINECART_TNT.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError36) {}
        try {
            var10001[EntityType.MUSHROOM_COW.ordinal()] = 48;
        }
        catch (NoSuchFieldError noSuchFieldError37) {}
        try {
            var10001[EntityType.OCELOT.ordinal()] = 50;
        }
        catch (NoSuchFieldError noSuchFieldError38) {}
        try {
            var10001[EntityType.PAINTING.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError39) {}
        try {
            var10001[EntityType.PIG.ordinal()] = 42;
        }
        catch (NoSuchFieldError noSuchFieldError40) {}
        try {
            var10001[EntityType.PIG_ZOMBIE.ordinal()] = 32;
        }
        catch (NoSuchFieldError noSuchFieldError41) {}
        try {
            var10001[EntityType.PLAYER.ordinal()] = 60;
        }
        catch (NoSuchFieldError noSuchFieldError42) {}
        try {
            var10001[EntityType.PRIMED_TNT.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError43) {}
        try {
            var10001[EntityType.SHEEP.ordinal()] = 43;
        }
        catch (NoSuchFieldError noSuchFieldError44) {}
        try {
            var10001[EntityType.SILVERFISH.ordinal()] = 35;
        }
        catch (NoSuchFieldError noSuchFieldError45) {}
        try {
            var10001[EntityType.SKELETON.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError46) {}
        try {
            var10001[EntityType.SLIME.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError47) {}
        try {
            var10001[EntityType.SMALL_FIREBALL.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError48) {}
        try {
            var10001[EntityType.SNOWBALL.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError49) {}
        try {
            var10001[EntityType.SNOWMAN.ordinal()] = 49;
        }
        catch (NoSuchFieldError noSuchFieldError50) {}
        try {
            var10001[EntityType.SPIDER.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError51) {}
        try {
            var10001[EntityType.SPLASH_POTION.ordinal()] = 55;
        }
        catch (NoSuchFieldError noSuchFieldError52) {}
        try {
            var10001[EntityType.SQUID.ordinal()] = 46;
        }
        catch (NoSuchFieldError noSuchFieldError53) {}
        try {
            var10001[EntityType.THROWN_EXP_BOTTLE.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError54) {}
        try {
            var10001[EntityType.UNKNOWN.ordinal()] = 62;
        }
        catch (NoSuchFieldError noSuchFieldError55) {}
        try {
            var10001[EntityType.VILLAGER.ordinal()] = 53;
        }
        catch (NoSuchFieldError noSuchFieldError56) {}
        try {
            var10001[EntityType.WEATHER.ordinal()] = 59;
        }
        catch (NoSuchFieldError noSuchFieldError57) {}
        try {
            var10001[EntityType.WITCH.ordinal()] = 41;
        }
        catch (NoSuchFieldError noSuchFieldError58) {}
        try {
            var10001[EntityType.WITHER.ordinal()] = 39;
        }
        catch (NoSuchFieldError noSuchFieldError59) {}
        try {
            var10001[EntityType.WITHER_SKULL.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError60) {}
        try {
            var10001[EntityType.WOLF.ordinal()] = 47;
        }
        catch (NoSuchFieldError noSuchFieldError61) {}
        try {
            var10001[EntityType.ZOMBIE.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError62) {}
        return DamagerFixer.$SWITCH_TABLE$org$bukkit$entity$EntityType = var10001;
    }
    
    @EventHandler
    public void entityDamagePedra(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getDamager() != null) {
            final Player p = (Player)e.getDamager();
            if (e.getEntity() instanceof Player && p.getItemInHand().getType() == Material.STONE_SWORD) {
                e.setDamage(e.getDamage() * 0.6);
                return;
            }
            if (p.getItemInHand().getType() == Material.WOOD_SWORD) {
                e.setDamage(e.getDamage() * 0.5);
            }
        }
    }
    
    @EventHandler
    public void noBreaking(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.FISHING_ROD && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            p.getItemInHand().setDurability((short)0);
            p.updateInventory();
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void NerfSwords(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            final Player player = (Player)event.getDamager();
            if (event.getDamage() > 1.0) {
                event.setDamage(event.getDamage() - 1.0);
            }
            if (event.getDamager() instanceof Player) {
                if (player.getFallDistance() > 0.0f && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                    final int NewDamage = (int)(event.getDamage() * 1.5) - (int)event.getDamage();
                    if (event.getDamage() > 1.0) {
                        event.setDamage(event.getDamage() - NewDamage);
                    }
                }
                if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                    for (final PotionEffect Effect : player.getActivePotionEffects()) {
                        if (Effect.getType().equals((Object)PotionEffectType.INCREASE_DAMAGE)) {
                            final double Division = (Effect.getAmplifier() + 2) * 1.3 + 1.0;
                            int NewDamage2;
                            if (event.getDamage() / Division <= 1.0) {
                                NewDamage2 = (Effect.getAmplifier() + 2) * 3 + 3;
                            }
                            else {
                                NewDamage2 = (int)(event.getDamage() / Division);
                            }
                            event.setDamage((double)NewDamage2);
                            break;
                        }
                    }
                }
                if (player.getItemInHand().getType() == Material.AIR) {
                    event.setDamage(0.5);
                }
                if (player.getItemInHand().getType() == Material.WOOD_SWORD) {
                    event.setDamage(2.5);
                }
                if (player.getItemInHand().getType() == Material.STONE_SWORD) {
                    event.setDamage(3.5);
                }
                if (player.getItemInHand().getType() == Material.GOLD_SWORD) {
                    event.setDamage(4.5);
                }
                if (player.getItemInHand().getType() == Material.IRON_SWORD) {
                    event.setDamage(4.5);
                }
                if (player.getItemInHand().getType() == Material.DIAMOND_SWORD) {
                    event.setDamage(5.5);
                }
                if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                    for (final PotionEffect Effect : player.getActivePotionEffects()) {
                        if (Effect.getType().equals((Object)PotionEffectType.INCREASE_DAMAGE) && player.getItemInHand() != null && player.getItemInHand().getType().name().contains("SWORD")) {
                            final Random r = new Random();
                            if (r.nextInt(3) == 0) {
                                event.setDamage(event.getDamage() + 2.0);
                                break;
                            }
                            event.setDamage(event.getDamage() + 1.5);
                        }
                    }
                }
                if (player.getFallDistance() > 0.0f && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                    if (player.getItemInHand().getType() == Material.AIR) {
                        event.setDamage(0.5);
                    }
                    if (player.getItemInHand().getType() == Material.WOOD_SWORD) {
                        event.setDamage(event.getDamage() + 1.0);
                    }
                    if (player.getItemInHand().getType() == Material.STONE_SWORD) {
                        event.setDamage(event.getDamage() + 1.0);
                    }
                    if (player.getItemInHand().getType() == Material.GOLD_SWORD) {
                        event.setDamage(event.getDamage() + 1.5);
                    }
                    if (player.getItemInHand().getType() == Material.IRON_SWORD) {
                        event.setDamage(event.getDamage() + 1.0);
                    }
                    if (player.getItemInHand().getType() == Material.DIAMOND_SWORD) {
                        event.setDamage(event.getDamage() + 1.0);
                    }
                }
            }
        }
    }
}
