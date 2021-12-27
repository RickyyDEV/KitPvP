package ridev.com.br.utils.other;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import ridev.com.br.Main;
import ridev.com.br.utils.text.FancyText;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerAPI {
    public static String getName(final Player jogador) {
        return jogador.getName();
    }

    public void respawn(final Player jogador, final int tempo) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> ((CraftPlayer) jogador).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN)), tempo);
    }

    public static void giveItem(final Player jogador, final ItemStack itemstack) {
        getPlayerInventory(jogador).addItem(itemstack);
    }

    public static void runChatCommand(final Player jogador, final String comando, final String mensagem) {
        final TextComponent message = new TextComponent(mensagem);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, comando));
        jogador.spigot().sendMessage(message);
    }

    public static void suggestChatCommand(final Player jogador, final String comando, final String mensagem) {
        final TextComponent message = new TextComponent(mensagem);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, comando));
        jogador.spigot().sendMessage(message);
    }

    public static void openChatFile(final Player jogador, final String arquivo, final String mensagem) {
        final TextComponent message = new TextComponent(mensagem);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, arquivo));
        jogador.spigot().sendMessage(message);
    }

    public static void openChatUrl(final Player jogador, final String url, final String mensagem) {
        final TextComponent message = new TextComponent(mensagem);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        jogador.spigot().sendMessage(message);
    }

    public static void setItem(final Player jogador, final int slot, final ItemStack itemstack) {
        getPlayerInventory(jogador).setItem(slot - 1, itemstack);
    }

    public static Inventory getPlayerInventory(final Player jogador) {
        return jogador.getInventory();
    }

    public static Inventory getInventory(final Inventory inventario) {
        createInventory(null, 27, "");
        return inventario;
    }

    public static Inventory createInventory(final InventoryHolder dono, final int tamanho, final String nome) {
        return getInventory(Bukkit.createInventory(dono, tamanho, FancyText.colored(nome)));
    }

    public static void setXp(final Player jogador, final float xp) {
        jogador.setExp(xp);
    }

    public static void setFood(final Player jogador, final int fome) {
        jogador.setFoodLevel(fome);
    }

    public static int getFood(final Player jogador) {
        return jogador.getFoodLevel();
    }

    public static float getXp(final Player jogador) {
        return jogador.getExp();
    }

    public static void addXp(final Player jogador, final int xp) {
        jogador.setExp(jogador.getExp() + xp);
    }

    public static void addOp(final Player jogador) {
        jogador.setOp(true);
    }

    public static void removeOp(final Player jogador) {
        jogador.setOp(false);
    }

    public static void killPlayer(final Player jogador) {
        jogador.setHealth(0.0);
    }

    public static double getHeal(final Player jogador) {
        return jogador.getHealth();
    }

    public static Player getPlayer(final Player jogador) {
        return jogador.getPlayer();
    }

    public static InetSocketAddress getAddress(final Player jogador) {
        return jogador.getAddress();
    }

    public static void enableFly(final Player jogador) {
        jogador.setAllowFlight(true);
    }

    public static void disableFly(final Player jogador) {
        jogador.setAllowFlight(false);
    }

    public static boolean getFly(final Player jogador) {
        return jogador.getAllowFlight();
    }

    public static UUID getUUID(final Player jogador) {
        return jogador.getUniqueId();
    }

    public static void message(final Player jogador, final String mensagem) {
        jogador.sendMessage(mensagem);
    }

    public static void setGamemode(final Player jogador, final GameMode modo) {
        jogador.setGameMode(modo);
    }

    public static GameMode getGamemode(final Player alvo) {
        return alvo.getGameMode();
    }

    public static Inventory openInventory(final Player jogador, final Inventory inventario) {
        jogador.openInventory(inventario);
        return inventario;
    }

    public static void putArmor(final Player jogador, final ItemStack[] itemstack) {
        jogador.getInventory().setArmorContents(itemstack);
    }

    public static Inventory getEnderchest(final Player jogador) {
        return jogador.getEnderChest();
    }

    public static void openEnchant(final Player jogador) {
        jogador.openEnchanting(jogador.getLocation(), true);
    }

    public static InventoryType getEnchantType(final Player jogador) {
        return jogador.openEnchanting(jogador.getLocation(), true).getType();
    }

    public static ItemStack getHead(final Player jogador) {
        final ItemStack kCabeca = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta mCabeca = (SkullMeta) kCabeca.getItemMeta();
        mCabeca.setOwner(jogador.getName());
        mCabeca.setDisplayName(jogador.getName());
        kCabeca.setItemMeta(mCabeca);
        return kCabeca;
    }

    public static ItemStack getHead(final String jogador) {
        final ItemStack kCabeca = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta mCabeca = (SkullMeta) kCabeca.getItemMeta();
        mCabeca.setOwner(jogador);
        mCabeca.setDisplayName(jogador);
        kCabeca.setItemMeta(mCabeca);
        return kCabeca;
    }

    public static ItemStack getSkull(String link) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures",
                new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + "http://textures.minecraft.net/texture/" + link + "\"}}}")));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            Main.LOGGER.log(Level.SEVERE, "Erro ao tentar pegar Skull!", ex);
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static void visible(final Player jogador, final boolean bool) {
        if (bool) {
            jogador.showPlayer(jogador);
        } else {
            jogador.hidePlayer(jogador);
        }
    }

    public static String getPlayerString(final Player jogador) {
        return jogador.getName();
    }
}
