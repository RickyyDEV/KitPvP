package ridev.com.br.api.inventory.loja.box;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class BoxShop extends SimpleInventory {
    public BoxShop() {
        super("loja.mysterybox", FancyText.colored("&7Loja de Caixas Misteriosas"), 9 * 6);
    }


    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        InventoryItem basico = InventoryItem.of(
                newMenuItem(Material.ENDER_CHEST, "&aBaú Básico", "&7Abra caixas mistériosas para", "&7Ganhar diversos prêmios impolgantes!", "&r", "&fComprar por:", "&f◾ &a600 Coins", "&r", "&aClique Para comprar!")).defaultCallback(a -> {
            if (us.getCoins() >= 600) {
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você comprou uma caixa Básica!"));
                us.addBoxes(BoxType.BASICO, 1);
                us.removeCoins(600);
                this.updateInventory(p);
            } else {
                p.closeInventory();
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você não possue Coins o suficiente!"));
            }
        });
        InventoryItem mediano = InventoryItem.of(
                newMenuItem(Material.ENDER_CHEST, "&aBaú Mediano", "&7Abra caixas mistériosas para", "&7Ganhar diversos prêmios impolgantes!", "&r", "&fComprar por:", "&f◾ &a1000 Coins", "&r", "&aClique Para comprar!")).defaultCallback(a -> {
            if (us.getCoins() >= 1000) {
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você comprou uma caixa Mediana!"));
                us.addBoxes(BoxType.MEDIANO, 1);
                us.removeCoins(1000);
                this.updateInventory(p);
            } else {
                p.closeInventory();
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você não possue Coins o suficiente!"));
            }
        });
        InventoryItem raro = InventoryItem.of(
                newMenuItem(Material.ENDER_CHEST, "&aBaú Raro", "&7Abra caixas mistériosas para", "&7Ganhar diversos prêmios impolgantes!", "&r", "&fComprar por:", "&f◾ &a1450 Coins", "&r", "&aClique Para comprar!")).defaultCallback(a -> {
            if (us.getCoins() >= 1450) {
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você comprou uma caixa Rara!"));
                us.addBoxes(BoxType.RARO, 1);
                us.removeCoins(1450);
                this.updateInventory(p);
            } else {
                p.closeInventory();
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você não possue Coins o suficiente!"));
            }
        });
        InventoryItem supremo = InventoryItem.of(
                newMenuItem(Material.ENDER_CHEST, "&aBaú Supremo", "&7Abra caixas mistériosas para", "&7Ganhar diversos prêmios impolgantes!", "&r", "&fComprar por:", "&f◾ &a1850 Coins", "&r", "&aClique Para comprar!")).defaultCallback(a -> {
            if (us.getCoins() >= 1850) {
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você comprou uma caixa Suprema!"));
                us.addBoxes(BoxType.SUPREMO, 1);
                us.removeCoins(1850);
            } else {
                p.closeInventory();
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você não possue Coins o suficiente!"));
            }
        });
        InventoryItem saldo = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("coin_head"), "&eSaldo", new String[]{"&7Você possue &a" + us.getCoins() + " Coins!"})).defaultCallback(a -> a.setCancelled(true));

        editor.setItem(19, basico);
        editor.setItem(21, mediano);
        editor.setItem(23, raro);
        editor.setItem(25, supremo);
        editor.setItem(40, saldo);
    }


    public static ItemStack newMenuItem(Material ironFence, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }


    public static ItemStack newMenuItemItemStack(ItemStack ironFence, String s, String[] strings) {
        final ItemMeta kitem = ironFence.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        ironFence.setItemMeta(kitem);

        return ironFence;
    }

    
}
