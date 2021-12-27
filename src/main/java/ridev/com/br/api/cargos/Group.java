package ridev.com.br.api.cargos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ridev.com.br.utils.text.FancyText;


@Data
@Builder
@AllArgsConstructor
public class Group {

    private final String roleName;
    private final String permission;
    private final String prefix;
    private final int priority;
    private final boolean broadcast;
    private final String nameWithColor;
    private final String roleColor;


    public Group(@NonNull String name, @NonNull String permission, @NonNull String prefix, boolean broadcast, int priority) {

        this.roleName = FancyText.colored(name);
        this.permission = FancyText.colored(permission);
        this.prefix = FancyText.colored(prefix);
        this.priority = priority;
        this.broadcast = broadcast;
        String roleColor = FancyText.getFirstColor(prefix);
        this.nameWithColor = roleColor + this.roleName;
        this.roleColor = roleColor;

    }


    public void createGroup() {
        Group group = Group.builder()
                .roleName(this.roleName)
                .permission(this.permission)
                .prefix(this.prefix)
                .priority(this.priority)
                .broadcast(this.broadcast)
                .nameWithColor(this.nameWithColor)
                .roleColor(this.roleColor)
                .build();
        GroupManager.getGroups().add(group);
    }


}

