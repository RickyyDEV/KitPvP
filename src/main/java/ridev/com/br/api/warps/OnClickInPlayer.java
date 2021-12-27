package ridev.com.br.api.warps;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ridev.com.br.api.user.User;

public class OnClickInPlayer extends Event implements Cancellable {
    private final User author;
    private final User clicked;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;


    public OnClickInPlayer(User author, User clicked) {
        this.author = author;
        this.clicked = clicked;
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

    public User getAuthor() {
        return this.author;
    }

    public User getClicked() {
        return this.clicked;
    }
}
