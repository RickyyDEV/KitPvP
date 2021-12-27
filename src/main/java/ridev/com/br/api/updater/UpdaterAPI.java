package ridev.com.br.api.updater;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import ridev.com.br.Main;
import ridev.com.br.utils.other.ModuleLogger;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * All rights reserved.
 *
 * @author ColoredCarrot
 * @since 2.6
 */
public class UpdaterAPI {
    private static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP UPDATE SYSTEM");
    static ArrayList<String> itens = new ArrayList<>();

    public static void isUpdated() {
        try {
            String url = "https://pastebin.com/raw/5WUCFJLs";

            URLConnection openConnection = new URL(url).openConnection();
            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            @SuppressWarnings("resource")
            Scanner scan = new Scanner((new InputStreamReader(openConnection.getInputStream())));
            while (scan.hasNextLine()) {
                //We save the first line into a string
                itens.add(scan.nextLine());
                //If the firstline contains the string "true" (this is where you could put your plugin name)
            }
            if (!itens.get(0).equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
                //The string customer would be "CureMe" as that is the second line of the pastebin
                String downloadlink = itens.get(1);
                LOGGER.info("O Plugin Está Desatualizado! A Nova versão existente é a " + itens.get(0) + "!");
                LOGGER.info("INSTALANDO...");
                //We return the method not having disabled the plugin
                try {


                    File file = new File(Main.getInstance().getDataFolder(), "RiKitPvP.jar");
                    File f = new File(Bukkit.getServer().getWorldContainer().getPath() + File.separator + "plugins", "RiKitPvP.jar");
                    f.mkdirs();
                    if (f.exists()) {
                        f.delete();
                    }

                    FileUtils.copyURLToFile(new URL(downloadlink), f);

                    LOGGER.info("Plugins Atualizado Com Sucesso! Reinicie o server para surtir efeito!");
                } catch (Exception e) {
                    LOGGER.warning("ERRO AO TENTAR INSTALAR A VERSÃO MAIS ATUALIZADA DO RIKITPVP");
                    LOGGER.warning("Caso tentar novamente e repercurtir e erros, pessa suporte no servidor!");
                    e.printStackTrace();
                }
            } else {
                LOGGER.info("Versão do seu plugin " + Main.getInstance().getDescription().getVersion());
                LOGGER.info("Versão mais Atual " + itens.get(0));
                LOGGER.info("Seu Plugin está na versão Mais Atualizada! Aproveite!");
            }


        } catch (Exception ignored) {

        }
    }
}
