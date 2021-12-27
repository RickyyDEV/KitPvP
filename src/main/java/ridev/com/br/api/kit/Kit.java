package ridev.com.br.api.kit;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public interface Kit {


    List<String> description();

    ItemStack icone();

    int id();

    String name();

    HashMap<Integer, ItemStack> itens();

    int price();

    KitRarity rarity();

    Listener event();
}
