package me.nullicorn.hypixel4j.response.player;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import me.nullicorn.hypixel4j.data.HypixelObject;
import me.nullicorn.hypixel4j.util.GameType;

/**
 * Represents the status of a Hypixel player's session.
 * <p>
 * There are some important things to note:
 * <ul>
 *     <li>Players may opt to hide this information in the API. To check if they have it hidden, check the value of "<code>settings.apiSession</code>" for the player in the player endpoint. If the value is set to false, they have hidden their session in the API. If it is set to true or does not exist, then they have not hidden it.</li>
 *     <li>If their session information is hidden in the API, or if they are offline, then {@link #location}, {@link #mode}, and {@link #mapName} may be null.</li>
 * </ul>
 */
@Getter
public class HypixelPlayerSession implements HypixelObject {

    /**
     * Whether or not the requested player is online, or false if the player has chosen to hide
     * their session status in the API (see documentation for {@link HypixelPlayerSession})
     */
    @SerializedName("online")
    protected boolean isPlayerOnline;

    /**
     * The player's current location. This value may be any of the {@link GameType GameTypes'} type
     * names / enum names, but can also include non-game values such as "TOURNAMENT", "MAIN", and
     * others. This may be null if it is unknown (see documentation for {@link
     * HypixelPlayerSession})
     */
    @SerializedName("gameType")
    protected String location;

    /**
     * Sub-type (or "mode") of the game / location that the player is currently in (not to be
     * confused with Minecraft gamemodes), or null if it is unknown (see documentation for {@link
     * HypixelPlayerSession})
     */
    protected String mode;

    /**
     * Name of the Minecraft map that the player's current game is being played on, or null if it is
     * unknown (see documentation for {@link HypixelPlayerSession})
     */
    @SerializedName("map")
    protected String mapName;

    @Override
    public String toString() {
        return "HypixelPlayerSession{" +
            "isPlayerOnline=" + isPlayerOnline +
            ", location=" + location +
            ", mode='" + mode + '\'' +
            ", mapName='" + mapName + '\'' +
            '}';
    }
}
