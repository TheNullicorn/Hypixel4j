package me.nullicorn.hypixel4j.response.gamecounts;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

/**
 * Represents a game's player-count returned from the /gameCounts endpoint
 */
public class HypixelGameCounts {

    /**
     * The total number of players playing in this game/location
     */
    @Getter
    @SerializedName("players")
    protected long totalPlayers;

    /**
     * The number of players playing in each of this game's modes
     */
    @SerializedName("modes")
    protected Map<String, Long> playersPerMode;

    /**
     * @return A list of this game's modes that currently have active players
     */
    public List<String> getModes() {
        return playersPerMode == null
            ? Collections.emptyList()
            : new ArrayList<>(playersPerMode.keySet());
    }

    /**
     * @return Get the number of players playing a certain mode of this game, or 0 if the mod does
     * not exist
     */
    public long getPlayersInMode(String mode) {
        return playersPerMode == null
            ? 0
            : Optional.ofNullable(playersPerMode.get(mode)).orElse(0L);
    }

    /**
     * @return A map of mode names to player counts
     */
    public Map<String, Long> getModeMap() {
        return playersPerMode == null
            ? Collections.emptyMap()
            : playersPerMode;
    }

    @Override
    public String toString() {
        return "HypixelGameCounts{" +
            "totalPlayers=" + totalPlayers +
            ", playersPerMode=" + playersPerMode +
            '}';
    }
}
