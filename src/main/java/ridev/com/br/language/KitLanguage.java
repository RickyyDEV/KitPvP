package ridev.com.br.language;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("kits.yml")
public class KitLanguage implements ConfigurationInjectable {
    @Getter
    private static final KitLanguage instance = new KitLanguage();

    @ConfigField("kit-use-type")
    private String saveType;


    @ConfigField("start-kit")
    private List<String> kits;


    @ConfigSection("kits")
    private ConfigurationSection kitsPermissions;


    @ConfigField("kits.turtle.permission")
    private String turtlePermission;
    @ConfigField("kits.turtle.description")
    private List<String> turtleDescription;
    @ConfigField("kits.turtle.preco")
    private int turtlePreco;

    @ConfigField("kits.noiser.permission")
    private String noiserPermission;
    @ConfigField("kits.noiser.description")
    private List<String> noiserDescription;
    @ConfigField("kits.noiser.preco")
    private int noiserPreco;

    @ConfigField("kits.dinamite.permission")
    private String dinamitePermission;
    @ConfigField("kits.dinamite.description")
    private List<String> dinamiteDescription;
    @ConfigField("kits.dinamite.preco")
    private int dinamitePreco;
    @ConfigField("kits.dinamite.cooldown-wait")
    private String dinamiteCooldownWait;
    @ConfigField("kits.dinamite.dinamite-puted")
    private String dinamitePuted;
    @ConfigField("kits.dinamite.explose-time")
    private String dinamiteExploseTime;

    @ConfigField("kits.jumper.permission")
    private String jumperPermission;
    @ConfigField("kits.jumper.description")
    private List<String> jumperDescription;
    @ConfigField("kits.jumper.preco")
    private int jumperPreco;

    @ConfigField("kits.frost.permission")
    private String frostPermission;
    @ConfigField("kits.frost.description")
    private List<String> frostDescription;
    @ConfigField("kits.frost.preco")
    private int frostPreco;
    @ConfigField("kits.frost.frost-cooldown")
    private String frostCooldown;
    @ConfigField("kits.frost.frosted-author")
    private String frostedAuthor;
    @ConfigField("kits.frost.frosted-victim")
    private String frostedVictim;
    @ConfigField("kits.frost.frosted-unfreeze")
    private String frostedUnfreeze;

    @ConfigField("kits.barbarian.permission")
    private String barbarianPermission;
    @ConfigField("kits.barbarian.description")
    private List<String> barbarianDescription;
    @ConfigField("kits.barbarian.preco")
    private int barbarianPreco;
    @ConfigField("kits.barbarian.level-passed")
    private String barbarianLevelPassed;
    @ConfigField("kits.barbarian.kills-remaing")
    private String barbarianRemaing;


    @ConfigField("kits.astronauta.permission")
    private String astronautaPermission;
    @ConfigField("kits.astronauta.description")
    private List<String> astronautaDescription;
    @ConfigField("kits.astronauta.preco")
    private int astronautaPreco;
    @ConfigField("kits.astronauta.sended-meteorites")
    private String astronautaSended;
    @ConfigField("kits.astronauta.wait-cooldown")
    private String astronautaCooldown;

    @ConfigField("kits.trevo.permission")
    private String trevoPermission;
    @ConfigField("kits.trevo.description")
    private List<String> trevoDescription;
    @ConfigField("kits.trevo.preco")
    private int trevoPreco;
    @ConfigField("kits.trevo.trevo-gift")
    private String trevoGift;


    @ConfigField("kits.fireman.permission")
    private String firemanPermission;
    @ConfigField("kits.fireman.description")
    private List<String> firemanDescription;
    @ConfigField("kits.fireman.preco")
    private int firemanPreco;

    @ConfigField("kits.fisherman.permission")
    private String fishermanPermission;
    @ConfigField("kits.fisherman.description")
    private List<String> fishermanDescription;
    @ConfigField("kits.fisherman.preco")
    private int fishermanPreco;

    @ConfigField("kits.gladiador.permission")
    private String gladiadorPermission;
    @ConfigField("kits.gladiador.description")
    private List<String> gladiadorDescription;
    @ConfigField("kits.gladiador.preco")
    private int gladiadorPreco;
    @ConfigField("kits.gladiador.in-glad")
    private String gladiadorInGlad;

    @ConfigField("kits.kangaroo.permission")
    private String kangarooPermission;
    @ConfigField("kits.kangaroo.description")
    private List<String> kangarooDescription;
    @ConfigField("kits.kangaroo.preco")
    private int kangarooPreco;
    @ConfigField("kits.kangaroo.in-batle")
    private String kangarooInBatle;

    @ConfigField("kits.malhado.permission")
    private String malhadoPermission;
    @ConfigField("kits.malhado.description")
    private List<String> malhadoDescription;
    @ConfigField("kits.malhado.preco")
    private int malhadoPreco;

    @ConfigField("kits.naruto.permission")
    private String narutoPermission;
    @ConfigField("kits.naruto.description")
    private List<String> narutoDescription;
    @ConfigField("kits.naruto.preco")
    private int narutoPreco;

    @ConfigField("kits.ninja.permission")
    private String ninjaPermission;
    @ConfigField("kits.ninja.description")
    private List<String> ninjaDescription;
    @ConfigField("kits.ninja.preco")
    private int ninjaPreco;
    @ConfigField("kits.ninja.wait-cooldown")
    private String ninjaWaitCooldown;
    @ConfigField("kits.ninja.teleport")
    private String ninjaTeleport;

    @ConfigField("kits.pvp.permission")
    private String pvpPermission;
    @ConfigField("kits.pvp.description")
    private List<String> pvpDescription;
    @ConfigField("kits.pvp.preco")
    private int pvpPreco;

    @ConfigField("kits.stomper.permission")
    private String stomperPermission;
    @ConfigField("kits.stomper.description")
    private List<String> stomperDescription;
    @ConfigField("kits.stomper.preco")
    private int stomperPreco;
    @ConfigField("kits.stomper.player-stompado")
    private String stomperStompado;

    @ConfigField("kits.troller.permission")
    private String trollerPermission;
    @ConfigField("kits.troller.description")
    private List<String> trollerDescription;
    @ConfigField("kits.troller.preco")
    private int trollerPreco;
    @ConfigField("kits.troller.troller-cooldown")
    private String trollerCooldown;
    @ConfigField("kits.troller.troller-teleport")
    private String trollerTeleport;

    @ConfigField("kits.viper.permission")
    private String viperPermission;
    @ConfigField("kits.viper.description")
    private List<String> viperDescription;
    @ConfigField("kits.viper.preco")
    private int viperPreco;


    public static <T> T get(Function<KitLanguage, T> function) {
        return function.apply(instance);
    }
}

