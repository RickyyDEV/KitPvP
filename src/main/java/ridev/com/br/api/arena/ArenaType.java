package ridev.com.br.api.arena;

public enum ArenaType {
    A1, A2, A3, A4, A5, A6, A7, A8, A9, A10;


    public static ArenaType transform(String s) {
        if (s.equalsIgnoreCase("arena1")) return A1;
        if (s.equalsIgnoreCase("arena2")) return A2;
        if (s.equalsIgnoreCase("arena3")) return A3;
        if (s.equalsIgnoreCase("arena4")) return A4;
        if (s.equalsIgnoreCase("arena5")) return A5;
        if (s.equalsIgnoreCase("arena6")) return A6;
        if (s.equalsIgnoreCase("arena7")) return A7;
        if (s.equalsIgnoreCase("arena8")) return A8;
        if (s.equalsIgnoreCase("arena9")) return A9;
        if (s.equalsIgnoreCase("arena10")) return A10;
        return null;
    }

    public String getName() {
        if (this.equals(A1)) return "arena1";
        if (this.equals(A2)) return "arena2";
        if (this.equals(A3)) return "arena3";
        if (this.equals(A4)) return "arena4";
        if (this.equals(A5)) return "arena5";
        if (this.equals(A6)) return "arena6";
        if (this.equals(A7)) return "arena7";
        if (this.equals(A8)) return "arena8";
        if (this.equals(A9)) return "arena9";
        if (this.equals(A10)) return "arena10";
        return null;
    }

    public String getBeatifulName() {
        if (this.equals(A1)) return "Arena 1";
        if (this.equals(A2)) return "Arena 2";
        if (this.equals(A3)) return "Arena 3";
        if (this.equals(A4)) return "Arena 4";
        if (this.equals(A5)) return "Arena 5";
        if (this.equals(A6)) return "Arena 6";
        if (this.equals(A7)) return "Arena 7";
        if (this.equals(A8)) return "Arena 8";
        if (this.equals(A9)) return "Arena 9";
        if (this.equals(A10)) return "Arena 10";
        return null;
    }
}
