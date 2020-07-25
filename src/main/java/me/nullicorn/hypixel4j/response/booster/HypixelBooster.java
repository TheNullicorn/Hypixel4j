package me.nullicorn.hypixel4j.response.booster;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import me.nullicorn.hypixel4j.util.GameType;
import me.nullicorn.hypixel4j.util.ObjectIdUtil;

/**
 * Represents a network booster purchased by a player on Hypixel
 */
@Getter
public class HypixelBooster {

    /**
     * The booster's internal ID; this is represented by a BSON objectId which can be parsed to
     * determine when the booster was purchased
     */
    @SerializedName("_id")
    protected String id;

    /**
     * Minecraft UUID of the player who owns/activated the booster
     */
    @SerializedName("purchaserUuid")
    protected UUID ownerUuid;

    /**
     * The coin multiplier applied by this booster
     */
    @SerializedName("amount")
    protected float multiplier;

    /**
     * The original duration of this booster (in seconds) before counting down
     */
    @SerializedName("originalLength")
    protected long totalDuration;

    /**
     * The number of seconds remaining before this booster is deactivated; if this is equal to
     * {@link #totalDuration}, the booster is not yet active
     */
    @SerializedName("length")
    protected long timeLeft;

    /**
     * The date when this booster was queued by its owner
     */
    protected Date dateActivated;

    /**
     * The game that this booster's multiplier will be applied to
     */
    protected GameType gameType;

    /**
     * If this booster was converted from another gameType, this is the booster's original type;
     * otherwise, this is null
     * <p>
     * e.g. a booster that was originally for VampireZ will be converted to a Classic Games booster
     */
    protected GameType originalGameType;

    /**
     * Stores the value of the booster's "stacked" field, which can be either a boolean or an array
     */
    @Getter(AccessLevel.PRIVATE)
    @SerializedName("stacked")
    @JsonAdapter(BoosterStackDataTypeAdapter.class)
    protected BoosterStackData stackData;

    /**
     * Whether or not this booster will be stacked onto another booster when active
     */
    public boolean isStacked() {
        return stackData != null && stackData.isStacked;
    }

    /**
     * @return A list of players stacked onto this booster (if this booster is not stacked itself)
     */
    public List<UUID> getStackedPlayers() {
        return stackData == null ? Collections.emptyList() : stackData.stackedPlayers;
    }

    /**
     * @return The date when this booster was purchased by its owner
     */
    public Date getDatePurchased() {
        return ObjectIdUtil.getTimestamp(getId());
    }

    /**
     * @return Whether or not this booster is currently in the queue (inactive)
     */
    public boolean isInQueue() {
        return isStacked() || timeLeft == totalDuration;
    }

    /**
     * @return Whether or not this booster's time is currently ticking down
     */
    public boolean isActive() {
        return !isStacked() && !isInQueue();
    }

    @Override
    public String toString() {
        return "HypixelBooster{" +
            "id='" + id + '\'' +
            ", ownerUuid=" + ownerUuid +
            ", multiplier=" + multiplier +
            ", totalDuration=" + totalDuration +
            ", timeLeft=" + timeLeft +
            ", datePurchased=" + getDatePurchased() +
            ", dateActivated=" + dateActivated +
            ", gameType=" + gameType +
            ", originalGameType=" + originalGameType +
            ", isStacked=" + isStacked() +
            ", stackedPlayers=" + getStackedPlayers() +
            '}';
    }

    /**
     * Information related to a booster's stacking properties
     */
    public static final class BoosterStackData {

        private BoosterStackData() {
        }

        /**
         * Whether or not the booster will be stacked onto another booster when active
         */
        protected boolean isStacked;

        /**
         * A list of players stacked onto the booster (if this booster is not stacked itself)
         */
        protected List<UUID> stackedPlayers;
    }

    /**
     * Json type-adapter for deserializing a booster's "stacked" field
     */
    private static class BoosterStackDataTypeAdapter implements JsonDeserializer<BoosterStackData> {

        @Override
        public BoosterStackData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            BoosterStackData stackData = new BoosterStackData();

            if (json != null) {
                if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isBoolean()) {
                    stackData.isStacked = json.getAsBoolean();
                    stackData.stackedPlayers = Collections.emptyList();

                } else if (json.isJsonArray()) {
                    stackData.isStacked = false;
                    Type uuidListType = TypeToken.getParameterized(List.class, UUID.class)
                        .getRawType();
                    stackData.stackedPlayers = context.deserialize(json, uuidListType);

                } else {
                    stackData.isStacked = false;
                    stackData.stackedPlayers = Collections.emptyList();
                }
            }
            return stackData;
        }
    }
}
