package me.nullicorn.hypixel4j.response.gamecounts;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.nullicorn.hypixel4j.data.HypixelObject;
import me.nullicorn.hypixel4j.response.APIResponse;
import me.nullicorn.hypixel4j.util.GameType;

/**
 * A response from the Hypixel API's /gameCounts endpoint.
 *
 * @see <a href=https://github.com/HypixelDev/PublicAPI/blob/master/Documentation/methods/gameCounts.md>gameCounts.md</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameCountsResponse extends APIResponse<GameCountsResponse> implements HypixelObject {

    @SerializedName("games")
    protected Map<String, HypixelGameCounts> games;

    /**
     * The total number of players connected to Hypixel
     */
    @Getter
    @SerializedName("playerCount")
    protected long totalPlayerCount;

    /**
     * @return The list of games/areas that the API returned {@link HypixelGameCounts} records for;
     * these names can be used in {@link #getGameCountsFor(String)}
     */
    public List<String> getGames() {
        return games == null
            ? Collections.emptyList()
            : new ArrayList<>(games.keySet());
    }

    /**
     * Note: Not all gameCounts records will have a corresponding {@link GameType} (such as
     * MAIN_LOBBY, LIMBO, REPLAY, QUEUE, etc). For these, use {@link #getGameCountsFor(String)}
     *
     * @return The {@link HypixelGameCounts} record for the specified game, or null if none was
     * returned from the API
     */
    public HypixelGameCounts getGameCountsFor(GameType gameType) {
        return getGameCountsFor(gameType.name());
    }

    /**
     * @return The {@link HypixelGameCounts} record for the specified game, or null if none was
     * returned from the API
     */
    public HypixelGameCounts getGameCountsFor(String gameType) {
        return games == null
            ? null
            : games.get(gameType);
    }

    @Override
    public GameCountsResponse getPayload() {
        return this;
    }

    @Override
    public String toString() {
        return "GameCountsResponse{" +
            "games=" + games +
            ", totalPlayerCount=" + totalPlayerCount +
            '}';
    }
}
