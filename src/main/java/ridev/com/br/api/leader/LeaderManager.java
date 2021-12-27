package ridev.com.br.api.leader;

import lombok.Data;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class LeaderManager {


    @Getter
    private static Map<String, Leader> leaders = new ConcurrentHashMap<>();


    public static Leader getLeader(String leadername) {
        return leaders.get(leadername);
    }
}
