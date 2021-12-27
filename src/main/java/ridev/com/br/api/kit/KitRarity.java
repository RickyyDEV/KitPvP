package ridev.com.br.api.kit;

import ridev.com.br.utils.text.FancyText;

public enum KitRarity {
    COMUM, MEDIANO, RARO, SUPREMO;


    public String getBeatifulName() {
        if (this.equals(COMUM)) return FancyText.colored("&7COMUM");
        if (this.equals(MEDIANO)) return FancyText.colored("&9MEDIANO");
        if (this.equals(RARO)) return FancyText.colored("&eRARO");
        if (this.equals(SUPREMO)) return FancyText.colored("&dSUPREMO");
        return null;
    }
}
