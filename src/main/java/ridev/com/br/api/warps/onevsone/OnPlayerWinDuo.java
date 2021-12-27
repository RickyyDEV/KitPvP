package ridev.com.br.api.warps.onevsone;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.combat.CombatLogAPI;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserRecompenses;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.apis.Mine;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.text.FancyText;

public class OnPlayerWinDuo extends Event implements Cancellable {
    private final User winner;
    private final User loser;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;


    public OnPlayerWinDuo(User winner, User loser) {
        this.winner = winner;
        this.loser = loser;
        CombatLogAPI.removePlayerCombat(winner.getPlayer());
        CombatLogAPI.removePlayerCombat(loser.getPlayer());
        OnevsOne.inDuel.remove(winner);
        OnevsOne.inDuel.remove(loser);
        winner.getPlayer().sendMessage(FancyText.colored("&b&lDUELS &8➸ &7Você ganhou a batalha contra &c" + loser.getUsername() + "&7!"));
        loser.getPlayer().sendMessage(FancyText.colored("&b&lDUELS &8➸ &7Você perdeu a batalha contra &c" + winner.getUsername() + "&7!"));

        UserRecompenses.giveRecompenses(winner, loser);
        respawnUsers(WarpLibrary.getWarp(WarpType.ONEVSONE).getSpawn());
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public User getWinner() {
        return this.winner;
    }

    public User getLoser() {
        return this.loser;
    }

    public void respawnUsers(Location loc) {
        winner.getPlayer().getInventory().clear();
        loser.getPlayer().getInventory().clear();
        new BukkitRunnable() {
            public void run() {
                MineReflect.makeRespawn(winner.getPlayer());
                MineReflect.makeRespawn(loser.getPlayer());
            }
        }.runTaskLater(Main.getInstance(), 1);

        new BukkitRunnable() {
            public void run() {
                loser.getPlayer().setMaxHealth(20);
                winner.getPlayer().setMaxHealth(20);
                loser.getPlayer().setHealth(20);
                winner.getPlayer().setHealth(20);
                winner.getPlayer().teleport(loc);
                loser.getPlayer().teleport(loc);
                OnevsOneItens.setIntens(winner.getPlayer());
                OnevsOneItens.setIntens(loser.getPlayer());
                Protecao.setImortal(winner.getPlayer(), true);
                Protecao.setImortal(loser.getPlayer(), true);
            }
        }.runTaskLater(Main.getInstance(), 2);
    }


    public void giveRecompenses() {
        this.winner.addKill();
        this.loser.addMorte();
        if (ConfigValue.get(ConfigValue::giveXp) != null && ConfigValue.get(ConfigValue::giveXp)) {
            int randomXP = Mine.getRandomInt(ConfigValue.get(ConfigValue::giveXpMinimum), ConfigValue.get(ConfigValue::giveXpMaximum));
            this.winner.addXp(randomXP);
            this.winner.getPlayer().sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você &aganhou &a" + randomXP + " XP &7!"));
        }

        if (ConfigValue.get(ConfigValue::giveCoins) != null && ConfigValue.get(ConfigValue::giveCoins)) {
            int randomCoins = Mine.getRandomInt(ConfigValue.get(ConfigValue::giveCoinsMinimum), ConfigValue.get(ConfigValue::giveCoinsMaximum));
            this.winner.addCoins(randomCoins);
            this.winner.getPlayer().sendMessage(FancyText.colored("&b&lXP &8➸ &7Você &aganhou &6" + randomCoins + " Coins &7!"));
        }

        if (ConfigValue.get(ConfigValue::loseXp) != null && ConfigValue.get(ConfigValue::loseXp)) {
            int randomXp = Mine.getRandomInt(ConfigValue.get(ConfigValue::loseXpMinimum), ConfigValue.get(ConfigValue::loseXpMaximum));
            this.winner.removeXp(randomXp);
            this.winner.getPlayer().sendMessage(FancyText.colored("&b&lXP &8➸ &7Você &cPerdeu &a" + randomXp + " Xp &7!"));
        }

        if (ConfigValue.get(ConfigValue::loseCoins) != null && ConfigValue.get(ConfigValue::loseCoins)) {
            int randomCoins = Mine.getRandomInt(ConfigValue.get(ConfigValue::loseCoinsMinimum), ConfigValue.get(ConfigValue::loseCoinsMaximum));
            this.winner.removeCoins(randomCoins);
            this.winner.getPlayer().sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você &cPerdeu &6" + randomCoins + " Coins &7!"));
        }
    }
}
