package me.nullicorn.hypixel4j.response.key;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import lombok.Getter;
import me.nullicorn.hypixel4j.data.HypixelObject;

/**
 * Holds information and stats about a Hypixel API key
 */
@Getter
public class HypixelAPIKeyStats implements HypixelObject {

    protected UUID key;

    @SerializedName("owner")
    protected UUID ownerUuid;

    @SerializedName("limit")
    protected long maxQueriesPerMin;

    protected long queriesInPastMin;

    protected long totalQueries;

    @Override
    public String toString() {
        return "HypixelAPIKeyStats{" +
            "key=" + key +
            ", ownerUuid=" + ownerUuid +
            ", maxQueriesPerMin=" + maxQueriesPerMin +
            ", queriesInPastMin=" + queriesInPastMin +
            ", totalQueries=" + totalQueries +
            '}';
    }
}
