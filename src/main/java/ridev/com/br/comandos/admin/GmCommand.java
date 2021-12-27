package ridev.com.br.comandos.admin;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import ridev.com.br.comandos.Commands;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.text.FancyText;

public class GmCommand extends Commands {
    public GmCommand() {
        super("gm", "gmode");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if (ConfigValue.get(ConfigValue::gmPermission).isEmpty() || p.hasPermission(ConfigValue.get(ConfigValue::gmPermission))) {
            if (args.length == 0) {
                p.sendMessage(FancyText.colored("&b&lGAMEMODE &8➸ &7Utilize: &6/gm <creative/adventure/survival>/ &7Ou &6/gm <1/2/3>&7!"));
            } else {
                GameMode modetype = p.getGameMode();
                String name = "";
                if (args[0].matches("-?\\d+")) {
                    int mode = Integer.parseInt(args[0]);
                    if (mode == 0 || mode == 1 || mode == 2) {
                        if (mode == 0) {
                            modetype = GameMode.SURVIVAL;
                            name = "Sobrevivência";
                        }
                        if (mode == 1) {
                            modetype = GameMode.CREATIVE;
                            name = "Criativo";
                        }
                        if (mode == 2) {
                            modetype = GameMode.ADVENTURE;
                            name = "Aventura";
                        }
                        p.setGameMode(modetype);
                        p.sendMessage(FancyText.colored(LangValue.get(LangValue::gameModeChange)).replace("%game_type%", name));
                    } else {
                        p.sendMessage(FancyText.colored("&b&lGAMEMODE &8➸ &7Utilize: &6/gm <creative/adventure/survival>/ &7Ou &6/gm <1/2/3>&7!"));
                    }
                } else {
                    if (args[0].equalsIgnoreCase("criativo") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("sobrevivencia") || args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("aventura")) {
                        if (args[0].equalsIgnoreCase("criativo") || args[0].equalsIgnoreCase("creative")) {
                            modetype = GameMode.CREATIVE;
                            name = "Criativo";
                        }
                        if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("sobrevivencia")) {
                            modetype = GameMode.SURVIVAL;
                            name = "Sobrevivência";
                        }
                        if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("aventura")) {
                            modetype = GameMode.ADVENTURE;
                            name = "Aventura";
                        }
                        p.setGameMode(modetype);
                        p.sendMessage(FancyText.colored(LangValue.get(LangValue::gameModeChange)).replace("%game_type%", name));
                    } else {
                        p.sendMessage(FancyText.colored("&b&lGAMEMODE &8➸ &7Utilize: &6/gm <creative/adventure/survival>/ &7Ou &6/gm <1/2/3>&7!"));
                    }
                }
            }
        } else {
            p.sendMessage(FancyText.colored(LangValue.get(LangValue::gameModeNoPermission)));
        }
    }
}
