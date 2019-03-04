package de.c0debase.bot.database.data;

import lombok.Data;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class CodebaseUser {

    private String userID, guildID;
    private int level, xp;
    private Double coins;
    private long lastMessage;
    private List<String> roles;

    public boolean addXP(int xp) {
        int morexp = ThreadLocalRandom.current().nextInt(xp);
        this.xp += morexp;
        coins += morexp * 0.03;
        double reach = 1000 * level * 1.2;
        if (this.xp >= reach && reach != 0) {
            this.xp = 0;
            level += 1;
            return true;
        } else if (this.xp >= 1000 && level == 0) {
            level += 1;
            return true;
        }
        return false;
    }
}
