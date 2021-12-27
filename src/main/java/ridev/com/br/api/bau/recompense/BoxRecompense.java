package ridev.com.br.api.bau.recompense;

import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.kit.KitLibrary;
import ridev.com.br.api.kit.KitRarity;
import ridev.com.br.api.user.User;
import ridev.com.br.utils.apis.Mine;
import ridev.com.br.utils.text.FancyText;

import java.util.ArrayList;
import java.util.List;

public class BoxRecompense {


    public static String darRecompensa(BoxType type, User p) {
        String msg = "";
        if (type.equals(BoxType.BASICO)) {
            int random = Mine.getRandomInt(0, 100);
            if (random < 85) {
                int random_int = Mine.getRandomInt(20, 40);
                p.addCoins(random_int);

                msg = FancyText.colored("&f[&l&7BASICO&f] Você Recebeu &6" + random_int + " &6Coins!");
            } else if (random > 85) {
                msg = FancyText.colored("&f[&l&eRARO&f] Você Recebeu A mensagem de Abate &6'Desumilde'");
            }
        }
        if (type.equals(BoxType.MEDIANO)) {
            int random = Mine.getRandomInt(0, 100);
            if (random < 65) {
                int random_int = Mine.getRandomInt(70, 35);
                p.addCoins(random_int);
                msg = FancyText.colored("&f[&l&7BASICO&f] Você Recebeu &6" + random_int + " &fCoins!");
            } else if (random > 65 && random < 80) {
                Kit randomKit = getRandomKit(KitRarity.COMUM);
                msg = FancyText.colored("&f[&l&aRARO&f] Você Recebeu o Kit &6" + randomKit.name() + "&7 !");
                p.addKit(randomKit);
            } else if (random > 85) {
                msg = FancyText.colored("&f[&l&6LENDÁRIO&f] Você Recebeu o Efeito de Abate &6'Cospe Fogo'!");
            }
        }
        if (type.equals(BoxType.RARO)) {
            int random = Mine.getRandomInt(0, 100);
            if (random < 60) {
                int random_int = Mine.getRandomInt(50, 150);
                p.addCoins(random_int);
                msg = FancyText.colored("&f[&l&7BASICO&f] Você Recebeu &6" + random_int + " &fCoins!");
            } else if (random > 60 && random < 75) {
                Kit randomKit = getRandomKit(KitRarity.MEDIANO);
                msg = FancyText.colored("&f[&l&aRARO&f] Você Recebeu o Kit &6" + randomKit.name() + "&7 !");
                p.addKit(randomKit);
            } else if (random > 75) {
                int random_int = Mine.getRandomInt(50, 150);
                p.addXp(random_int);
                msg = FancyText.colored("&f[&l&6LENDÁRIO&f] Você Recebeu &6" + random_int + " &6XP!");
            }
        }
        if (type.equals(BoxType.SUPREMO)) {
            int random = Mine.getRandomInt(0, 100);
            if (random < 55) {
                int random_int = Mine.getRandomInt(100, 400);
                p.addCoins(random_int);
                msg = FancyText.colored("&f[&l&7BASICO&f] Você Recebeu &6" + random_int + " &fCoins!");
            } else if (random > 55 && random < 70) {
                Kit randomKit = getRandomKit(KitRarity.RARO);
                msg = FancyText.colored("&f[&l&aRARO&f] Você Recebeu o Kit &6" + randomKit.name() + "&7 !");
                p.addKit(randomKit);
            } else if (random > 70) {
                int random_int = Mine.getRandomInt(100, 300);
                p.addXp(random_int);
                msg = FancyText.colored("&f[&l&6LENDÁRIO&f] Você Recebeu &6" + random_int + " &6XP!");
            }
        }
        if (type.equals(BoxType.MASTER)) {
            int random = Mine.getRandomInt(0, 100);
            if (random < 50) {
                int random_int = Mine.getRandomInt(500, 1200);
                p.addCoins(random_int);
                msg = FancyText.colored("&f[&l&aBASICO&f] Você Recebeu &6" + random_int + " &fCoins!");
            } else if (random > 50 && random < 80) {
                int random_int = Mine.getRandomInt(500, 1200);
                p.addXp(random_int);
                msg = FancyText.colored("&f[&l&aRARO&f] Você Recebeu &6" + random_int + " &6XP!");
            } else if (random > 80) {
                Kit randomKit = getRandomKit(KitRarity.SUPREMO);
                msg = FancyText.colored("&f[&l&aRARO&f] Você Recebeu o Kit &6" + randomKit.name() + "&7 !");
                p.addKit(randomKit);
            }
        }
        return msg;
    }


    public static Kit getRandomKit(KitRarity raridade) {
        List<Kit> kitsToRandom = new ArrayList<>();
        for (Kit kit : KitLibrary.getKits()) {
            if (kit.rarity().equals(raridade)) {
                kitsToRandom.add(kit);
            }
        }
        if (!kitsToRandom.isEmpty()) {
            return Mine.getRandom(kitsToRandom);
        }
        return null;
    }
}
