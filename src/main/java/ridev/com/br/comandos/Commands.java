package ridev.com.br.comandos;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import ridev.com.br.Main;
import ridev.com.br.comandos.admin.*;
import ridev.com.br.comandos.duel.AceitarDuelsCommand;
import ridev.com.br.comandos.duel.AceitarSumoCommand;
import ridev.com.br.comandos.users.*;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.other.ModuleLogger;

import java.util.Arrays;
import java.util.logging.Level;

public abstract class Commands extends Command {

    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP Commands");

    public Commands(String name, String... aliases) {
        super(name);
        this.setAliases(Arrays.asList(aliases));

        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            simpleCommandMap.register(this.getName(), "RiKitPvP", this);
        } catch (ReflectiveOperationException ex) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Não foi possivel registrar o comando: ", ex);
        }
    }

    public abstract void perform(Player sender, String label, String[] args);

    @SneakyThrows
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) this.perform((Player) sender, commandLabel, args);
        return true;
    }

    public static void setupCommands() {
        new AceitarDuelsCommand();
        new AceitarSumoCommand();
        if (ConfigValue.get(ConfigValue::kpEnable)) {
            new KpCommand();
        }
        if (ConfigValue.get(ConfigValue::lobbyEnable)) {
            new LobbyCommand();
        }
        if (ConfigValue.get(ConfigValue::scoreEnable)) {
            new ScoreboardCommand();
        }
        if (ConfigValue.get(ConfigValue::flyEnable)) {
            new FlyCommand();
        }
        if (ConfigValue.get(ConfigValue::gmEnable)) {
            new GmCommand();
        }
        if (ConfigValue.get(ConfigValue::profileEnable)) {
            new ProfileCommand();
        }
        if (ConfigValue.get(ConfigValue::invEnable)) {
            new InvCommand();
        }
        if (ConfigValue.get(ConfigValue::clearchatEnable)) {
            new ClearChatCommand();
        }
        if (ConfigValue.get(ConfigValue::infoEnable)) {
            new InfoCommand();
        }
        if (ConfigValue.get(ConfigValue::rankEnable)) {
            new RankCommand();
        }
        if (ConfigValue.get(ConfigValue::tagEnable)) {
            new TagCommand();
        }
        if (ConfigValue.get(ConfigValue::tellEnable)) {
            new TellCommand();
        }
        if (ConfigValue.get(ConfigValue::respondEnable)) {
            new RespondCommand();
        }
        if (ConfigValue.get(ConfigValue::tphereEnable)) {
            new TpHereCommand();
        }
        if (ConfigValue.get(ConfigValue::invisibleEnable)) {
            new InvisibleCommand();
        }
        LOGGER.log(Level.INFO, "Comandos Carregados Com sucesso!");
    }

}
