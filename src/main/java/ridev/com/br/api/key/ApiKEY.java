package ridev.com.br.api.key;

import ridev.com.br.language.ConfigValue;
import ridev.com.br.utils.other.ModuleLogger;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ApiKEY {
    private static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP KEY SYSTEM");


    public static boolean isLicencied2() {
        String email = ConfigValue.get(ConfigValue::licenseEmail);
        String senha = ConfigValue.get(ConfigValue::licenseSenha);
        String licensa = ConfigValue.get(ConfigValue::licenseLicensa);
        String url = "";

        return false;
    }


    public static void isLicencied() {
        //We are getting the licence key string from the config
        String key = ConfigValue.get(ConfigValue::licensa);
        String email = ConfigValue.get(ConfigValue::licenseEmail);
        String senha = ConfigValue.get(ConfigValue::licenseSenha);
        String licensa = ConfigValue.get(ConfigValue::licenseLicensa);
        try {
            //We are defining the url location as a string to begin with using "/raw" after pastebin to get the raw paste.
            String url = "https://pastebin.com/raw/" + key;
            //Here we open a connection with the pastebin url
            URLConnection openConnection = new URL(url).openConnection();
            //We use firefox's key to access the site, this can be changed to chrome for example but you will need to find the correct key to do so.
            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            @SuppressWarnings("resource")
            //Here we are then reading from the webpage
            Scanner scan = new Scanner((new InputStreamReader(openConnection.getInputStream())));
            while (scan.hasNextLine()) {
                //We save the first line into a string
                String firstline = scan.nextLine();
                //If the firstline contains the string "true" (this is where you could put your plugin name)
                if (firstline.contains("true")) {
                    //The string customer would be "CureMe" as that is the second line of the pastebin
                    String customer = scan.nextLine();
                    LOGGER.info("Este Plugin Foi licenciado Com Sucesso! Quem Comprou Foi: " + customer + ". Tenha um bom Uso!");
                    //We return the method not having disabled the plugin
                    return;
                }
            }
        } catch (Exception ignored) {

        }
        //We return the method having disabled the plugin because it hasnt already been returned.
        LOGGER.info("A Key Do plugin não é válida, Compre-a por meio do discord https://discord.gg/AFu86sHXRH");
        System.exit(0);
    }
}
