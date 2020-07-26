package me.nullicorn.hypixel4j.response.key;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.nullicorn.hypixel4j.data.HypixelObject;
import me.nullicorn.hypixel4j.response.APIResponse;

/**
 * A response from the Hypixel API's /key endpoint.
 *
 * @see <a href=https://github.com/HypixelDev/PublicAPI/blob/master/Documentation/methods/key.md>key.md</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeyStatsResponse extends APIResponse<HypixelAPIKeyStats> implements HypixelObject {

    @SerializedName("record")
    protected HypixelAPIKeyStats keyStats;

    @Override
    public HypixelAPIKeyStats getPayload() {
        return keyStats;
    }
}
