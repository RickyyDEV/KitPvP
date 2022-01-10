package ridev.com.br.api.warps.lava;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import ridev.com.br.api.user.User;
import ridev.com.br.api.warps.WarpLibrary;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.utils.apis.Mine;
import ridev.com.br.utils.text.FancyText;

public class OnPlayerLoseLava extends Event implements Cancellable {
    private final User dier;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;


    public OnPlayerLoseLava(User dier) {
        this.dier = dier;
        dier.getPlayer().sendMessage(FancyText.colored("&6&lLAVA &8➸ &7Você não conseguiu completar o challenge! Tente novamente! Não desista."));
        respawnUsers(WarpLibrary.getWarp(WarpType.LAVA).getSpawn());
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

    public User getDier() {
        return this.dier;
    }

    public void respawnUsers(Location loc) {
        dier.getPlayer().getInventory().clear();

        dier.getPlayer().setMaxHealth(20);
        dier.getPlayer().setHealth(20);
        dier.getPlayer().teleport(loc);
        Mine.clearInventory(dier.getPlayer());
        LavaItem.setItens(dier.getPlayer());
        Protecao.setImortal(dier.getPlayer(), false);
        for (PotionEffect effect : dier.getPlayer().getActivePotionEffects()) {
            dier.getPlayer().removePotionEffect(effect.getType());
        }
        dier.getPlayer().setFireTicks(0);
    }
}
