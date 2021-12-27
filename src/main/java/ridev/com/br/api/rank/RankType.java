package ridev.com.br.api.rank;

import ridev.com.br.utils.text.FancyText;

public enum RankType {
    UNRANKED, PRIMARY, BRONZE, OURO, DIAMOND, ELITE, RUBY, KING, SPECIALIST, MASTER, PROFESSIONAL;


    public static RankType transform(String type) {
        if (type.equals("Unranked")) return UNRANKED;
        if (type.equals("Primary")) return PRIMARY;
        if (type.equals("Bronze")) return BRONZE;
        if (type.equals("Ouro")) return OURO;
        if (type.equals("Diamond")) return DIAMOND;
        if (type.equals("Elite")) return ELITE;
        if (type.equals("Ruby")) return RUBY;
        if (type.equals("King")) return KING;
        if (type.equals("Specialist")) return SPECIALIST;
        if (type.equals("Master")) return MASTER;
        if (type.equals("Professional")) return PROFESSIONAL;
        return null;
    }

    public String getName() {
        if (this.equals(UNRANKED)) return "Unranked";
        if (this.equals(PRIMARY)) return "Primary";
        if (this.equals(BRONZE)) return "Bronze";
        if (this.equals(OURO)) return "Ouro";
        if (this.equals(DIAMOND)) return "Diamond";
        if (this.equals(ELITE)) return "Elite";
        if (this.equals(RUBY)) return "Ruby";
        if (this.equals(KING)) return "King";
        if (this.equals(SPECIALIST)) return "Specialist";
        if (this.equals(MASTER)) return "Master";
        if (this.equals(PROFESSIONAL)) return "Professional";
        return null;
    }

    public String getBeatifulName() {
        if (this.equals(UNRANKED)) return FancyText.colored("&fUnranked");
        if (this.equals(PRIMARY)) return FancyText.colored("&aPrimary");
        if (this.equals(BRONZE)) return FancyText.colored("&eBronze");
        if (this.equals(OURO)) return FancyText.colored("&6Ouro");
        if (this.equals(DIAMOND)) return FancyText.colored("&bDiamond");
        if (this.equals(ELITE)) return FancyText.colored("&5Elite");
        if (this.equals(RUBY)) return FancyText.colored("&4Ruby");
        if (this.equals(KING)) return FancyText.colored("&6King");
        if (this.equals(SPECIALIST)) return FancyText.colored("&dSpecialist");
        if (this.equals(MASTER)) return FancyText.colored("&cMaster");
        if (this.equals(PROFESSIONAL)) return FancyText.colored("&eProfessional");
        return null;
    }


    public String getSymbol() {
        if (this.equals(UNRANKED)) return FancyText.colored("&f-");
        if (this.equals(PRIMARY)) return FancyText.colored("&a=");
        if (this.equals(BRONZE)) return FancyText.colored("&e✴");
        if (this.equals(OURO)) return FancyText.colored("&6✷");
        if (this.equals(DIAMOND)) return FancyText.colored("&b✦");
        if (this.equals(ELITE)) return FancyText.colored("&5✹");
        if (this.equals(RUBY)) return FancyText.colored("&4✪");
        if (this.equals(KING)) return FancyText.colored("&6♕");
        if (this.equals(SPECIALIST)) return FancyText.colored("&d✫");
        if (this.equals(MASTER)) return FancyText.colored("&c♬");
        if (this.equals(PROFESSIONAL)) return FancyText.colored("&e®");
        return null;
    }


    public int getXp() {
        if (this.equals(UNRANKED)) return 1000;
        if (this.equals(PRIMARY)) return 2000;
        if (this.equals(BRONZE)) return 4000;
        if (this.equals(OURO)) return 6500;
        if (this.equals(DIAMOND)) return 9500;
        if (this.equals(ELITE)) return 13000;
        if (this.equals(RUBY)) return 17000;
        if (this.equals(KING)) return 19000;
        if (this.equals(SPECIALIST)) return 22000;
        if (this.equals(MASTER)) return 24000;
        if (this.equals(PROFESSIONAL)) return 26000;
        return 0;
    }

    public RankType getNextRank() {
        if (this.equals(UNRANKED)) return PRIMARY;
        if (this.equals(PRIMARY)) return BRONZE;
        if (this.equals(BRONZE)) return OURO;
        if (this.equals(OURO)) return DIAMOND;
        if (this.equals(DIAMOND)) return ELITE;
        if (this.equals(ELITE)) return RUBY;
        if (this.equals(RUBY)) return KING;
        if (this.equals(KING)) return SPECIALIST;
        if (this.equals(SPECIALIST)) return MASTER;
        if (this.equals(MASTER)) return PROFESSIONAL;
        if (this.equals(PROFESSIONAL)) return null;
        return null;
    }
}
