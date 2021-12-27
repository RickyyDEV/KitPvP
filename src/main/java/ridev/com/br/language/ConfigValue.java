package ridev.com.br.language;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("config.yml")
public class ConfigValue implements ConfigurationInjectable {
    @Getter
    private static final ConfigValue instance = new ConfigValue();
    //LICENSE
    @ConfigField("License")
    private String licensa;


    // DATABASE

    @ConfigField("database.type")
    private String databaseType;

    @ConfigField("database.address")
    private String databaseAddress;

    @ConfigField("database.username")
    private String databaseUsername;

    @ConfigField("database.password")
    private String databasePassword;

    @ConfigField("database.database")
    private String databaseDatabase;


    // COMMANDS
    @ConfigField("config.commands.kp.enable")
    private boolean kpEnable;
    @ConfigField("config.commands.kp.permission")
    private String kpPermission;

    @ConfigField("config.commands.fly.enable")
    private boolean flyEnable;
    @ConfigField("config.commands.fly.permission")
    private String flyPermission;

    @ConfigField("config.commands.gm.enable")
    private boolean gmEnable;
    @ConfigField("config.commands.gm.permission")
    private String gmPermission;

    @ConfigField("config.commands.profile.enable")
    private boolean profileEnable;
    @ConfigField("config.commands.profile.permission")
    private String profilePermission;

    @ConfigField("config.commands.inv.enable")
    private boolean invEnable;
    @ConfigField("config.commands.inv.permission")
    private String invPermission;

    @ConfigField("config.commands.clear-chat.enable")
    private boolean clearchatEnable;
    @ConfigField("config.commands.clear-chat.permission")
    private String clearchatPermission;

    @ConfigField("config.commands.info.enable")
    private boolean infoEnable;
    @ConfigField("config.commands.info.permission")
    private String infoPermission;

    @ConfigField("config.commands.lobby.enable")
    private boolean lobbyEnable;
    @ConfigField("config.commands.lobby.permission")
    private String lobbyPermission;

    @ConfigField("config.commands.rank.enable")
    private boolean rankEnable;
    @ConfigField("config.commands.rank.permission")
    private String rankPermission;

    @ConfigField("config.commands.scoreboard.enable")
    private boolean scoreEnable;
    @ConfigField("config.commands.scoreboard.permission")
    private String scorePermission;

    @ConfigField("config.commands.tag.enable")
    private boolean tagEnable;
    @ConfigField("config.commands.tag.permission")
    private String tagPermission;

    @ConfigField("config.commands.tell.enable")
    private boolean tellEnable;
    @ConfigField("config.commands.tell.permission")
    private String tellPermission;

    @ConfigField("config.commands.r.enable")
    private boolean respondEnable;
    @ConfigField("config.commands.r.permission")
    private String respondPermission;

    @ConfigField("config.commands.tphere.enable")
    private boolean tphereEnable;
    @ConfigField("config.commands.tphere.permission")
    private String tpherePermission;

    @ConfigField("config.commands.invisible.enable")
    private boolean invisibleEnable;
    @ConfigField("config.commands.invisible.permission")
    private String invisiblePermission;


    // SERVIDORES
    @ConfigField("config.servidores.bungee-cord-use")
    private boolean bungeeUse;

    @ConfigField("config.servidores.lobby-principal")
    private String lobbyPrincipal;

    @ConfigField("config.servidores.hg")
    private String HG;

    @ConfigField("config.servidores.gladiador")
    private String Gladiador;

    // SPAWN ITENS
    @ConfigField("config.spawn-itens.server-item.slot")
    private int slotServer;

    @ConfigField("config.spawn-itens.perfil-item.slot")
    private int slotPerfil;

    @ConfigField("config.spawn-itens.warp-item.slot")
    private int slotWarp;

    @ConfigField("config.spawn-itens.kits-item.slot")
    private int slotKits;

    @ConfigField("config.spawn-itens.loja-item.slot")
    private int slotLoja;

    // COOLDOWN

    @ConfigField("config.cooldown.enable")
    private boolean cooldownEnable;

    @ConfigField("config.cooldown.permission")
    private String cooldownBypass;

    @ConfigField("config.cooldown.time")
    private int cooldownTime;


    // RECOMPENSAS

    // COINS
    @ConfigField("recompensas.coins.give-coins")
    private boolean giveCoins;

    @ConfigField("recompensas.coins.give-coins-min")
    private int giveCoinsMinimum;

    @ConfigField("recompensas.coins.give-coins-max")
    private int giveCoinsMaximum;


    @ConfigField("recompensas.coins.lose-coins")
    private boolean loseCoins;

    @ConfigField("recompensas.coins.lose-coins-min")
    private int loseCoinsMinimum;

    @ConfigField("recompensas.coins.lose-coins-max")
    private int loseCoinsMaximum;


    // XP
    @ConfigField("recompensas.xp.give-xp")
    private boolean giveXp;

    @ConfigField("recompensas.xp.give-xp-min")
    private int giveXpMinimum;

    @ConfigField("recompensas.xp.give-xp-max")
    private int giveXpMaximum;

    @ConfigField("recompensas.xp.lose-xp")
    private boolean loseXp;

    @ConfigField("recompensas.xp.lose-xp-min")
    private int loseXpMinimum;

    @ConfigField("recompensas.xp.lose-xp-max")
    private int loseXpMaximum;

    public static <T> T get(Function<ConfigValue, T> function) {
        return function.apply(instance);
    }
}
