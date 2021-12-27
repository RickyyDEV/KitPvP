package ridev.com.br.api.warps.fps;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ridev.com.br.api.combat.CombatLogAPI;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserRecompenses;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.apis.Mine;
import ridev.com.br.utils.text.FancyText;

public class OnPlayerWinFps extends Event implements Cancellable {
    private final User winner;
    private final User loser;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;


    public OnPlayerWinFps(User winner, User loser) {
        this.winner = winner;
        this.loser = loser;
        CombatLogAPI.removePlayerCombat(winner.getPlayer());
        CombatLogAPI.removePlayerCombat(loser.getPlayer());
        FPSItem.pinv.remove(loser.getPlayer());
        winner.getPlayer().sendMessage(FancyText.colored("&6&lFPS &8➸ &7Você ganhou a batalha contra &c" + loser.getUsername() + "&7!"));
        loser.getPlayer().sendMessage(FancyText.colored("&6&llFPS &8➸ &7Você perdeu a batalha contra &c" + winner.getUsername() + "&7!"));

        UserRecompenses.giveRecompenses(winner, loser);
        respawnUsers(WarpLibrary.getWarp(WarpType.FPS).getSpawn());
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
        loser.getPlayer().getInventory().clear();
        loser.getPlayer().setMaxHealth(20);
        loser.getPlayer().setHealth(20);
        loser.getPlayer().teleport(loc);
        FPSItem.setItens(loser.getPlayer());
        Protecao.setImortal(loser.getPlayer(), WarpLibrary.getWarp(WarpType.FPS).isItensBeforeFall());
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
