package me.nullicorn.hypixel4j.response.player;

import lombok.Getter;
import me.nullicorn.hypixel4j.response.APIResponse;

/**
 * A response from the Hypixel API's /status endpoint.
 *
 * @see <a href=https://github.com/HypixelDev/PublicAPI/blob/master/Documentation/methods/status.md>status.md</a>
 */
public class SessionStatusResponse extends APIResponse<HypixelPlayerSession> {

    /**
     * The session information returned by the API
     */
    @Getter
    protected HypixelPlayerSession session;

    @Override
    public HypixelPlayerSession getPayload() {
        return session;
    }
}
