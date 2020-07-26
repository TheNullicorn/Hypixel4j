package me.nullicorn.hypixel4j.response.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.UUID;
import me.nullicorn.hypixel4j.data.ComplexHypixelObject;
import me.nullicorn.hypixel4j.util.FormatCode;
import me.nullicorn.hypixel4j.util.GameType;
import me.nullicorn.hypixel4j.util.ObjectIdUtil;
import me.nullicorn.hypixel4j.util.UuidUtil;

/**
 * Represents a Hypixel player
 */
public class HypixelPlayer extends ComplexHypixelObject {

    /**
     * @param data The raw player JsonObject returned from the Hypixel API
     */
    public HypixelPlayer(JsonObject data) {
        this.raw = data;
    }

    /**
     * The raw JsonObject that represents this Hypixel player; the object in the "player" field of
     * the API's player endpoint
     */
    @SerializedName("player")
    private final JsonElement raw;

    @Override
    public JsonObject getRaw() {
        if (raw != null && raw.isJsonObject()) {
            return raw.getAsJsonObject();
        } else {
            return null;
        }
    }

    /**
     * @return Whether or not this player exists in the Hypixel API; this may be false if the player
     * has never connected to the network before
     */
    public boolean exists() {
        return getRaw() != null;
    }

    /**
     * @return The Minecraft username the player had when they last connected to Hypixel, or null if
     * it is unknown
     */
    public String getName() {
        // Attempt to get their display name
        String displayName = getStringProperty("displayname", null);
        if (displayName != null) {
            return displayName;
        }

        // Fallback to their most recently-known alias
        JsonArray knownAliases = getArrayProperty("knownAliases");
        if (knownAliases != null && knownAliases.size() > 0) {
            return knownAliases.get(knownAliases.size() - 1).getAsString();
        }

        // Fallback to lowercase variants of their name
        return getStringProperty("playername", getStringProperty("username", null));
    }

    /**
     * @return The player's Minecraft UUID, or null if it could not be found
     */
    public UUID getUuid() {
        if (hasProperty("uuid")) {
            return UuidUtil.fromUndashed(getStringProperty("uuid", null));
        } else {
            return null;
        }
    }

    /**
     * @return This player's total network experience points
     */
    public long getExperience() {
        long experience = getLongProperty("networkExp", 0);
        experience += getTotalExpToExactLevel(getLongProperty("networkLevel", 0) + 1);
        return experience;
    }

    /**
     * @return The player's precise network level (e.g. 128.64 for someone 64% of the way to level
     * 129 from level 128)
     */
    public double getLevel() {
        return getExactLevelFromExp(getExperience());
    }

    /**
     * @return This player's total karma count
     */
    public long getKarma() {
        return getLongProperty("karma", 0);
    }

    /**
     * @return The player's first login date, or null if it is unknown
     */
    public Date getFirstLogin() {
        if (hasProperty("_id")) {
            return ObjectIdUtil.getTimestamp(getStringProperty("_id", ""));

        } else if (hasProperty("firstLogin")) {
            return new Date(getLongProperty("firstLogin", 0));

        }
        return null;
    }

    /**
     * @return The player's last login date, or null if it is unknown
     */
    public Date getLastLogin() {
        if (hasProperty("lastLogin")) {
            return new Date(getLongProperty("lastLogin", 0));
        }
        return null;
    }

    /**
     * @return The player's last logout date, or null if it is unknown
     */
    public Date getLastLogout() {
        if (hasProperty("lastLogout")) {
            return new Date(getLongProperty("lastLogout", 0));
        }
        return null;
    }

    /**
     * @return The type of the player's most recently-played game, or {@link GameType#UNKNOWN} if it
     * is unknown
     */
    public GameType getMostRecentGameType() {
        if (hasProperty("mostRecentGameType")) {
            return GameType.fromTypeName(getStringProperty("mostRecentGameType", null));
        } else {
            return GameType.UNKNOWN;
        }
    }

    /**
     * @return The last Minecraft version that the player used to connect to Hypixel, or null if it
     * is unknown
     */
    public String getLastKnownMinecraftVersion() {
        return getStringProperty("mcVersionRp", null);
    }

    /**
     * @return Whether or not this player is connected to Hypixel
     * @deprecated This method is not reliable. The <code>status</code> endpoint should be used
     * instead
     */
    @Deprecated
    public boolean isOnline() {
        return getLongProperty("lastLogin", 0) > getLongProperty("lastLogout", 0);
    }

