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
        if (this.equals(BASICO)) return FancyText.colored("&a Baú Básico " + quantity + "x");
        if (this.equals(MEDIANO)) return FancyText.colored("&a Baú Mediano " + quantity + "x");
        if (this.equals(RARO)) return FancyText.colored("&a Baú Raro " + quantity + "x");
        if (this.equals(SUPREMO)) return FancyText.colored("&a Baú Supremo " + quantity + "x");
        if (this.equals(MASTER)) return FancyText.colored("&a Baú Master " + quantity + "x");
        return null;
    }

    public String getBeatifulName() {
        if (this.equals(BASICO)) return FancyText.colored("&a Baú Básico");
        if (this.equals(MEDIANO)) return FancyText.colored("&a Baú Mediano");
        if (this.equals(RARO)) return FancyText.colored("&a Baú Raro");
        if (this.equals(SUPREMO)) return FancyText.colored("&a Baú Supremo");
        if (this.equals(MASTER)) return FancyText.colored("&a Baú Master");
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
        if (type.equalsIgnoreCase("basico")) return BASICO;
        if (type.equalsIgnoreCase("mediano")) return MEDIANO;
        if (type.equalsIgnoreCase("raro")) return RARO;
        if (type.equalsIgnoreCase("supremo")) return SUPREMO;
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
                    FancyText.colored("&7Abra Caixas Misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"),
                    "",
                    FancyText.colored("&fItens em destaque Nesta Caixa:"),
                    FancyText.colored("&7◾ &6Ganhar De 20 coins até 50!"),
                    FancyText.colored("&7◾ &6Ganhar Mensagens de Abate!"),
                    FancyText.colored("&7◾ &bGanhar efeito de Abate!")};
        }
        if (this.equals(MEDIANO)) {
            return new String[]{
                    FancyText.colored("&7Abra Caixas Misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"), "",
                    FancyText.colored("&fItens em destaque Nesta Caixa:"),
                    FancyText.colored("&7◾ &6Ganhar De 35 coins até 70!"),
                    FancyText.colored("&7◾ &6Kits Simples (kits básicos)!"),
                    FancyText.colored("&7◾ &bGanhar de 35 à 70 de XP!")};
        }
        if (this.equals(RARO)) {
            return new String[]{FancyText.colored("&7Abra Caixas Misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"),
                    "",
                    FancyText.colored("&fItens em destaque Nesta Caixa:"),
                    FancyText.colored("&7◾ &6Ganhar De 50 coins até 200!"),
                    FancyText.colored("&7◾ &6Kits Bem Legais (kits Raros)!"),
                    FancyText.colored("&7◾ &bGanhar de 50 à 150 de XP!")};
        }
        if (this.equals(SUPREMO)) {
            return new String[]{FancyText.colored("&7Abra Caixas Misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"),
                    "",
                    FancyText.colored("&fItens em destaque Nesta Caixa:"),
                    FancyText.colored("&7◾ &6Ganhar De 100 coins até 400!"),
                    FancyText.colored("&7◾ &6Kits Super Lendários! (kits Lendários)!"),
                    FancyText.colored("&7◾ &bGanhar de 100 à 300 de XP!")};
        }
        if (this.equals(MASTER)) {
            return new String[]{FancyText.colored("&7Abra Caixas Misteriosas para ter a chance"),
                    FancyText.colored("&7de receber um dos itens abaixo!"),
                    "",
                    FancyText.colored("&fItens em destaque Nesta Caixa:"),
                    FancyText.colored("&7◾ &6Ganhar De 500 coins até 1200!"),
                    FancyText.colored("&7◾ &6Kits Super Lendários! (kits Lendários)!"),
                    FancyText.colored("&7◾ &bGanhar de 500 à 1200 de XP!"),
                    FancyText.colored("&7◾ &bGanhar VIPS temporários!")};
        }
        return null;
    }
}
