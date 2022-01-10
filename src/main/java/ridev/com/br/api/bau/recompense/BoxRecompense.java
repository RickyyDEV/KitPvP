package ridev.com.br.api.bau.recompense;

import lombok.NonNull;
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
            if (random <= 85) {
                int random_int = Mine.getRandomInt(20, 40);
                p.addCoins(random_int);

                msg = FancyText.colored("&f[&l&7BASICO&f] Você recebeu &6" + random_int + " &6coins!");
            }
            if (random >= 85) {
                Kit randomKit = getRandomKit(KitRarity.COMUM);
                if (p.getKits().contains(randomKit)) {
                    playerHasHaveItem(p, "Kit " + randomKit.name());
                } else {
                    msg = FancyText.colored("&f[&l&aRARO&f] Você recebeu o kit &6" + randomKit.name() + "&7 !");
                    p.addKit(randomKit);
                }
            }
        }
        if (type.equals(BoxType.MEDIANO)) {
            int random = Mine.getRandomInt(0, 100);
            if (random <= 65) {
                int random_int = Mine.getRandomInt(70, 35);
                p.addCoins(random_int);
                msg = FancyText.colored("&f[&l&7BASICO&f] Você recebeu &6" + random_int + " &fcoins!");
            }
            if (random >= 66) {
                Kit randomKit = getRandomKit(KitRarity.MEDIANO);
                if (p.getKits().contains(randomKit)) {
                    playerHasHaveItem(p, "Kit " + randomKit.name());
                } else {
                    msg = FancyText.colored("&f[&l&aRARO&f] Você recebeu o kit &6" + randomKit.name() + "&7 !");
                    p.addKit(randomKit);
                }
            }
        }
        if (type.equals(BoxType.RARO)) {
            int random = Mine.getRandomInt(0, 100);
            if (random <= 60) {
                int random_int = Mine.getRandomInt(50, 150);
                p.addCoins(random_int);
                msg = FancyText.colored("&f[&l&7BASICO&f] Você recebeu &6" + random_int + " &fcoins!");
            }
            if (random >= 61 && random <= 75) {
                Kit randomKit = getRandomKit(KitRarity.RARO);
                if (p.getKits().contains(randomKit)) {
                    playerHasHaveItem(p, "Kit " + randomKit.name());
                } else {
                    msg = FancyText.colored("&f[&l&aRARO&f] Você recebeu o kit &6" + randomKit.name() + "&7 !");
                    p.addKit(randomKit);
                }
            }
            if (random >= 76) {
                int random_int = Mine.getRandomInt(50, 150);
                p.addXp(random_int);
                msg = FancyText.colored("&f[&l&6LENDÁRIO&f] Você recebeu &6" + random_int + " &6XP!");
            }
        }
        if (type.equals(BoxType.SUPREMO)) {
            int random = Mine.getRandomInt(0, 100);
            if (random <= 55) {
                int random_int = Mine.getRandomInt(100, 400);
                p.addCoins(random_int);
                msg = FancyText.colored("&f[&l&7BASICO&f] Você recebeu &6" + random_int + " &fcoins!");
            }
            if (random >= 56 && random <= 70) {
                Kit randomKit = getRandomKit(KitRarity.SUPREMO);
                if (p.getKits().contains(randomKit)) {
                    playerHasHaveItem(p, "Kit " + randomKit.name());
                } else {
                    msg = FancyText.colored("&f[&l&aRARO&f] Você recebeu o kit &6" + randomKit.name() + "&7 !");
                    p.addKit(randomKit);
                }
            }
            if (random >= 71) {
                int random_int = Mine.getRandomInt(100, 300);
                p.addXp(random_int);
                msg = FancyText.colored("&f[&l&6LENDÁRIO&f] Você recebeu &6" + random_int + " &6XP!");
            }
        }
        if (type.equals(BoxType.MASTER)) {
            int random = Mine.getRandomInt(0, 100);
            if (random <= 50) {
                int random_int = Mine.getRandomInt(500, 1200);
                p.addCoins(random_int);
                msg = FancyText.colored("&f[&l&aBASICO&f] Você recebeu &6" + random_int + " &fcoins!");
            }
            if (random >= 50 && random <= 80) {
                int random_int = Mine.getRandomInt(500, 1200);
                p.addXp(random_int);
                msg = FancyText.colored("&f[&l&aRARO&f] Você recebeu &6" + random_int + " &6XP!");
            }
            if (random >= 81) {
                Kit randomKit = getRandomKit(KitRarity.SUPREMO);
                if (p.getKits().contains(randomKit)) {
                    playerHasHaveItem(p, "Kit " + randomKit.name());
                } else {
                    msg = FancyText.colored("&f[&l&aRARO&f] Você recebeu o kit &6" + randomKit.name() + "&7 !");
                    p.addKit(randomKit);
                }
            }
        }
        return msg;
    }


    public static @NonNull Kit getRandomKit(@NonNull KitRarity raridade) {
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


    public static void playerHasHaveItem(User us, String itemName) {
        us.getPlayer().sendMessage(FancyText.colored("&aVocê ja possui o item " + itemName));
        us.getPlayer().sendMessage(FancyText.colored("&E por isso foi recompensado com &6200 coins&7!"));
        us.addCoins(200);
    }
}
