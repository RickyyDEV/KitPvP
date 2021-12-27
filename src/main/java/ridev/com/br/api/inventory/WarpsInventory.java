package ridev.com.br.api.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.Warp;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.api.warps.fps.FPSItem;
import ridev.com.br.api.warps.onevsone.OnevsOneItens;
import ridev.com.br.api.warps.sumo.SumoItens;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class WarpsInventory extends SimpleInventory {
    public WarpsInventory() {
        super("warp.inv", FancyText.colored("&aWarps"), 9 * 5);
    }

    public static void openInv(Player p) {
        inv.openInventory(p);
    }

    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        InventoryItem fps = InventoryItem.of(
                        newMenuItem(Material.GLASS, "&a&lFPS", "&r", "&7Quer Treinar Seu PvP Sem Perder Fps?", "&r", "&7Jogue em uma arena com o", "&7melhor fps possível.", "&r", "&a⋄ &7Jogadores: &a" + WarpType.FPS.getCount()))
                .defaultCallback(a -> {
                            if (WarpLibrary.getWarp(WarpType.FPS) != null) {
                                us.setWarp(WarpType.FPS);
                                Warp fpswarp = WarpLibrary.getWarp(WarpType.FPS);
                                p.teleport(fpswarp.getSpawn());
                                p.setMaxHealth(20);
                                p.setHealth(20);
                                if (fpswarp.isItensBeforeFall()) {
                                    p.getInventory().clear();
                                } else {
                                    Protecao.setImortal(p, false);
                                    FPSItem.setItens(p);
                                }
                                MineReflect.sendTitle(p, 30, 30, 30, FancyText.colored(LangValue.get(LangValue::fpsTitle)), FancyText.colored(LangValue.get(LangValue::fpsSubtitle)));
                                p.playSound(p.getLocation(), Sound.LEVEL_UP, 10F, 0F);
                            } else {
                                if (p.isOp()) {
                                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &cA Warp FPS não foi setada corretamente!"));
                                } else {
                                    p.sendMessage(FancyText.colored("&b&lFPS &8➸ &cNão foi possivel entrar nesta warp! &7Tente novamente mais tarde!"));
                                }
                            }
                        }
                );
        InventoryItem _1v1 = InventoryItem.of(
                        newMenuItem(Material.BLAZE_ROD, "&a&l1v1", "&r", "&7Treine e se divirta com seus amigos", "&7ou com pessoas aleatorias!", "&7Então a 1v1 foi feita", "&7Para você!", "&r", "&a⋄ &7Jogadores: &a" + WarpType.ONEVSONE.getCount()))
                .defaultCallback(a -> {
                            if (WarpLibrary.getWarp(WarpType.ONEVSONE).getSpawn() != null && WarpLibrary.getWarp(WarpType.ONEVSONE).getFirstSpawn() != null && WarpLibrary.getWarp(WarpType.ONEVSONE).getSecondSpawn() != null) {

                                p.teleport(WarpLibrary.getWarp(WarpType.ONEVSONE).getSpawn());
                                us.setWarp(WarpType.ONEVSONE);
                                MineReflect.sendTitle(p, 30, 30, 30, FancyText.colored(LangValue.get(LangValue::onevsoneTitle)), FancyText.colored(LangValue.get(LangValue::onevsoneSubtitle)));
                                p.playSound(p.getLocation(), Sound.LEVEL_UP, 10F, 0F);
                                OnevsOneItens.setIntens(p);
                            } else {
                                if (p.isOp()) {
                                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &cA Warp 1v1 não foi setada corretamente!"));
                                } else {
                                    p.sendMessage(FancyText.colored("&b&l1v1 &8➸ &cNão foi possivel entrar nesta warp! &7Tente novamente mais tarde!"));
                                }
                            }
                        }
                );
        InventoryItem sumo = InventoryItem.of(
                        newMenuItem(Material.APPLE, "&a&lSUMO", "&7Diante De um inimigo aleatório", "&7Seu objetivo é derruba-lo e garantir", "&7O Máximo de vitórias possíveis", "&r", "&a⋄ &7Jogadores: &a" + WarpType.SUMO.getCount()))
                .defaultCallback(a -> {
                            if (WarpLibrary.getWarp(WarpType.SUMO).getSpawn() != null && WarpLibrary.getWarp(WarpType.SUMO).getFirstSpawn() != null && WarpLibrary.getWarp(WarpType.SUMO).getSecondSpawn() != null) {
                                Location sumoloc = WarpLibrary.getWarp(WarpType.SUMO).getSpawn();
                                us.setWarp(WarpType.SUMO);
                                p.teleport(sumoloc);
                                SumoItens.setIntens(p);
                                MineReflect.sendTitle(p, 30, 30, 30, FancyText.colored(LangValue.get(LangValue::sumoTitle)), FancyText.colored(LangValue.get(LangValue::sumoSubtitle)));
                                p.playSound(p.getLocation(), Sound.LEVEL_UP, 10F, 0F);
                            } else {
                                if (p.isOp()) {
                                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &cA Warp sumô não foi setada corretamente!"));
                                } else {
                                    p.sendMessage(FancyText.colored("&b&lSUMO &8➸ &cNão foi possivel entrar nesta warp! &7Tente novamente mais tarde!"));
                                }
                            }
                        }
                );
        InventoryItem lava = InventoryItem.of(
                        newMenuItem(Material.LAVA_BUCKET, "&a&lLAVA", "&r", "&7Treine sua sopa e seu recraft", "&7ao mesmo tempo!", "&7E de quebra", "&7bata records de tempo vivo!", "&7Tem algo melhor que isso?", "&r", "&a⋄ &7Jogadores: &a" + WarpType.LAVA.getCount()))
                .defaultCallback(a -> {
                            if (WarpLibrary.getWarp(WarpType.LAVA) != null) {
                                Location lavaloc = WarpLibrary.getWarp(WarpType.LAVA).getSpawn();
                                us.setWarp(WarpType.LAVA);
                                p.setMaxHealth(20);
                                p.setHealth(20);
                                p.teleport(lavaloc);
                                Protecao.setImortal(p, false);
                                MineReflect.sendTitle(p, 30, 30, 30, FancyText.colored(LangValue.get(LangValue::lavaTitle)), FancyText.colored(LangValue.get(LangValue::lavaSubtitle)));
                                p.playSound(p.getLocation(), Sound.LEVEL_UP, 10F, 0F);
                            } else {
                                if (p.isOp()) {
                                    p.sendMessage(FancyText.colored("&eKitPvP &8➸ &cA Warp lava não foi setada corretamente!"));
                                } else {
                                    p.sendMessage(FancyText.colored("&b&lLAVA &8➸ &cNão foi possivel entrar nesta warp! &7Tente novamente mais tarde!"));
                                }
                            }
                        }
                );
        editor.setItem(13, fps);
        editor.setItem(31, _1v1);
        editor.setItem(21, sumo);
        editor.setItem(23, lava);
    }

    public static ItemStack newMenuItem(Material ironFence, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }

    static WarpsInventory inv = new WarpsInventory().init();
}
