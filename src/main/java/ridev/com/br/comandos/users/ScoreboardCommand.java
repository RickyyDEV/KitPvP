package ridev.com.br.comandos.users;

import org.bukkit.entity.Player;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.scoreboard.ScoreboardManager;
import ridev.com.br.utils.text.FancyText;

public class ScoreboardCommand extends Commands {
    public ScoreboardCommand() {
        super("scoreboard", "sb", "score");
    }

    @Override
    public void perform(Player sender, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::scorePermission).isEmpty() || sender.hasPermission(ConfigValue.get(ConfigValue::scorePermission))) {
            if (ScoreboardManager.scores.containsKey(sender)) {
                sender.sendMessage(FancyText.colored("&b&lSCORE &8➸ &fA ScoreBoard Foi &cDESATIVADA&f!"));
                ScoreboardManager.removeScoreboard(sender);
            } else {
                sender.sendMessage(FancyText.colored("&b&lSCORE &8➸ &fA ScoreBoard Foi &aATIVADA&f!"));
                ScoreboardManager.setScore(sender);
            }
        } else {
            sender.sendMessage(FancyText.colored("&cVocê não possui permissão para executar este Comando!"));
        }
    }
}
