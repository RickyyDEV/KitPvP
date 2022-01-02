package ridev.com.br.eventos;

import org.bukkit.Bukkit;
import ridev.com.br.Main;
import ridev.com.br.api.bau.BoxEvent;
import ridev.com.br.api.bau.animation.BoxArmorStandEvent;
import ridev.com.br.api.combat.CombatLogAPI;
import ridev.com.br.api.inventory.menu.coins.CoinsEvent;
import ridev.com.br.api.inventory.menu.leader.LeaderEvent;
import ridev.com.br.api.inventory.menu.mysterybox.addbox.AddBoxEvent;
import ridev.com.br.api.inventory.menu.mysterybox.location.MysteryBoxLocationSetEvent;
import ridev.com.br.api.inventory.menu.xp.XpEvent;
import ridev.com.br.api.warps.fps.FPS;
import ridev.com.br.api.warps.lava.Lava;
import ridev.com.br.api.warps.onevsone.OnevsOne;
import ridev.com.br.api.warps.sumo.Sumo;
import ridev.com.br.utils.other.ModuleLogger;

import java.util.logging.Level;

public class Eventos {
    public static final ModuleLogger LOGGER = new ModuleLogger("RiKitPvP Events");


    private final Main plugin;

    public Eventos(Main plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTask(plugin, this::makeListeners);
    }

    public void makeListeners() {
        Bukkit.getPluginManager().registerEvents(new aoEntrar(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new SegurancaGeral(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new Protecao(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new ChatCooldown(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new WorldsEvents(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new FPS(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new Menus(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new Lava(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new PlacaEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new aoSair(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new SopaEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new CombatLogAPI(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new DamagerFixer(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new CoinsEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new BoxEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new BoxArmorStandEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new LeaderEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new MysteryBoxLocationSetEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new OnevsOne(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new XpEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new Sumo(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new AddBoxEvent(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new KnockbackListener(), this.plugin);
        Bukkit.getPluginManager().registerEvents(new BussolaEvent(), this.plugin);
        LOGGER.log(Level.INFO, "Eventos Carregados Com sucesso!");
    }


}
