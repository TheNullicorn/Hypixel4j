package me.nullicorn.hypixel4j.response.player;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import lombok.Getter;
import me.nullicorn.hypixel4j.util.GameType;

/**
 * Represents a game that was or is currently being played by a player on Hypixel.
 * <p>
 * There are a few important things to note:
 * <ul>
 *     <li>Players may opt to hide this information in the API. To check if they have it hidden, check the value of "<code>settings.apiRecentGames</code>" for the player in the player endpoint. If the value is set to false, they have hidden their session in the API. If it is set to true or does not exist, then they have not hidden it.</li>
 *     <li>If {@link #endedAt} is null, then the game is currently running (or was at the time of the request)</li>
 * </ul>
 */
@Getter
public class HypixelGameSession extends HypixelSession {

    /**
     * GameType of the game played during the session
     */
    protected GameType gameType;

    /**
     * Exact time when the game began
     */
    @SerializedName("date")
    protected Date startedAt;

    /**
     * Exact time when the game ended
     */
    @SerializedName("ended")
    protected Date endedAt;

    @Override
    public String toString() {
        return "HypixelGameSession{" +
            "gameType=" + gameType +
            ", startedAt=" + startedAt +
            ", endedAt=" + endedAt +
            ", mode='" + mode + '\'' +
            ", mapName='" + mapName + '\'' +
            '}';
    }
}
