package ridev.com.br.api.cargos;


import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import ridev.com.br.api.user.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupManager {


    @Getter
    private static List<Group> groups = new ArrayList<>();


    public static Group getGroup(@NonNull String groupName) {
        for (Group gp : groups) {
            if (gp.getRoleName().equalsIgnoreCase(groupName)) return gp;
        }
        return null;
    }

    public static Group getPlayerGroup(@NonNull Player p) {
        for (Group gp : groups) {
            if (p.hasPermission(gp.getPermission())) {
                return gp;
            } else {
                if (gp.getPermission().equalsIgnoreCase("empty")) {
                    return gp;
                }
            }
        }
        return null;
    }

    public static Group getGroupByPriority(int prio) {
        for (Group gp : groups) {
            if (gp.getPriority() == prio) return gp;
        }
        return null;
    }

    public static List<Group> getPlayerHavesPermission(User us) {
        List<Group> grupos = new ArrayList<>();
        for (int i = us.getRole().getPriority(); i < groups.size(); i++) {
            grupos.add(getGroupByPriority(i));
        }
        return grupos;
    }
}
