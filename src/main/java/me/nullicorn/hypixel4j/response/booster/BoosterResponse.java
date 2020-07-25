package me.nullicorn.hypixel4j.response.booster;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.nullicorn.hypixel4j.data.HypixelObject;
import me.nullicorn.hypixel4j.response.APIResponse;
import me.nullicorn.hypixel4j.util.GameType;

/**
 * A response from the Hypixel API's /boosters endpoint.
 *
 * @see <a href=https://github.com/HypixelDev/PublicAPI/blob/master/Documentation/methods/boosters.md>boosters.md</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoosterResponse extends APIResponse<BoosterResponse> implements HypixelObject {

    @SerializedName("boosters")
    protected List<HypixelBooster> boosterList;

    @SerializedName("boosterState")
    @JsonAdapter(BoosterStateTypeAdapter.class)
    protected boolean areBoostersPaused;

    @Override
    public BoosterResponse getPayload() {
        return this;
    }

    /**
     * @return Whether or not boosters are currently ticking down (may be true if Hypixel is in
     * maintenance mode, boosters are down, etc)
     */
    public boolean areBoostersPaused() {
        return areBoostersPaused;
    }

    /**
     * @return The complete list of boosters returned from the API
     */
    public List<HypixelBooster> getAllBoosters() {
        return boosterList;
    }

    /**
     * @return The active booster for the specified GameType, or null if no booster is active for
     * that game
     */
    public HypixelBooster getActiveBoosterForGameType(GameType gameType) {
        HypixelBooster match = null;

        for (HypixelBooster booster : boosterList) {
            if (booster.gameType == gameType && booster.isActive() && (match == null
                || booster.dateActivated.getTime() > match.dateActivated.getTime())) {
                match = booster;
            }
        }
        return match;
    }

    /**
     * @return A list of non-stacked boosters that are queued for the specified GameType
     */
    public List<HypixelBooster> getBoostersInQueue(GameType gameType) {
        List<HypixelBooster> subList = new ArrayList<>();
        for (HypixelBooster booster : boosterList) {
            if (booster.getGameType() == gameType && !booster.isStacked() && booster.isInQueue()) {
                subList.add(booster);
            }
        }
        return subList;
    }

    /**
     * @return A list of stacked boosters that are queued for the specified GameType
     */
    public List<HypixelBooster> getStackedBoostersInQueue(GameType gameType) {
        List<HypixelBooster> subList = new ArrayList<>();
        for (HypixelBooster booster : boosterList) {
            if (booster.getGameType() == gameType && booster.isStacked() && booster.isInQueue()) {
                subList.add(booster);
            }
        }
        return subList;
    }

    @Override
    public String toString() {
        return "BoosterResponse{" +
            "boosterList=" + boosterList +
            ", areBoostersPaused=" + areBoostersPaused +
            '}';
    }

    /**
     * Json type-adapter for reading a booster response's "boosterState.decrementing" field
     */
    private static class BoosterStateTypeAdapter implements JsonDeserializer<Boolean> {

        @Override
        public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            if (json != null && json.isJsonObject()) {
                return !json.getAsJsonObject().get("decrementing").getAsBoolean();
            }
            return true;
        }
    }
}
