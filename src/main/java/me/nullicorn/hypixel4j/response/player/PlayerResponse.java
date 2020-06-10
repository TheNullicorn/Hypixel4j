package me.nullicorn.hypixel4j.response.player;

import me.nullicorn.hypixel4j.response.APIResponse;

/**
 * A response from the Hypixel API's /player endpoint.
 *
 * @see <a href=https://github.com/HypixelDev/PublicAPI/blob/master/Documentation/methods/player.md>player.md</a>
 */
public class PlayerResponse extends APIResponse<HypixelPlayer> {

    private static final HypixelPlayer EMPTY_PLAYER = new HypixelPlayer(null);

    protected HypixelPlayer player;

    /**
     * @return The player object returned by the API, or an empty player if the API returned null
     */
    @Override
    public HypixelPlayer getPayload() {
        return player == null ? EMPTY_PLAYER : player;
    }
}
