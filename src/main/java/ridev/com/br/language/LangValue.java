package ridev.com.br.language;


import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("language.yml")
public class LangValue implements ConfigurationInjectable {

    @Getter
    private static final LangValue instance = new LangValue();


    @ConfigField("link")
    private String link;


    //      BOAS VINDAS MESSAGE
    @ConfigField("mensagens.join-message.boas-vindas")
    private List<String> boasVindas;

    @ConfigField("mensagens.join-message.spawn-title")
    private String spawnTitle;

    @ConfigField("mensagens.join-message.spawn-subtitle")
    private String spawnSubtitle;


    //      ARENA

    @ConfigField("mensagens.arena-title")
    private String arenaTitle;

    @ConfigField("mensagens.arena-subtitle")
    private String arenaSubtitle;


    //      SCOREBOARD

    @ConfigField("scoreboard.titulo")
    private List<String> scoreTitulo;

    @ConfigField("scoreboard.lines")
    private List<String> scoreLines;


    //      TABLIST

    @ConfigField("tablist.title")
    private List<String> tabTitle;

    @ConfigField("tablist.footer")
    private List<String> tabFooter;


    //       COOLDOWN

    @ConfigField("mensagens.cooldown")
    private String cooldown;


    //         RANK
    @ConfigField("mensagens.rank-pass")
    private String rankPass;

    //         XP

    @ConfigField("mensagens.xp.give-xp")
    private String giveXp;

    @ConfigField("mensagens.xp.lose-xp")
    private String loseXp;


    //         COINS

    @ConfigField("mensagens.coins.give-coins")
    private String giveCoins;

    @ConfigField("mensagens.coins.lose-coins")
    private String loseCoins;


    //          WARPS

    //          1v1
    @ConfigField("mensagens.warps.1v1.join-title")
    private String onevsoneTitle;

    @ConfigField("mensagens.warps.1v1.join-subtitle")
    private String onevsoneSubtitle;

    //          FPS
    @ConfigField("mensagens.warps.fps.join-title")
    private String fpsTitle;

    @ConfigField("mensagens.warps.fps.join-subtitle")
    private String fpsSubtitle;


    //          SUMO
    @ConfigField("mensagens.warps.sumo.join-title")
    private String sumoTitle;

    @ConfigField("mensagens.warps.fps.join-subtitle")
    private String sumoSubtitle;


    //          LAVA
    @ConfigField("mensagens.warps.lava.join-title")
    private String lavaTitle;

    @ConfigField("mensagens.warps.lava.join-subtitle")
    private String lavaSubtitle;


    //             COMMANDS

    // CLEAR CHAT
    @ConfigField("mensagens.commands.clear-chat.final-message")
    private String clearChatFinalMessage;

    @ConfigField("mensagens.commands.clear-chat.no-permission")
    private String clearChatNoPermission;

    // GM
    @ConfigField("mensagens.commands.gm.gamemode-change")
    private String gameModeChange;

    @ConfigField("mensagens.commands.gm.no-permission")
    private String gameModeNoPermission;

    // INFO
    @ConfigField("mensagens.commands.info.no-permission")
    private String infoNoPermission;

    // INV
    @ConfigField("mensagens.commands.inv.seeing-inventory")
    private String seeingInventoryInv;

    @ConfigField("mensagens.commands.inv.not-found")
    private String invNotFound;

    @ConfigField("mensagens.commands.inv.no-permission")
    private String invNoPermission;


    // INVISIBLE
    @ConfigField("mensagens.commands.invisible.isInvisible")
    private String invisibleIsInvisible;

    @ConfigField("mensagens.commands.invisible.isVisible")
    private String invisibleIsVisible;

    @ConfigField("mensagens.commands.invisible.no-permission")
    private String invisibleNoPermission;

    // TP HERE
    @ConfigField("mensagens.commands.tphere.tp-success")
    private String tphereSucess;

    @ConfigField("mensagens.commands.tphere.not-found")
    private String tphereNotFound;

    @ConfigField("mensagens.commands.tphere.tp-yourself")
    private String tphereYourSelf;

    @ConfigField("mensagens.commands.tphere.no-permission")
    private String tphereNoPermission;

    //  FLY
    @ConfigField("mensagens.commands.fly.can-fly")
    private String flyCanFly;

    @ConfigField("mensagens.commands.fly.canot-fly")
    private String flyCanotFly;

    @ConfigField("mensagens.commands.fly.mencioned-user-not-found")
    private String flyUserNotFound;

    @ConfigField("mensagens.commands.fly.only-lobby")
    private String flyOnlyLobby;

    @ConfigField("mensagens.commands.no-permission")
    private String flyNoPermission;

    // LOBBY
    @ConfigField("mensagens.commands.lobby.lobby-unset")
    private String lobbyUnSet;

    @ConfigField("mensagens.commands.lobby.lobby-invited")
    private String lobbyInvited;

    @ConfigField("mensagens.commands.lobby.no-permission")
    private String lobbyNoPermission;


    // PROFILE
    @ConfigField("mensagens.commands.profile.user-not-found")
    private String profileUserNotFound;

    @ConfigField("mensagens.commands.profile.no-permission")
    private String profileNoPermission;


    public static <T> T get(Function<LangValue, T> function) {
        return function.apply(instance);
    }
}
