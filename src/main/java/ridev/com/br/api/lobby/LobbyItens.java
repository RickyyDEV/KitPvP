package ridev.com.br.api.lobby;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.other.PlayerAPI;
import ridev.com.br.utils.text.FancyText;

import java.util.HashMap;

public class LobbyItens {


    public static HashMap<Integer, ItemStack> getItens() {
        HashMap<Integer, ItemStack> itens = new HashMap<>();
        int warps = ConfigValue.get(ConfigValue::slotWarp);
        int server = ConfigValue.get(ConfigValue::slotServer);
        int cabeca = ConfigValue.get(ConfigValue::slotPerfil);
        int kits = ConfigValue.get(ConfigValue::slotKits);
        int loja = ConfigValue.get(ConfigValue::slotLoja);

        itens.put(server, setitem(Material.COMPASS, FancyText.colored("&aServidores &7(Clique Direito)")));
        itens.put(warps, setitem(Material.EXPLOSIVE_MINECART, FancyText.colored("&aWarps &7(Clique Direito)")));
        itens.put(cabeca, setitem(PlayerAPI.getSkull("82dbd06f9c927db6c79fc9f5ef2397ab0654191364d0f5c89f924e5ed8d3af"), FancyText.colored("&aSeu Perfil &7(Clique Direito)")));
        itens.put(loja, setitem(Material.EMERALD, FancyText.colored("&aLoja &7(Clique Direito)")));
        itens.put(kits, setitem(Material.NETHER_STAR, FancyText.colored("&aKits &7(Clique Direito)")));
        return itens;
    }

    public static ItemStack setitem(final Material mat, final String nome) {
        final ItemStack item = new ItemStack(mat);
        final ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(FancyText.colored(nome));
        item.setItemMeta(itemmeta);
        return item;
    }

    public static ItemStack setitem(final ItemStack mat, final String nome) {
        final ItemMeta itemmeta = mat.getItemMeta();
        itemmeta.setDisplayName(FancyText.colored(nome));
        mat.setItemMeta(itemmeta);
        return mat;
    }
}
