package ridev.com.br.api.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.Main;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.other.PlayerAPI;
import ridev.com.br.utils.text.FancyText;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ServerInventory extends SimpleInventory {


    public ServerInventory() {
        super("server.id", FancyText.colored("&aServidores"), 9 * 6);
        configuration(configuration -> {
            configuration.secondUpdate(1);
        });
    }

    public static void openInv(Player p) {
        inv.openInventory(p);
    }

    @SneakyThrows
    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem livro = InventoryItem.of(
                newMenuItem(Material.BOOKSHELF, "&6Lobby Principal", "&6Clique para entrar")).defaultCallback(a -> {
                    if (ConfigValue.get(ConfigValue::bungeeUse)) {
                        p.closeInventory();
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        DataOutputStream out = new DataOutputStream(b);
                        try {
                            out.writeUTF("Connect");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.writeUTF(ConfigValue.get(ConfigValue::lobbyPrincipal));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        p.sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());
                        p.sendMessage(FancyText.colored("&aConectando..."));
                    } else {
                        p.closeInventory();
                        p.sendMessage(FancyText.colored("&cVocê já está No Lobby Principal!"));
                    }
                }
        );

        InventoryItem diamante = InventoryItem.of(
                        newMenuItem(Material.DIAMOND, "&bContribua Com O Servidor!", " &7Você pode ajudar o Servidor", "&7 Tornando-se &fVIP &7e ", "&7 adquirindo pacotes de &fCoins&7!", "&r", "&7 Além de ajudar a manter", "&7 a rede online", "&7 você ainda terá acesso a vários &fbenefícios"))
                .defaultCallback(a -> {
                            p.closeInventory();
                            p.sendMessage(FancyText.colored("&r"));
                            PlayerAPI.openChatUrl(p, LangValue.get(LangValue::link), FancyText.colored("&bClique &b&lAQUI&b Para acessar nossa loja."));
                            p.sendMessage(FancyText.colored("&r"));
                        }
                );
        InventoryItem kitpvp = InventoryItem.of(
                        newMenuItem(Material.IRON_HELMET, "&e&lKITPVP", "&7Gosta de treinar o seu PvP?", "&7Esse é o lugar certo para isso!", "&7Interaja com novos jogadores e embarque", "&7Nessa Aventura Incerta!"))
                .defaultCallback(a -> {
                            p.closeInventory();
                            p.sendMessage(FancyText.colored("&cVocê já Este No KitPvP"));
                        }
                );
        InventoryItem gladiador = InventoryItem.of(
                        newMenuItem(Material.IRON_BARDING, "&e&lGLADIADOR", "&7Sinta-se Um Incrível gladiador da Antiguidade", "&7e destaque-se entre os demais gladiadores!", "&7Jogue Para Valer!"))
                .defaultCallback(a -> {
                            if (ConfigValue.get(ConfigValue::bungeeUse)) {
                                p.closeInventory();
                                ByteArrayOutputStream b = new ByteArrayOutputStream();
                                DataOutputStream out = new DataOutputStream(b);
                                try {
                                    out.writeUTF("Connect");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    out.writeUTF(ConfigValue.get(ConfigValue::Gladiador));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                p.sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());
                                p.sendMessage(FancyText.colored("&aConectando..."));
                            } else {
                                p.closeInventory();
                                p.sendMessage(FancyText.colored("&cEm Desenvolvimento!"));
                            }
                        }
                );
        InventoryItem hg = InventoryItem.of(
                        newMenuItem(Material.MUSHROOM_SOUP, "&e&lHG", "&7Pegue Cogumelos... Faça itens fortes...", "&7Use da sua estratégia para", "&7conseguir a vitória!"))
                .defaultCallback(a -> {
                            if (ConfigValue.get(ConfigValue::bungeeUse)) {
                                p.closeInventory();
                                ByteArrayOutputStream b = new ByteArrayOutputStream();
                                DataOutputStream out = new DataOutputStream(b);
                                try {
                                    out.writeUTF("Connect");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    out.writeUTF(ConfigValue.get(ConfigValue::HG));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                p.sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());
                                p.sendMessage(FancyText.colored("&aConectando..."));
                            } else {
                                p.closeInventory();
                                p.sendMessage(FancyText.colored("&cEm Desenvolvimento!"));
                            }
                        }
                );
        editor.setItem(12, livro);
        editor.setItem(14, diamante);
        editor.setItem(30, kitpvp);
        editor.setItem(31, gladiador);
        editor.setItem(32, hg);
    }


    public static ItemStack newMenuItem(Material ironFence, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }


    static ServerInventory inv = new ServerInventory().init();


}
