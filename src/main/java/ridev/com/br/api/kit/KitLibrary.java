package ridev.com.br.api.kit;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class KitLibrary {


    @Getter
    static List<Kit> kits = new ArrayList<>();


    public static Kit getKit(String name) {
        for (Kit kits : kits) {
            String kitname = kits.name();
            if (kitname.equalsIgnoreCase(name)) return kits;
        }
        return null;
    }

    public static Kit getKit(int id) {
        for (Kit kits : kits) {
            int kitid = kits.id();
            if (id == kitid) return kits;
        }
        return null;
    }
}
