package me.nullicorn.hypixel4j.response.player;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import me.nullicorn.hypixel4j.response.ComplexAPIResponse;
import me.nullicorn.hypixel4j.util.FormatCode;
import me.nullicorn.hypixel4j.util.GameType;
import me.nullicorn.hypixel4j.util.UuidUtil;

/**
 * Created by Ben on 6/8/20 @ 7:25 PM
 */
public class HypixelPlayer extends ComplexAPIResponse {

    @Getter
    @SerializedName("player")
    private JsonObject raw;

    /**
     * @return This player's display name
     */
    public String getName() {
        return getStr("displayname", "Unknown");
    }

    /**
     * @return The player's Minecraft UUID, or null if it could not be found
     */
    public UUID getUuid() {
        if (hasProperty("uuid")) {
            return UuidUtil.fromUndashed(getStr("uuid"));
        } else {
            return null;
        }
    }

    /**
     * @return This player's total network experience points
     */
    public long getExperience() {
        return getLong("networkExp");
    }

    /**
     * @return This player's total karma count
     */
    public long getKarma() {
        return getLong("karma");
    }

    /**
     * @return The player's first login date, or null if it is unknown
     */
    public Date getFirstLogin() {
        if (hasProperty("firstLogin")) {
            return new Date(getLong("firstLogin"));
        }
        return null;
    }

    /**
     * @return The player's last login date, or null if it is unknown
     */
    public Date getLastLogin() {
        if (hasProperty("lastLogin")) {
            return new Date(getLong("lastLogin"));
        }
        return null;
    }

    /**
     * @return The player's last logout date, or null if it is unknown
     */
    public Date getLastLogout() {
        if (hasProperty("lastLogout")) {
            return new Date(getLong("lastLogout"));
        }
        return null;
    }

    /**
     * @return The type of the player's most recently-played game, or {@link GameType#UNKNOWN} if it is unknown
     */
    public GameType getMostRecentGameType() {
        if (hasProperty("mostRecentGameType")) {
            return GameType.fromTypeName(getStr("mostRecentGameType"));
        } else {
            return GameType.UNKNOWN;
        }
    }

    /**
     * @return Whether or not this player is connected to Hypixel
     * @deprecated This method is not reliable. The recentGames endpoint should be used instead
     */
    @Deprecated
    public boolean isOnline() {
        return getLong("lastLogin") > getLong("lastLogout");
    }

    /**
     * @return Whether or not this player has a rank; prefixes do not count (as they are only cosmetic)
     */
    public boolean hasRank() {
        return hasRankInField("rank")
            || hasRankInField("monthlyPackageRank")
            || hasRankInField("newPackageRank")
            || hasRankInField("packageRank");
    }

    /**
     * @return Whether or not this player is on the Hypixel Build Team
     */
    public boolean isOnBuildTeam() {
        return getBool("buildTeam") || getBool("buildTeamAdmin");
    }

    /**
     * @return The highest rank that this player has; prefixes do not count
     */
    public HypixelRank getHighestRank() {
        if (hasRankInField("rank")) {
            return HypixelRank.from(getStr("rank"));

        } else if (hasRankInField("monthlyPackageRank")) {
            return HypixelRank.from(getStr("monthlyPackageRank"));

        } else if (hasRankInField("newPackageRank")) {
            return HypixelRank.from(getStr("newPackageRank"));

        } else if (hasRankInField("packageRank")) {
            return HypixelRank.from(getStr("packageRank"));
        }

        return HypixelRank.DEFAULT;
    }

    /**
     * @return Get the player's rank prefix formatted using Minecraft's pre-1.16 formatting codes
     * @see <a href=https://minecraft.gamepedia.com/Formatting_codes>Formatting Codes</a>
     */
    public String getRankPrefix() {
        String prefix = getStr("prefix", null);
        if (prefix != null && !prefix.isEmpty()) {
            return prefix;
        }

        return getHighestRank().getPrefix(
            FormatCode.fromName(getStr("monthlyRankColor", "GOLD")),
            // Default MVP++ tag color is gold
            FormatCode.fromName(getStr("rankPlusColor", "RED"))
            // Default MVP+/MVP++ plus color is light red
        );
    }

    /**
     * Utility method to check whether a rank field contains a valid ranks. If the field's value is NONE or NORMAL, false is returned
     *
     * @return Whether or not the specified field contains an active rank value
     */
    private boolean hasRankInField(String name) {
        String value = getStr(name, "NONE");
        return !value.isEmpty() && !value.equals("NONE") && !value.equals("NORMAL");
    }
}
