package ridev.com.br.utils.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import lombok.experimental.Delegate;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Stream;

public class CustomHologram implements Hologram {

    @Delegate
    private final Hologram hologram;

    public CustomHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    public CustomHologram(Plugin plugin, Location location, String... lines) {
        this(HologramsAPI.createHologram(plugin, location));
        Stream.of(lines).forEach(hologram::appendTextLine);
    }

    public CustomHologram(Plugin plugin, Location location, List<String> lines) {
        this(HologramsAPI.createHologram(plugin, location));
        lines.forEach(hologram::appendTextLine);
    }

    public void updateTextLine(int index, String newText) {
        final HologramLine line = hologram.getLine(index);
        if (line instanceof TextLine) {
            ((TextLine) line).setText(newText);
        }
    }
}
