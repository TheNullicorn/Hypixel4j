package me.nullicorn.hypixel4j.response.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import me.nullicorn.hypixel4j.response.APIResponse;
import me.nullicorn.hypixel4j.util.FormatCode;
import me.nullicorn.hypixel4j.util.GsonUtil;

/**
 * Created by Ben on 6/8/20 @ 7:25 PM
 */
public class HypixelPlayer extends APIResponse {

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
     * @return This player's total network experience points
     */
    public long getExperience() {
        return getInt("networkExp");
    }

    /**
     * @return This player's total karma count
     */
    public long getKarma() {
        return getInt("karma");
    }

    /**
     * @return Whether or not this player is connected to Hypixel
     * @deprecated This method is not reliable. The recentGames endpoint should be used instead
     */
    @Deprecated
    public boolean isOnline() {
        return getInt("lastLogin") > getInt("lastLogout");
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
            FormatCode.from(getStr("monthlyRankColor", "GOLD")), // Default MVP++ tag color is gold
            FormatCode.from(getStr("rankPlusColor", "RED")) // Default MVP+/MVP++ plus color is light red
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

    // ==================== PROPERTY GETTERS ====================

    // Boolean properties

    /**
     * @return The boolean value of the property, or false if it does not exist
     * @see #getProperty(String)
     */
    public boolean getBool(String name) {
        return GsonUtil.getBool(name, raw);
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    public boolean getBool(String name, boolean def) {
        return GsonUtil.getBool(name, def, raw);
    }

    // Integer properties

    /**
     * @return The integer value of the property, or 0 if it does not exist
     * @see #getProperty(String)
     */
    public long getInt(String name) {
        return GsonUtil.getInt(name, raw);
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    public long getInt(String name, long def) {
        return GsonUtil.getInt(name, def, raw);
    }

    // Floating-point properties

    /**
     * @return The float value of the property, or 0.0D if it does not exist
     * @see #getProperty(String)
     */
    public double getFloat(String name) {
        return GsonUtil.getFloat(name, raw);
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    public double getFloat(String name, double def) {
        return GsonUtil.getFloat(name, def, raw);
    }

    // String properties

    /**
     * @return The string value of the property, or an empty string if it does not exist
     * @see #getProperty(String)
     */
    public String getStr(String name) {
        return GsonUtil.getString(name, raw);
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    public String getStr(String name, String def) {
        return GsonUtil.getString(name, def, raw);
    }

    /**
     * @param name Dot-notation path to the property
     * @return Whether or not the property exists
     */
    public boolean hasProperty(String name) {
        return getProperty(name) != null;
    }

    /**
     * @param name Dot-notation path to the property
     * @return The value of the property, or null if it does not exist
     */
    public JsonElement getProperty(String name) {
        return GsonUtil.get(raw, name);
    }
}
