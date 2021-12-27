package ridev.com.br.api.inventory.menu;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.api.builder.BuildAPI;
import ridev.com.br.api.inventory.menu.arena.MainArena;
import ridev.com.br.api.inventory.menu.coins.CoinsEvent;
import ridev.com.br.api.inventory.menu.leader.MainLeader;
import ridev.com.br.api.inventory.menu.mysterybox.MainMysteryBox;
import ridev.com.br.api.inventory.menu.warp.MainWarp;
import ridev.com.br.api.inventory.menu.world.MainWorld;
import ridev.com.br.api.inventory.menu.xp.XpEvent;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.Sound;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class MainInventory extends SimpleInventory {
    public MainInventory() {
        super("main.inv.principal", FancyText.colored("&7Central DE Comandos"), 9 * 6);
    }

    public void openInv(Player p) {
        this.openInventory(p);
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
        InventoryItem mysteryBoxMenu = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("chest_head"), "&aMystery Box", new String[]{"&7Clique Aqui para entrar", "&7no menu da mystery box!"})).defaultCallback(a -> {
            p.closeInventory();
            new MainMysteryBox().init().openInventory(p);
        });
        InventoryItem buildMenu = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("builder_head"), "&aBuild", new String[]{"&7Clique Aqui para Acionar", "&7O Modo Builder!"})).defaultCallback(a -> {
            if (BuildAPI.isBuilder(p)) {
                Sound.CHICKEN_EGG_POP.play(p, 10, 10);
                BuildAPI.removeBuilder(p);
                p.setGameMode(GameMode.SURVIVAL);
                p.closeInventory();
                p.sendMessage(FancyText.colored("&e&lBUILDER &8➸ &cVocê Saiu do modo builder"));
            } else {
                Sound.CHICKEN_EGG_POP.play(p, 10, 10);
                BuildAPI.addBuilder(p);
                p.setGameMode(GameMode.CREATIVE);
                p.closeInventory();
                p.sendMessage(FancyText.colored("&e&lBUILDER &8➸ &aVocê Entrou no modo builder"));
            }
        });
        InventoryItem xpMenu = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("xp_head"), "&aXP", new String[]{"&7Clique Aqui para Acionar", "&7XP Na conta de um usuário!"})).defaultCallback(a -> {
            XpEvent.getPlayers().add(p);
            p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Agora Diga-me o nome do usuário que deseja adicionar os XP"));
        });
        InventoryItem warpMenu = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("warp_head"), "&aWarps", new String[]{"&7Clique Aqui para Setar", "&7Ou Remover Warps!"})).defaultCallback(a -> new MainWarp().init().openInventory(p));
        InventoryItem worldMenu = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("world_head"), "&aMundos", new String[]{"&7Clique Aqui para Carregar/,", "&7Remover/Teleportar Para Mundos!"})).defaultCallback(a -> new MainWorld().init().openInventory(p));
        InventoryItem leaderMenu = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("leader_head"), "&aLeaderBoards", new String[]{"&7Clique Aqui para Setar", "&7Ou Remover LeaderBoards!"})).defaultCallback(a -> new MainLeader().init().openInventory(p));
        InventoryItem arenaMenu = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("arena_head"), "&aArenas", new String[]{"&7Clique Aqui para Setar", "&7Ou Remover Spawn de Arena!"})).defaultCallback(a -> new MainArena().init().openInventory(p));
        InventoryItem coinsMenu = InventoryItem.of(
                newMenuItemItemStack(CacheSystem.getItem("coin_head"), "&aCoins", new String[]{"&7Clique Aqui para Adicionar", "&7Coins Na Conta de Um Usuário!"})).defaultCallback(a -> {
            a.setCancelled(true);
            p.closeInventory();
            p.sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Me Informe O Nome do player que deseja adicionar os coins!"));
            CoinsEvent.getPlayers().remove(p);
            CoinsEvent.getPlayers().add(p);
        });
        editor.setItem(10, mysteryBoxMenu);
        editor.setItem(12, buildMenu);
        editor.setItem(14, xpMenu);
        editor.setItem(16, warpMenu);
        editor.setItem(28, worldMenu);
        editor.setItem(30, leaderMenu);
        editor.setItem(32, arenaMenu);
        editor.setItem(34, coinsMenu);
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
