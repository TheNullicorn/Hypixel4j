package me.nullicorn.hypixel4j.response.player;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import me.nullicorn.hypixel4j.data.HypixelObject;

/**
 * Represents a player's session on Hypixel (where the player is, what game they are playing, what
 * map they are playing on, etc)
 */
@Getter
public abstract class HypixelSession implements HypixelObject {

    /**
     * Sub-type (or "mode") of the game / location that the session took place in, or null if it is
     * unknown; not to be confused with Minecraft gamemodes
     */
    protected String mode;

    /**
     * Name of the Minecraft map that the session took place on or was played on, in the case of a
     * minigame, or null if it is unknown
     */
    @SerializedName("map")
    protected String mapName;

    @Override
    public String toString() {
        return "HypixelSession{" +
            "mode='" + mode + '\'' +
            ", mapName='" + mapName + '\'' +
            '}';
    }
}
