package ridev.com.br.eventos;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import ridev.com.br.api.inventory.signinv.CocoabeanInv;
import ridev.com.br.api.inventory.signinv.CogumelosInv;
import ridev.com.br.utils.text.FancyText;

import java.util.Objects;

public class PlacaEvent implements Listener {


    @EventHandler
    public void onSignChange(final SignChangeEvent e) {
        if (e.getLine(0).equalsIgnoreCase("recraft") && e.getLine(1).equalsIgnoreCase("cogumelos")) {
            e.setLine(0, FancyText.colored("&f&l--------- "));
            e.setLine(1, FancyText.colored("&c&lCOGUMELOS "));
            e.setLine(2, FancyText.colored("&f&lCLIQUE "));
            e.setLine(3, FancyText.colored("&f&l--------- "));
        }
        if (e.getLine(0).equalsIgnoreCase("recraft") && e.getLine(1).equalsIgnoreCase("cocoabeans")) {
            e.setLine(0, FancyText.colored("&f&l--------- "));
            e.setLine(1, FancyText.colored("&e&lCOCOABEANS "));
            e.setLine(2, FancyText.colored("&f&lCLIQUE "));
            e.setLine(3, FancyText.colored("&f&l--------- "));
        }
    }

    @EventHandler
    public void Inv(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock() != null && (e.getClickedBlock().getType() == Material.WALL_SIGN)
                || (Objects.requireNonNull(e.getClickedBlock()).getType() == Material.SIGN_POST))) {
            Sign s = (Sign) e.getClickedBlock().getState();
            String[] lines = s.getLines();

            if (lines.length > 3 && lines[0].equalsIgnoreCase(FancyText.colored("&f&l--------- "))) {
                if (lines[1].equalsIgnoreCase(FancyText.colored("&c&lCOGUMELOS "))) {
                    new CogumelosInv().init().openInventory(p);
                } else if (lines[1].equalsIgnoreCase(FancyText.colored("&e&lCOCOABEANS "))) {
                    new CocoabeanInv().init().openInventory(p);
                }
            }
        }
    }

}
