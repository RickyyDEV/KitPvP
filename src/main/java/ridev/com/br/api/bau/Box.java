package ridev.com.br.api.bau;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import ridev.com.br.Main;
import ridev.com.br.api.world.LocationAPI;
import ridev.com.br.utils.files.Files;
import ridev.com.br.utils.text.FancyText;

import java.util.logging.Level;


@Builder
@Data
@AllArgsConstructor
@Setter
public class Box {
    private String name;
    private Block block;
    private Location location;
    private boolean using;
    private Hologram holograma;


    public Box(String name, Block bloco) {
        this.block = bloco;
        this.location = this.block.getLocation().clone();
        this.using = false;
        this.name = name;
    }


    @SneakyThrows
    public void createMysteryBox() {
        //                  WORLD CONFIGURATIONS
        Location hologramloc = this.location.clone().add(0.5, 1.7, 0.5);
        //                  WORLD CONFIGURATIONS

        //                 HOLOGRAM CONFIGURATIONS
        Hologram holo = HologramsAPI.createHologram(Main.getInstance(), hologramloc);
        holo.appendTextLine(FancyText.colored("&bCaixa Misteriosa"));
        holo.appendTextLine(FancyText.colored("&eClique para abrir"));
        this.holograma = holo;
        //                 HOLOGRAM CONFIGURATIONS

        Box caixa = Box.builder()
                .block(this.block)
                .location(this.location.clone())
                .using(this.using)
                .name(this.name)
                .holograma(holo)
                .build();
        BoxManager.getBoxes().put(this.name, caixa);
    }

    @SneakyThrows
    public void removeMysteryBox() {

        if (this.getBlock() != null) {
            if (!this.isUsing()) {
                Hologram hl = this.getHolograma();
                if (hl != null) {
                    hl.delete();
                }
                Main.getLoc().set("bau." + this.getName(), null);
                Files.saveLocConfig();
                BoxManager.getBoxes().remove(this.getName());
            } else {
                Main.LOGGER.log(Level.INFO, "Esta mystery box ainda está em Uso! Espere o usuário receber seu premio para remove-la!");
            }
        }
    }

    @SneakyThrows
    public void saveInConfig() {
        String location = LocationAPI.serlialize(this.location);
        Main.getLoc().set("bau." + this.name + ".spawn", location);
        Files.saveLocConfig();
    }


    @SneakyThrows
    public void removeInConfig() {
        Main.getLoc().set("bau." + this.name, null);
        Files.saveLocConfig();
    }

    public void setUsing(boolean use) {
        this.using = use;
    }
}
