package ridev.com.br.api.inventory.loja.box;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.utils.item.ItemBuilder;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

public class BoxShop extends SimpleInventory {
    public BoxShop() {
        super("loja.mysterybox", FancyText.colored("&7Loja de caixas misteriosas"), 9 * 6);
    }


    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        User us = UserManager.getPlayer(p);
        InventoryItem basico = InventoryItem.of(
                new ItemBuilder(Material.ENDER_CHEST).setName("&aBaú Básico").addLore("&7Abra caixas mistériosas para").addLore("&7Ganhar diversos prêmios impolgantes!").addLore("&r").addLore("&fComprar por:").addLore("&f◾ &a600 Coins").addLore("&r").addLore("&aClique para comprar!").build()).defaultCallback(a -> {
            if (us.getCoins() >= 600) {
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você &acomprou uma caixa Básica&7!"));
                us.addBoxes(BoxType.BASICO, 1);
                us.removeCoins(600);
                this.updateInventory(p);
            } else {
                p.closeInventory();
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você &cnão possui coins o suficiente&7!"));
            }
        });
        InventoryItem mediano = InventoryItem.of(
                new ItemBuilder(Material.ENDER_CHEST).setName("&aBaú Mediano").addLore("&7Abra caixas mistériosas para").addLore("&7Ganhar diversos prêmios impolgantes!").addLore("&r").addLore("&fComprar por:").addLore("&f◾ &a1000 Coins").addLore("&r").addLore("&aClique para comprar!").build()).defaultCallback(a -> {
            if (us.getCoins() >= 1000) {
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você &acomprou uma caixa Mediana&7!"));
                us.addBoxes(BoxType.MEDIANO, 1);
                us.removeCoins(1000);
                this.updateInventory(p);
            } else {
                p.closeInventory();
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você &cnão possui coins o suficiente&7!"));
            }
        });
        InventoryItem raro = InventoryItem.of(
                new ItemBuilder(Material.ENDER_CHEST).setName("&aBaú Raro").addLore("&7Abra caixas mistériosas para").addLore("&7Ganhar diversos prêmios impolgantes!").addLore("&r").addLore("&fComprar por:").addLore("&f◾ &a1450 Coins").addLore("&r").addLore("&aClique para comprar!").build()).defaultCallback(a -> {
            if (us.getCoins() >= 1450) {
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você &acomprou uma caixa Rara&7!"));
                us.addBoxes(BoxType.RARO, 1);
                us.removeCoins(1450);
                this.updateInventory(p);
            } else {
                p.closeInventory();
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você &cnão possui coins o suficiente&7!"));
            }
        });
        InventoryItem supremo = InventoryItem.of(
                new ItemBuilder(Material.ENDER_CHEST).setName("&aBaú Supremo").addLore("&7Abra caixas mistériosas para").addLore("&7Ganhar diversos prêmios impolgantes!").addLore("&r").addLore("&fComprar por:").addLore("&f◾ &a1850 Coins").addLore("&r").addLore("&aClique para comprar!").build()).defaultCallback(a -> {
            if (us.getCoins() >= 1850) {
                Sound.NOTE_PLING.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&b&lBOX &8➸ &7Você &acomprou uma caixa Suprema&7!"));
                us.addBoxes(BoxType.SUPREMO, 1);
                us.removeCoins(1850);
            } else {
                p.closeInventory();
                Sound.VILLAGER_NO.play(p, 10, 10);
                p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você &cnão possue coins o suficiente&7!"));
            }
        });
        ItemStack coins = new ItemBuilder(CacheSystem.getItem("coin_head")).setName("&eSaldo").addLore("&7Você possui &a" + us.getCoins() + " coins!").build();
        InventoryItem saldo = InventoryItem.of(coins).defaultCallback(a -> a.setCancelled(true));

        editor.setItem(19, basico);
        editor.setItem(21, mediano);
        editor.setItem(23, raro);
        editor.setItem(25, supremo);
        editor.setItem(40, saldo);
    }


}
