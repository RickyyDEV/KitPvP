package ridev.com.br.api.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import ridev.com.br.Main;
import ridev.com.br.utils.apis.Extra;
import ridev.com.br.utils.other.CacheSystem;
import ridev.com.br.utils.other.HardwareUtils;
import ridev.com.br.utils.text.FancyText;

import java.util.Arrays;

public class ServerInfoInventory extends SimpleInventory {
    public ServerInfoInventory() {
        super("serverinfo.menu", FancyText.colored("&7Informações do sistema: "), 9 * 6);
        configuration(configuration -> {
            configuration.secondUpdate(1);
        });
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        String TPS = HardwareUtils.getTPS();
        String OSName = HardwareUtils.getOSName();
        String totalMemory = HardwareUtils.formatBytes((long) HardwareUtils.getInformationToDouble("getTotalPhysicalMemorySize"), true);
        String freeMemory = HardwareUtils.formatBytes((long) HardwareUtils.getInformationToDouble("getFreePhysicalMemorySize"), true);
        String usingmemoryPorcentage = String.valueOf(HardwareUtils.getPorcentageMemoryUse()).substring(0, 4) + "%";
        String usingMemory = HardwareUtils.formatBytes((long) HardwareUtils.getRamUsed(), true);
        int procesatorCores = HardwareUtils.getProcessatorCores();
        String CPUName = HardwareUtils.getCPUName();
        String totalDiskSpace = HardwareUtils.formatBytes(HardwareUtils.getDiskTotalSize(), true);
        String getUsingDisk = HardwareUtils.formatBytes(HardwareUtils.getUsageDiskSize(), true);
        String getFreeSizeDisk = HardwareUtils.formatBytes(HardwareUtils.getFreeSizeDisk(), true);
        String ip = Extra.getServerIp();
        String servername = Bukkit.getServer().getServerName();
        new BukkitRunnable() {
            public void run() {
                InventoryItem machine = InventoryItem.of(
                        newMenuItemItemStack(CacheSystem.getItem("machine_head"), "&6Informações da Maquina: ",
                                "&r",
                                "&fSistema operacional: &6" + OSName,
                                "&fNúcleos lógicos: &6" + procesatorCores,
                                "&fModelo da CPU: &6" + CPUName.substring(0, 20),
                                "&fEspaço total: &6" + totalDiskSpace,
                                "&fEspaço sendo usado: &6" + getUsingDisk,
                                "&fEspaço livre: &6" + getFreeSizeDisk
                        )).defaultCallback(a -> a.setCancelled(true)
                );

                InventoryItem serverInfo = InventoryItem.of(
                        newMenuItemItemStack(CacheSystem.getItem("server_head"), "&6Informações do Servidor: ",
                                "&r",
                                "&fMemória RAM total: &6" + totalMemory,
                                "&fMemória livre: &6" + freeMemory,
                                "&fMemória sendo usada: &6" + usingMemory,
                                "&fUso da memória (%): &6" + usingmemoryPorcentage,
                                "&fTPS: &6" + TPS,
                                "&fNome do servidor: &6" + servername,
                                "&fIP do servidor: &6" + ip
                        )).defaultCallback(a -> a.setCancelled(true)
                );
                editor.setItem(13, machine);
                editor.setItem(40, serverInfo);
            }
        }.runTask(Main.getInstance());
    }

    @Override
    protected void update(Viewer viewer, InventoryEditor editor) {
        String TPS = HardwareUtils.getTPS();
        String OSName = HardwareUtils.getOSName();
        String totalMemory = HardwareUtils.formatBytes((long) HardwareUtils.getInformationToDouble("getTotalPhysicalMemorySize"), true);
        String freeMemory = HardwareUtils.formatBytes((long) HardwareUtils.getInformationToDouble("getFreePhysicalMemorySize"), true);
        String usingmemoryPorcentage = String.valueOf(HardwareUtils.getPorcentageMemoryUse()).substring(0, 4) + "%";
        String usingMemory = HardwareUtils.formatBytes((long) HardwareUtils.getRamUsed(), true);
        int procesatorCores = HardwareUtils.getProcessatorCores();
        String CPUName = HardwareUtils.getCPUName();
        String totalDiskSpace = HardwareUtils.formatBytes(HardwareUtils.getDiskTotalSize(), true);
        String getUsingDisk = HardwareUtils.formatBytes(HardwareUtils.getUsageDiskSize(), true);
        String getFreeSizeDisk = HardwareUtils.formatBytes(HardwareUtils.getFreeSizeDisk(), true);
        String ip = Extra.getServerIp();
        String servername = Bukkit.getServer().getServerName();
        new BukkitRunnable() {
            public void run() {
                InventoryItem machine = InventoryItem.of(
                        newMenuItemItemStack(CacheSystem.getItem("machine_head"), "&6Informações da Maquina: ",
                                "&r",
                                "&fSistema operacional: &6" + OSName,
                                "&fNúcleos lógicos: &6" + procesatorCores,
                                "&fModelo da CPU: &6" + CPUName.substring(0, 20),
                                "&fEspaço total: &6" + totalDiskSpace,
                                "&fEspaço sendo usado: &6" + getUsingDisk,
                                "&fEspaço livre: &6" + getFreeSizeDisk
                        )).defaultCallback(a -> a.setCancelled(true)
                );

                InventoryItem serverInfo = InventoryItem.of(
                        newMenuItemItemStack(CacheSystem.getItem("server_head"), "&6Informações do Servidor: ",
                                "&r",
                                "&fMemória RAM Total: &6" + totalMemory,
                                "&fMemória livre: &6" + freeMemory,
                                "&fMemória sendo usada: &6" + usingMemory,
                                "&fUso da memória (%): &6" + usingmemoryPorcentage,
                                "&fTPS: &6" + TPS,
                                "&fNome do servidor: &6" + servername,
                                "&fIP do servidor: &6" + ip
                        )).defaultCallback(a -> a.setCancelled(true)
                );
                editor.setItem(13, machine);
                editor.setItem(40, serverInfo);
            }
        }.runTask(Main.getInstance());
    }


    public static ItemStack newMenuItem(Material ironFence, String s, String... strings) {
        final ItemStack item = new ItemStack(ironFence);
        final ItemMeta kitem = item.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        item.setItemMeta(kitem);

        return item;
    }

    public static ItemStack newMenuItemItemStack(ItemStack ironFence, String s, String... strings) {
        final ItemMeta kitem = ironFence.getItemMeta();
        kitem.setDisplayName(FancyText.colored(s));
        kitem.setLore(Arrays.asList(FancyText.colored(strings)));
        ironFence.setItemMeta(kitem);

        return ironFence;
    }


}
