package ridev.com.br.api.bau.player;

import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;
import java.util.Map;

public enum BoxType {

    BASICO, MEDIANO, RARO, SUPREMO, MASTER;

    public int getSlot() {
        if (this.equals(BASICO)) return 20;
        if (this.equals(MEDIANO)) return 21;
        if (this.equals(RARO)) return 22;
        if (this.equals(SUPREMO)) return 23;
        if (this.equals(MASTER)) return 24;
        return 0;
    }

    public String getBeatifulName(int quantity) {
        if (this.equals(BASICO)) return FancyText.colored("&a Baú básico " + quantity + "x");
        if (this.equals(MEDIANO)) return FancyText.colored("&a Baú mediano " + quantity + "x");
        if (this.equals(RARO)) return FancyText.colored("&a Baú raro " + quantity + "x");
        if (this.equals(SUPREMO)) return FancyText.colored("&a Baú supremo " + quantity + "x");
        if (this.equals(MASTER)) return FancyText.colored("&a Baú master " + quantity + "x");
        return null;
    }

    public String getBeatifulName() {
        if (this.equals(BASICO)) return FancyText.colored("&a Baú básico");
        if (this.equals(MEDIANO)) return FancyText.colored("&a Baú mediano");
        if (this.equals(RARO)) return FancyText.colored("&a Baú raro");
        if (this.equals(SUPREMO)) return FancyText.colored("&a Baú supremo");
        if (this.equals(MASTER)) return FancyText.colored("&a Baú master");
        return null;
    }

    public static Map<BoxType, Integer> toHash(String s) {
        Map<BoxType, Integer> boxes = new HashMap<>();
        String[] boxsplitter = s.split(";");
        for (String value : boxsplitter) {
            String[] finalstring = value.split("=");
            boxes.put(transform(finalstring[0]), Integer.parseInt(finalstring[1]));
        }
        return boxes;
    }

    public static BoxType transform(String type) {
        if (type.equalsIgnoreCase("basico") || type.equalsIgnoreCase("basica")) return BASICO;
        if (type.equalsIgnoreCase("mediano") || type.equalsIgnoreCase("mediana")) return MEDIANO;
        if (type.equalsIgnoreCase("raro") || type.equalsIgnoreCase("rara")) return RARO;
        if (type.equalsIgnoreCase("supremo") || type.equalsIgnoreCase("suprema")) return SUPREMO;
        if (type.equalsIgnoreCase("master")) return MASTER;
        return null;
    }

    public String getName() {
        if (this.equals(BASICO)) return "basico";
        if (this.equals(MEDIANO)) return "mediano";
        if (this.equals(RARO)) return "raro";
        if (this.equals(SUPREMO)) return "supremo";
        if (this.equals(MASTER)) return "master";
        return null;
    }

    public String[] getDescription() {
        if (this.equals(BASICO)) {
            return new String[]{
                    FancyText.colored("&7Abra caixas misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"),
                    "",
                    FancyText.colored("&fItens em destaque nesta caixa:"),
                    FancyText.colored("&7◾ &6Ganhar de 20 coins até 50!"),
                    FancyText.colored("&7◾ &6Ganhar mensagens de abate!"),
                    FancyText.colored("&7◾ &bGanhar efeito de abate!")};
        }
        if (this.equals(MEDIANO)) {
            return new String[]{
                    FancyText.colored("&7Abra caixas misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"), "",
                    FancyText.colored("&fItens em destaque nesta caixa:"),
                    FancyText.colored("&7◾ &6Ganhar de 35 coins até 70!"),
                    FancyText.colored("&7◾ &6Kits simples (kits básicos)!"),
                    FancyText.colored("&7◾ &bGanhar de 35 à 70 de XP!")};
        }
        if (this.equals(RARO)) {
            return new String[]{FancyText.colored("&7Abra caixas misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"),
                    "",
                    FancyText.colored("&fItens em destaque nesta caixa:"),
                    FancyText.colored("&7◾ &6Ganhar de 50 coins até 200!"),
                    FancyText.colored("&7◾ &6Kits bem legais (kits Raros)!"),
                    FancyText.colored("&7◾ &bGanhar de 50 à 150 de XP!")};
        }
        if (this.equals(SUPREMO)) {
            return new String[]{FancyText.colored("&7Abra caixas misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"),
                    "",
                    FancyText.colored("&fItens em destaque nesta caixa:"),
                    FancyText.colored("&7◾ &6Ganhar de 100 coins até 400!"),
                    FancyText.colored("&7◾ &6Kits super lendários! (kits Lendários)!"),
                    FancyText.colored("&7◾ &bGanhar de 100 à 300 de XP!")};
        }
        if (this.equals(MASTER)) {
            return new String[]{FancyText.colored("&7Abra caixas misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"),
                    "",
                    FancyText.colored("&fItens em destaque nesta caixa:"),
                    FancyText.colored("&7◾ &6Ganhar De 500 coins até 1200!"),
                    FancyText.colored("&7◾ &6Kits super lendários! (kits Lendários)!"),
                    FancyText.colored("&7◾ &bGanhar de 500 à 1200 de XP!"),
                    FancyText.colored("&7◾ &bGanhar VIPS temporários!")};
        }
        return null;
    }
}
