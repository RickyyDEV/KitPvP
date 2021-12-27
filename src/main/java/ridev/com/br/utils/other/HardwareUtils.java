package ridev.com.br.utils.other;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class HardwareUtils {


    public static String getTPS() {
        final String name1 = Bukkit.getServer().getClass().getPackage().getName();
        final String version = name1.substring(name1.lastIndexOf(46) + 1);
        Class<?> mcsclass;
        final DecimalFormat format = new DecimalFormat("##.##");
        Object si = null;
        Field tpsField = null;
        try {
            mcsclass = Class.forName("net.minecraft.server." + version + "." + "MinecraftServer");
            si = mcsclass.getMethod("getServer", new Class[0]).invoke(null);
            tpsField = si.getClass().getField("recentTps");
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException | SecurityException ex2) {
            ex2.printStackTrace();
        }
        double[] tps = null;
        try {
            assert tpsField != null;
            tps = (double[]) tpsField.get(si);
        } catch (IllegalArgumentException | IllegalAccessException e2) {
            e2.printStackTrace();
        }
        String ftps;
        assert tps != null;
        if (Double.compare(tps[0], 20.0) > 0) {
            ftps = String.valueOf(20.0);
        } else {
            ftps = format.format(tps[0]);
        }
        return ftps;
    }

    public static long getDiskTotalSize() {
        final File dir = new File("/");
        return dir.getTotalSpace();
    }

    public static long getUsageDiskSize() {
        final File dir = new File("/");
        return dir.getUsableSpace();
    }

    public static long getFreeSizeDisk() {
        final File dir = new File("/");
        return dir.getFreeSpace();
    }

    public static double getTotalMemory() {
        return HardwareUtils.getInformationToDouble("getTotalPhysicalMemorySize");
    }

    public static double getFreeMemory() {
        return HardwareUtils.getInformationToDouble("getFreePhysicalMemorySize");
    }

    public static double getRamUsed() {
        return getTotalMemory() - getFreeMemory();
    }


    public static double getPorcentageMemoryUse() {

        return getRamUsed() * 100L / getTotalMemory();
    }

    public static int getProcessatorCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    @SneakyThrows
    public static String getCPUName() {
        return Files.lines(Paths.get("/proc/cpuinfo"))
                .filter(line -> line.startsWith("model name"))
                .map(line -> line.replaceAll(".*: ", ""))
                .findFirst().orElse("");
    }

    public static String getInformationToString(String information) {
        final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        final StringBuilder res = new StringBuilder();
        Method[] declaredMethods;
        for (int length = (declaredMethods = operatingSystemMXBean.getClass().getDeclaredMethods()).length, i = 0; i < length; ++i) {
            final Method m = declaredMethods[i];
            m.setAccessible(true);
            if (m.getName().startsWith("get") && Modifier.isPublic(m.getModifiers())) {
                Object v;
                try {
                    v = m.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    v = e;
                }
                switch (m.getName()) {
                    case "getTotalPhysicalMemorySize": {
                        if (information.equalsIgnoreCase("getTotalPhysicalMemorySize")) {
                            return ("Memória RAM total: " + formatBytes(Long.parseLong(v.toString()), false));
                        }
                    }
                    case "getFreePhysicalMemorySize": {
                        if (information.equalsIgnoreCase("getFreePhysicalMemorySize")) {
                            return ("&fMemória livre: " + formatBytes(Long.parseLong(v.toString()), false) + "\n");
                        }
                    }
                    default:
                        break;
                }
            }
        }
        return "";
    }

    public static double getInformationToDouble(String information) {
        final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        Method[] declaredMethods;
        for (int length = (declaredMethods = operatingSystemMXBean.getClass().getDeclaredMethods()).length, i = 0; i < length; ++i) {
            final Method m = declaredMethods[i];
            m.setAccessible(true);
            if (m.getName().startsWith("get") && Modifier.isPublic(m.getModifiers())) {
                Object v;
                try {
                    v = m.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    v = e;
                }
                switch (m.getName()) {
                    case "getTotalPhysicalMemorySize": {
                        if (information.equalsIgnoreCase("getTotalPhysicalMemorySize")) {
                            return Long.parseLong(v.toString());
                        }
                    }
                    case "getFreePhysicalMemorySize": {
                        if (information.equalsIgnoreCase("getFreePhysicalMemorySize")) {
                            return Long.parseLong(v.toString());
                        }
                    }
                    default:
                        break;
                }
            }
        }
        return 0;
    }

    public static String formatBytes(final long bytes, final boolean si) {
        final int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        final int exp = (int) (Math.log((double) bytes) / Math.log(unit));
        final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

}


