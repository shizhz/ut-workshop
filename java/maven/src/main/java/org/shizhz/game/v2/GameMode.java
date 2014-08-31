package org.shizhz.game.v2;

/**
 * Created by zzshi on 8/30/14.
 */
public enum GameMode {
    STORY("Story"), FIGHT("Fight");

    private final String description;

    GameMode(String description) {
       this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static GameMode getEnum(String code) {
        code = code == null ? "" : code.trim();
        switch (code) {
            case "1":
                return STORY;
            case "2":
                return FIGHT;
            default:
                throw new IllegalArgumentException();
        }
    }
}
