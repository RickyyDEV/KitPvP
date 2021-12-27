package ridev.com.br.api.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ridev.com.br.api.bau.player.BoxType;
import ridev.com.br.api.cargos.Group;
import ridev.com.br.api.kit.Kit;
import ridev.com.br.api.rank.RankType;
import ridev.com.br.api.rank.Ranks;
import ridev.com.br.api.warps.WarpType;
import ridev.com.br.utils.apis.MineReflect;
import ridev.com.br.utils.scoreboard.ScoreboardManager;
import ridev.com.br.utils.text.FancyText;

import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@Builder
public class User {

    private String username;
    private String caixas;
    private Map<BoxType, Integer> boxes;
    private List<Kit> kits;
    private Kit kit;
    private RankType rank;
    private Group role;

    private int kills;
    private int mortes;
    private int xp;
    private int coins;
    private WarpType warp;

    public void addCoins(int coins) {
        this.setCoins(this.getCoins() + coins);
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
        }
        UserManager.map.replace(this.getUsername(), this);

    }


    public String getKitsString() {
        StringBuilder s = new StringBuilder();
        for (Kit kits : this.getKits()) {
            String finalstring = kits.name() + ";";
            s.append(finalstring);
        }
        return s.toString();
    }

    public void removeCoins(int coins) {
        int coinsfinal = this.getCoins() - coins;
        if ((this.getCoins() - coins) < 0) coinsfinal = 0;
        this.setCoins(coinsfinal);
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
        }
        UserManager.map.replace(this.getUsername(), this);

    }

    public void removeXp(int xp) {
        int xpfinal = this.getXp() - xp;
        if ((this.getXp() - xp) < 0) xpfinal = 0;
        int finalXpfinal = xpfinal;
        this.setXp(xpfinal);
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
        }
        UserManager.map.replace(this.getUsername(), this);

    }


    public void setPlayerRank(RankType rank) {
        this.rank = rank;
        this.setRank(rank);
        this.setRank(rank);
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
            MineReflect.sendNameTag(Bukkit.getPlayer(this.username), this.getRole().getPrefix(), FancyText.colored("&7 (" + this.getRank().getSymbol() + "&7) "), this.getRole().getPriority());
        }
        UserManager.map.replace(this.getUsername(), this);

    }

    public void addKill() {
        this.setKills(this.getKills() + 1);
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
        }
        UserManager.map.replace(this.getUsername(), this);
    }

    public void addXp(int xp) {
        this.setXp(this.getXp() + xp);
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
        }
        UserManager.map.replace(this.getUsername(), this);
        Ranks.checkRankPlayer(this);
    }

    public void addKit(Kit kit) {
        this.getKits().add(kit);
        this.setKits(this.getKits());
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
        }
        UserManager.map.replace(this.getUsername(), this);
    }


    public void addMorte() {
        this.setMortes(this.getMortes() + 1);
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
        }
        UserManager.map.replace(this.getUsername(), this);
    }

    public void addBoxes(BoxType type, int quantity) {
        Map<BoxType, Integer> box = this.getBoxes();
        box.replace(type, box.get(type) + quantity);
        this.setBoxes(box);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<BoxType, Integer> boxes : box.entrySet()) {
            String toappend = boxes.getKey().getName() + "=" + boxes.getValue() + ";";
            sb.append(toappend);
        }
        this.setCaixas(sb.toString());
        if (Bukkit.getPlayer(this.username) != null) {
            ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
        }
        UserManager.map.replace(this.getUsername(), this);
    }

    public void removeBox(BoxType type) {
        Map<BoxType, Integer> box = this.getBoxes();
        StringBuilder sb = new StringBuilder();
        if (box.get(type) > 0) {
            box.replace(type, box.get(type) - 1);
            this.setBoxes(box);
            for (Map.Entry<BoxType, Integer> boxes : box.entrySet()) {
                String toappend = boxes.getKey().getName() + "=" + boxes.getValue() + ";";
                sb.append(toappend);
            }
            this.setCaixas(sb.toString());
            if (Bukkit.getPlayer(this.username) != null) {
                ScoreboardManager.reloadScoreboard(Bukkit.getPlayer(this.username));
            }
            UserManager.map.replace(this.getUsername(), this);
        }
    }


    public int getAllBoxesCount() {
        return this.getBoxCount(BoxType.BASICO) + this.getBoxCount(BoxType.MEDIANO) + this.getBoxCount(BoxType.RARO) + this.getBoxCount(BoxType.SUPREMO) + this.getBoxCount(BoxType.MASTER);
    }

    public int getBoxCount(BoxType type) {
        return this.getBoxes().get(type);
    }


    public Player getPlayer() {
        if (Bukkit.getPlayer(this.username) != null) {
            return Bukkit.getPlayer(this.username);
        }
        return null;
    }

}
