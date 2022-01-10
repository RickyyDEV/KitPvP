package ridev.com.br.api.bau.animation;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import ridev.com.br.Main;
import ridev.com.br.api.bau.Box;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.bau.recompense.BoxRecompense;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.hologram.CustomHologram;
import ridev.com.br.utils.other.ParticleEffect;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;

public class BoxAnimation {


    private static final HashMap<Player, Box> openingboxes = new HashMap<>();


    public static void startAnimation(Box caixa, BoxType type, Player p) {
        openingboxes.put(p, caixa);
        caixa.setUsing(true);
        Hologram hl = caixa.getHolograma();
        CustomHologram cs = new CustomHologram(hl);
        cs.updateTextLine(0, "");
        cs.updateTextLine(1, "");
        User us = UserManager.getPlayer(p);
        us.removeBox(type);
        Location armorLoc = caixa.getLocation().clone().add(0.5, 1.5, 0.5);
        playChestAction(caixa.getBlock(), true);
        BoxArmorStand armor = new BoxArmorStand(armorLoc, "&kasjdkoashdjiashda");
        armor.spawn();
        ArmorStand s = armor.getArmor();
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                i++;

                if (i < 15 && !s.isDead()) {
                    ParticleEffect.CRIT_MAGIC.display(0.0f, 0.0f, 0.0f, 0.5f, 5,
                            s.getEyeLocation(), 16);
                    s.teleport(new Location(s.getWorld(), s.getLocation().clone().getX(),
                            s.getLocation().getY() - 0.03, s.getLocation().clone().getZ(),
                            s.getLocation().clone().getYaw() + 25.0F, s.getLocation().clone().getPitch()));
                    Sound.NOTE_PLING.play(s.getWorld(), s.getEyeLocation().clone(), 10, 10);
                }
                if (i > 15 && i < 100 && !s.isDead()) {
                    Sound.NOTE_PLING.play(s.getWorld(), s.getEyeLocation().clone(), 10, 10);
                    ParticleEffect.LAVA.display(0.0f, 0.0f, 0.0f, 0.5f, 1,
                            s.getEyeLocation().clone(), 16);
                    s.teleport(new Location(s.getWorld(), s.getLocation().clone().getX(),
                            s.getLocation().clone().getY() - 0.04, s.getLocation().clone().getZ(),
                            s.getLocation().clone().getYaw() + 25.0F, s.getLocation().getPitch()));
                }
                if (s.getLocation().getY() < caixa.getLocation().clone().getY() - 0.8) {
                    i = 101;
                    s.remove();
                    playChestAction(caixa.getBlock(), false);
                    ParticleEffect.EXPLOSION_LARGE.display(1.2f, 0.5f, 1.2f, 0.0f, 10, caixa.getLocation(), 16);
                    Sound.EAT.play(p, 5, 5);
                    new BukkitRunnable() {
                        public void run() {
                            ParticleEffect.EXPLOSION_LARGE.display(1.2f, 0.5f, 1.2f, 0.0f, 10, caixa.getLocation().clone().add(0.3, 0.2, 0.0), 16);
                            Sound.EAT.play(p, 5, 5);
                        }
                    }.runTaskLater(Main.getInstance(), 20);
                    new BukkitRunnable() {
                        public void run() {
                            ParticleEffect.EXPLOSION_LARGE.display(1.2f, 0.5f, 1.2f, 0.0f, 10, caixa.getLocation().clone().add(0.0, 0.2, 0.3), 16);
                            Sound.EAT.play(p, 5, 5);
                        }
                    }.runTaskLater(Main.getInstance(), 30);
                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            cs.updateTextLine(1, FancyText.colored("&aRecompensa"));
                            cs.updateTextLine(0, BoxRecompense.darRecompensa(type, us));
                            ParticleEffect.EXPLOSION_LARGE.display(1.2f, 0.5f, 1.2f, 0.0f, 10, s.getLocation(), 16);
                            Sound.EXPLODE.play(p, 5, 5);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    cs.updateTextLine(1, FancyText.colored("&bCaixa Misteriosa"));
                                    cs.updateTextLine(0, FancyText.colored("&eClique para abrir"));
                                    caixa.setHolograma(hl);
                                    caixa.setUsing(false);
                                    this.cancel();
                                }
                            }.runTaskLater(Main.getInstance(), 50);
                            this.cancel();
                        }
                    }.runTaskLater(Main.getInstance(), 40);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);


    }

    private static void playChestAction(Block chest, boolean open) {
        Location location = chest.getLocation();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
        TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
        world.playBlockAction(position, tileChest.w(), 1, open ? 1 : 0);
    }


}
