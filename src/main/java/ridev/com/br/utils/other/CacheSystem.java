package ridev.com.br.utils.other;

import com.google.common.collect.Maps;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.logging.Level;

public class CacheSystem {


    private final static Map<String, ItemStack> ITEM_CACHE = Maps.newConcurrentMap();
    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP Cache Itens");

    public void setupCache() {
        /*
            WARP MENUS HEADS
         */
        ITEM_CACHE.put("chest_head", PlayerAPI.getSkull("16bd87cd8c147e76c57e1bb5415bbcc54b8c43c56177677257323cd269ae4"));
        ITEM_CACHE.put("builder_head", PlayerAPI.getSkull("1e1d4bc469d29d22a7ef6d21a61b451291f21bf51fd167e7fd07b719512e87a1"));
        ITEM_CACHE.put("xp_head", PlayerAPI.getSkull("399ad7a0431692994b6c412c7eafb9e0fc49975240b73a27d24ed797035fb894"));
        ITEM_CACHE.put("warp_head", PlayerAPI.getSkull("37cb0982d6975d58b63147932f373cd21e3ccbcaead3275c5aae8b27015f80c6"));
        ITEM_CACHE.put("world_head", PlayerAPI.getSkull("438cf3f8e54afc3b3f91d20a49f324dca1486007fe545399055524c17941f4dc"));
        ITEM_CACHE.put("leader_head", PlayerAPI.getSkull("351137e11443a8fbb05fcd3ccc1af9bd2303918f35448185e3ed96ef184da"));
        ITEM_CACHE.put("arena_head", PlayerAPI.getSkull("1765341353c029e9b655f4f57931ae6adc2c7a73c657945d945a307641d3778"));
        ITEM_CACHE.put("coin_head", PlayerAPI.getSkull("d67dce4645349e41a7f35797e2b9279e35a65f5e81a34496885d27268f369139"));
        /*
            WARPS MENUS HEADS
         */
        ITEM_CACHE.put("set_warp", PlayerAPI.getSkull("1811917c644f04f56b68c9c71b33ac082c2601c24409be78c2063d4df2a86525"));
        ITEM_CACHE.put("unset_warp", PlayerAPI.getSkull("da63b0f56f7ec64eacbb71fca31549d022740d9b7d4b61762a2efe5841a4bf25"));


        ITEM_CACHE.put("sumo_warp", PlayerAPI.getSkull("c420a66522b3a4f7fda934d262f5ca524ed8f609ca57abef5c355466f96ae5"));
        ITEM_CACHE.put("1v1_head", PlayerAPI.getSkull("d6cc6b83763a67fcada1ea184c2d1752ad240746c6be258a73983d8b657f4bb5"));

        /*
                LEADER BOARDS HEADS
         */

        ITEM_CACHE.put("kill_leader", PlayerAPI.getSkull("492f08c1294829d471a8e0109a06fb6ae717e5faf3e0808408a66d889227dac7"));
        ITEM_CACHE.put("xp_leader", PlayerAPI.getSkull("2c7667dbe26f607b699c9303e74f89510dac7543e0c92cef99b334549c57c48a"));

        /*
                ARENA MENU HEADS
        */
        ITEM_CACHE.put("arena_icon", PlayerAPI.getSkull("6a5361b52daf4f1c5c5480a39faaa10897595fa5763f757bdda3956588fec678"));


        /*
               COLOR HEADS
         */
        ITEM_CACHE.put("green", PlayerAPI.getSkull("ac906d688e65802569d9705b579bce56edc86ea5c36bdd6d6fc35516a77d4"));
        ITEM_CACHE.put("red", PlayerAPI.getSkull("eeb118dec73cfbf8885a2069d70273f37c0ef109579d8070aa793a481bd643e4"));
        ITEM_CACHE.put("back", PlayerAPI.getSkull("c49d271c5df84f8a3c8aa5d15427f62839341dab52c619a5987d38fbe18e464"));

        /*
            WORLD MENU HEADS
         */
        ITEM_CACHE.put("world_load", PlayerAPI.getSkull("a47d1dd4a7daff2aaf28e6a12a01f42d7e51593ef3dea762ef81847b1d4c5538"));
        ITEM_CACHE.put("world_teleport", PlayerAPI.getSkull("1321ab1673c95da208e75990242e398f13ac7b6467bb437fb3b7aa9f7cf3ce6d"));
        ITEM_CACHE.put("world_remove", PlayerAPI.getSkull("3c50b241793f7c364ca697aff5de0126fd64f3be022f270bd8df31b5d20dd"));
        /*
                MYSTERY BOX HEAD
         */
        ITEM_CACHE.put("mystery_present", PlayerAPI.getSkull("1789b3e2868d716a921dec5932d530a892f600235f187766bc02d145ed16865b"));
        ITEM_CACHE.put("other_box", PlayerAPI.getSkull("9c96be7886eb7df75525a363e5f549626c21388f0fda988a6e8bf487a53"));


        /*
            SYSTEM HEADS
         */
        ITEM_CACHE.put("machine_head", PlayerAPI.getSkull("5ebfd2396cbabdb42c348bcf41599c87a506a71ef60948c496f95c6cb63141"));
        ITEM_CACHE.put("server_head", PlayerAPI.getSkull("92b1712b907ce6b1402eaac28ec24a4d95568f4ab87e597980c15b22bbbd7a5"));
        LOGGER.log(Level.INFO, "Cache Dos itens carregados Com sucesso!");



        /*
            INFO
         */
        ITEM_CACHE.put("info_head", PlayerAPI.getSkull("d01afe973c5482fdc71e6aa10698833c79c437f21308ea9a1a095746ec274a0f"));


        /*
            KITS HEADS
         */

        ITEM_CACHE.put("troll_head", PlayerAPI.getSkull("df7f40a86bee8e21a55340856a8621aca495673aa17bfde18d3b7aa61b42c"));

        ITEM_CACHE.put("astronauta_head", PlayerAPI.getSkull("fb7dc675e07e74f2bdf4f4939394ec28b9929657be2a094188a50a4b9a2af4c"));

        ITEM_CACHE.put("naruto_head", PlayerAPI.getSkull("b06e34c1c94c7b829c59d01c7453d5f3e8259f389c2aca2fa314dca408693ce"));

        ITEM_CACHE.put("ice_head", PlayerAPI.getSkull("f0701bdf3b88f6e1d7c6e59ca2a3e27ed070d6fb3bc68057f3c12d548d1298f"));

        ITEM_CACHE.put("frog_head", PlayerAPI.getSkull("f1ebc7aad5a656d5842d481167b5b7b98eaf9d924c2dbb9348a31203330263"));

        ITEM_CACHE.put("noiser_head", PlayerAPI.getSkull("6fb290a13df88267ea5f5fcf796b6157ff64ccee5cd39d469724591babeed1f6"));

        ITEM_CACHE.put("turtle_head", PlayerAPI.getSkull("1546389bcd2a32a5a58f6e2e379904db0d167c23ba880d518d4e1a225b57820"));
    }

    public static ItemStack getItem(String item) {
        return ITEM_CACHE.get(item);
    }

}
