package ridev.com.br.api.bau;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

@Data
public class BoxManager {

    @Getter
    private static HashMap<String, Box> boxes = Maps.newHashMap();


    public static boolean boxHasExist(String boxName) {
        return getBox(boxName) != null;
    }

    public static Box getBox(String boxName) {
        return boxes.get(boxName);
    }

    public static Box getBox(Block bloco) {
        Box bl = null;
        for (Map.Entry<String, Box> boxes : boxes.entrySet()) {
            Box box = boxes.getValue();
            if (box.getBlock().equals(bloco)) bl = box;

        }
        return bl;
    }

    public static boolean isMysteryBox(Block block) {
        for (Map.Entry<String, Box> boxes : boxes.entrySet()) {
            Box box = boxes.getValue();
            if (box.getBlock().equals(block)) return box.getBlock().equals(block);
        }
        return false;
    }
}