    /**
     * @return Whether or not this player has hidden their session status in the API; if false, the
     * <code>status</code> endpoint will always return null for this player, even if they are
     * online
     */
    public boolean isSessionVisible() {
        return getBoolProperty("settings.apiSession", true);
    }

    /**
     * @return Whether or not this player has hidden their recent games in the API; if false, the
     * <code>recentGames</code> endpoint will always return an empty array for this player, even if
     * they have played any minigames recently
     */
    public boolean areRecentGamesVisible() {
        return getBoolProperty("settings.apiRecentGames", true);
    }

    /**
     * @return Whether or not this player has a rank; prefixes do not count (as they are only
     * cosmetic)
     */
    public boolean hasRank() {
        return getHighestRank() != HypixelRank.DEFAULT;
    }

    /**
     * @return Whether or not this player is on the Hypixel Build Team
     */
    public boolean isOnBuildTeam() {
        return getBoolProperty("buildTeam", false) || getBoolProperty("buildTeamAdmin", false);
    }

    /**
     * @return The highest rank that this player has; prefixes do not count
     */
    public HypixelRank getHighestRank() {
        if (hasRankInField("rank")) {
            return HypixelRank.from(getStringProperty("rank", null));

        } else if (hasRankInField("monthlyPackageRank")) {
            return HypixelRank.from(getStringProperty("monthlyPackageRank", null));

        } else if (hasRankInField("newPackageRank")) {
            return HypixelRank.from(getStringProperty("newPackageRank", null));

        } else if (hasRankInField("packageRank")) {
            return HypixelRank.from(getStringProperty("packageRank", null));
        }

        return HypixelRank.DEFAULT;
    }

    /**
     * @return Get the player's rank prefix formatted using Minecraft's pre-1.16 formatting codes
     * @see <a href=https://minecraft.gamepedia.com/Formatting_codes>Formatting Codes</a>
     */
    public String getRankPrefix() {
        String prefix = getStringProperty("prefix", null);
        if (prefix != null && !prefix.isEmpty()) {
            return prefix;
        }

        return getHighestRank().getPrefix(
            FormatCode.fromName(getStringProperty("monthlyRankColor", "GOLD")),
            // Default MVP++ tag color is gold
            FormatCode.fromName(getStringProperty("rankPlusColor", "RED"))
            // Default MVP+/MVP++ plus color is light red
        );
    }

    /**
     * Utility method to check whether a rank field contains a valid ranks. If the field's value is
     * NONE or NORMAL, false is returned
     *
     * @return Whether or not the specified field contains an active rank value
     */
    private boolean hasRankInField(String name) {
        String value = getStringProperty(name, "NONE");
        return !value.isEmpty() && !value.equals("NONE") && !value.equals("NORMAL");
    }

    /**
     * Square root of 2; stored here to avoid excessive use of sqrt()
     */
    private static final double SQRT_2 = StrictMath.sqrt(2);

    /**
     * Utility method to find the total amount of experience needed to get to a precise network
     * level
     *
     * @param level Player's exact network level
     * @return The total amount of network experience needed to reach that level
     */
    private long getTotalExpToExactLevel(double level) {
        return -15312 + (long) StrictMath.pow(
            (25 * SQRT_2 * level) + (125 / SQRT_2),
            2);
    }

    /**
     * Utility method to find the precise network level of a player given their total network
     * experience
     *
     * @param exp The player's total network experience
     * @return The exact level of a player with that amount of experience
     */
    private double getExactLevelFromExp(double exp) {
        return (StrictMath.sqrt(exp + 15312.5) - (125 / SQRT_2)) / (25 * SQRT_2);
    }

    @Override
    public String toString() {
        if (!exists()) {
            return "HypixelPlayer{exists=false}";
        }

        return "HypixelPlayer{" +
            "exists=" + exists() +
            ", uuid=" + getUuid() +
            ", name=" + getName() +
            ", hasRank=" + hasRank() +
            ", rank=" + getHighestRank() +
            ", networkLevel=" + getLevel() +
            ", karma=" + getKarma() +
            ", firstLogin=" + getFirstLogin() +
            ", isOnBuildTeam=" + isOnBuildTeam() +
            '}';
    }
}
