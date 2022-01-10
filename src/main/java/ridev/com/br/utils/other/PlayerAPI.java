package ridev.com.br.utils.other;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.apache.commons.io.IOUtils;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import ridev.com.br.Main;
import ridev.com.br.utils.text.FancyText;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerAPI {

    public static HashMap<Player, String> skinsDefault = new HashMap<>();

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

    public static String getUuid(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try {
            @SuppressWarnings("deprecation")
            String UUIDJson = IOUtils.toString(new URL(url));
            if (UUIDJson.isEmpty()) return "invalid name";
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return UUIDObject.get("id").toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return "error";
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


    public static void setDefaultSkin(Player p) {
        setSkinData(p, p.getName());
    }


    public static String getResponse(String _url) {
        try {
            URL url = new URL(_url);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            return IOUtils.toString(in, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void changeSkin(Player p, String skinURL) {
        GameProfile profile = ((CraftPlayer) p).getProfile();

        profile.getProperties().put("textures",
                new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + "http://textures.minecraft.net/texture/" + skinURL + "\"}}}")));
        for (Player all : Bukkit.getOnlinePlayers()) {

            p.hidePlayer(all);
        }

        new BukkitRunnable() {
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {

                    p.showPlayer(all);
                }
            }
        }.runTaskLater(Main.getInstance(), 5);
    }


    public static void setSkinData(Player p, String skinName) {
        String value = null;
        String signature = null;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(getResponse("https://api.mojang.com/users/profiles/minecraft/" + skinName));
            JSONObject json = (JSONObject) obj;
            Main.LOGGER.info("UUID PELO SITE: " + json.get("id"));
            Main.LOGGER.info("UUID PELO METHOD: " + p.getUniqueId());

            String uuid = (String) json.get("id");
            Object obj2 = parser.parse(getResponse("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false"));
            JSONObject json2 = (JSONObject) obj2;
            Object props = ((JSONArray) json2.get("properties")).get(0);
            JSONObject propsObj = (JSONObject) props;
            value = (String) propsObj.get("value");
            signature = (String) propsObj.get("signature");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl == p) continue;
            //REMOVES THE PLAYER
            ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) p).getHandle()));
            //CHANGES THE PLAYER'S GAME PROFILE
            GameProfile gp = ((CraftPlayer) p).getProfile();
            gp.getProperties().removeAll("textures");
            gp.getProperties().put("textures", new Property("textures", value, signature));
            //ADDS THE PLAYER

            new BukkitRunnable() {
                public void run() {
                    ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) p).getHandle()));
                    ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(p.getEntityId()));
                    ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer) p).getHandle()));
                }
            }.runTask(Main.getInstance());
        }
    }

    public static boolean setSkin(GameProfile profile, String username) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", UUIDTypeAdapter.fromString(getUuid(username)))).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                String skin = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];
                profile.getProperties().put("textures", new Property("textures", skin, signature));
                return true;
            } else {
                System.out.println("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
