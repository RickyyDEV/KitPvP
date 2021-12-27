package ridev.com.br.api.kit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ridev.com.br.Main;
import ridev.com.br.api.lobby.LobbyManager;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserRecompenses;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.apis.Mine;
import ridev.com.br.utils.text.FancyText;

import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Data
public class KitWinEvent extends Event implements Cancellable {

    private final User winner;
    private final User loser;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;


    public KitWinEvent(User winner, User loser) {
        this.winner = winner;
        this.loser = loser;
        this.loser.setWarp(WarpType.LOBBY);
        loser.setKit(null);
        winner.getPlayer().sendMessage(FancyText.colored("&9&lKIT &8➸ &7Você ganhou a batalha contra &a" + loser.getUsername() + "&7!"));
        loser.getPlayer().sendMessage(FancyText.colored("&9&lKIT &8➸ &7Você perdeu a batalha contra &c" + winner.getUsername() + "&7!"));
        this.loser.getPlayer().getInventory().clear();
        for (Map.Entry<Integer, ItemStack> itens : LobbyManager.lobby().getItens().entrySet()) {
            this.loser.getPlayer().getInventory().setItem(itens.getKey(), itens.getValue());
        }
        Protecao.setImortal(this.loser.getPlayer(), true);
        new BukkitRunnable() {
            @Override
            public void run() {
                loser.getPlayer().setMaxHealth(0.5);
                loser.getPlayer().setHealth(0.5);
                loser.getPlayer().teleport(LobbyManager.lobby().getLocation());
                UserRecompenses.giveRecompenses(winner, loser);
            }
        }.runTaskLater(Main.getInstance(), 1);
    }


    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
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
