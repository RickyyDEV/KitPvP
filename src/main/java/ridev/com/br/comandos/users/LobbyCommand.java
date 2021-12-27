package ridev.com.br.comandos.users;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import ridev.com.br.api.lobby.LobbyManager;
import ridev.com.br.api.user.User;
import ridev.com.br.api.user.UserManager;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.api.warps.fps.FPSItem;
import ridev.com.br.comandos.Commands;
import ridev.com.br.eventos.Protecao;
import ridev.com.br.language.ConfigValue;
import ridev.com.br.language.LangValue;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.text.FancyText;
import ridev.com.br.utils.title.TitleSchema;

import java.util.Map;

public class LobbyCommand extends Commands {
    public LobbyCommand() {
        super("lobby", "spawn");
    }

    @Override
    public void perform(Player sender, String label, String[] args) {
        User us = UserManager.getPlayer(sender);
        if (ConfigValue.get(ConfigValue::lobbyPermission).isEmpty() || sender.hasPermission(ConfigValue.get(ConfigValue::lobbyPermission))) {
            if (!LobbyManager.lobbyIsSet()) {
                if (sender.hasPermission("rikitpvp.admin") || sender.isOp()) {
                    sender.sendMessage(FancyText.colored("&b&lLOBBY &8➸ &7O Lobby &cnão foi setado&7!"));
                } else {
                    sender.sendMessage(FancyText.colored(LangValue.get(LangValue::lobbyUnSet)));
                }
            } else {
                if (!us.getWarp().equals(WarpType.LOBBY)) {
                    MineReflect.sendTitle(sender, "", "", 1, 1, 1);
                    sender.teleport(LobbyManager.lobby().getLocation());
                    sender.sendMessage(FancyText.colored(LangValue.get(LangValue::lobbyInvited)));
                    FPSItem.pinv.remove(sender);
                    sender.getInventory().clear();
                    sender.removePotionEffect(PotionEffectType.SLOW);
                    sender.removePotionEffect(PotionEffectType.BLINDNESS);
                    sender.setMaxHealth(0.5);
                    sender.setHealth(0.5);
                    sender.playSound(sender.getLocation(), Sound.LEVEL_UP, 10F, 0F);
                    sender.getInventory().setArmorContents(null);
                    sender.setFireTicks(0);
                    sender.setGameMode(GameMode.SURVIVAL);
                    us.setWarp(WarpType.LOBBY);
                    for (Map.Entry<Integer, ItemStack> itens : LobbyManager.lobby().getItens().entrySet()) {
                        sender.getInventory().setItem(itens.getKey(), itens.getValue());
                    }
                    Protecao.setImortal(sender, true);
                    TitleSchema.sendSpawnTitle(sender);
                } else {
                    sender.sendMessage(FancyText.colored("&b&lLOBBY &8➸ &7Você &cjá está no Spawn&7!"));
                }
            }
        }
    }
}
