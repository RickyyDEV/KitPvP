package ridev.com.br.api.user;

import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.apis.Mine;
import ridev.com.br.utils.text.FancyText;

public class UserRecompenses {

    public static void giveRecompenses(User winner, User loser) {
        winner.addKill();
        loser.addMorte();
        if (ConfigValue.get(ConfigValue::giveXp) != null && ConfigValue.get(ConfigValue::giveXp)) {
            int randomXP = Mine.getRandomInt(ConfigValue.get(ConfigValue::giveXpMinimum), ConfigValue.get(ConfigValue::giveXpMaximum));
            winner.addXp(randomXP);
            winner.getPlayer().sendMessage(FancyText.colored(LangValue.get(LangValue::giveXp)).replace("%value%", String.valueOf(randomXP)).replace("%player%", winner.getUsername()));
        }

        if (ConfigValue.get(ConfigValue::giveCoins) != null && ConfigValue.get(ConfigValue::giveCoins)) {
            int randomCoins = Mine.getRandomInt(ConfigValue.get(ConfigValue::giveCoinsMinimum), ConfigValue.get(ConfigValue::giveCoinsMaximum));
            winner.addCoins(randomCoins);
            winner.getPlayer().sendMessage(FancyText.colored(LangValue.get(LangValue::giveCoins)).replace("%value%", String.valueOf(randomCoins)).replace("%player%", winner.getUsername()));
        }

        if (ConfigValue.get(ConfigValue::loseXp) != null && ConfigValue.get(ConfigValue::loseXp)) {
            int randomXp = Mine.getRandomInt(ConfigValue.get(ConfigValue::loseXpMinimum), ConfigValue.get(ConfigValue::loseXpMaximum));
            winner.removeXp(randomXp);
//            winner.getPlayer().sendMessage(FancyText.colored("&b&lXP &8➸ &7Você &cPerdeu &a" + randomXp + " Xp &7!"));
            winner.getPlayer().sendMessage(FancyText.colored(LangValue.get(LangValue::loseXp)).replace("%value%", String.valueOf(randomXp)).replace("%player%", loser.getUsername()));
        }

        if (ConfigValue.get(ConfigValue::loseCoins) != null && ConfigValue.get(ConfigValue::loseCoins)) {
            int randomCoins = Mine.getRandomInt(ConfigValue.get(ConfigValue::loseCoinsMinimum), ConfigValue.get(ConfigValue::loseCoinsMaximum));
            winner.removeCoins(randomCoins);
//            winner.getPlayer().sendMessage(FancyText.colored("&a&lCOINS &8➸ &7Você &cPerdeu &6" + randomCoins + " Coins &7!"));
            winner.getPlayer().sendMessage(FancyText.colored(LangValue.get(LangValue::loseCoins)).replace("%value%", String.valueOf(randomCoins)).replace("%player%", loser.getUsername()));

        }
    }


}
