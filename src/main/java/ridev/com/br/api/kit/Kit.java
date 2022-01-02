package ridev.com.br.api.kit;

import lombok.NonNull;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public interface Kit {


    @NonNull List<String> description();

    @NonNull ItemStack icone();

    int id();

    @NonNull String name();

    @NonNull HashMap<Integer, ItemStack> itens();

    int price();

    @NonNull KitRarity rarity();

    Listener event();
}
