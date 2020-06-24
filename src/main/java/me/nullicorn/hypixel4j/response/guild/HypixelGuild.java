package me.nullicorn.hypixel4j.response.guild;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import me.nullicorn.hypixel4j.data.HypixelObject;
import me.nullicorn.hypixel4j.util.FormatCode;
import me.nullicorn.hypixel4j.util.GameType;

/**
 * Represents a Hypixel guild
 */
public class HypixelGuild implements HypixelObject {

    protected HypixelGuild() {
    }

    /**
     * The internal ID of this Hypixel guild; a valid <a href=https://docs.mongodb.com/manual/reference/bson-types/#objectid>BSON
     * ObjectId</a>
     */
    @Getter
    @SerializedName("_id")
    protected String id;

    /**
     * The guild's name; an alphanumeric string between 1 and 32 (both inclusive) characters long
     */
    @Getter
    protected String name;

    protected String     tag;
    protected FormatCode tagColor;

    /**
     * The public description for this guild. This appears to be able to contain any characters that
     * can be typed in Minecraft and can be up to 232 charachters long (256 total characters for a
     * Minecraft command, minus 24 for "/g settings description ")
     */
    @Getter
    protected String description;

    @Getter
    protected List<GameType> preferredGames;

    @Getter
    protected List<HypixelGuildMember> members;

    protected List<HypixelGuildRank> ranks;

    @Getter
    @SerializedName("exp")
    protected long experience;

    @Getter
    @SerializedName("created")
    protected Date createdAt;

    // Privacy settings
    @Getter
    protected boolean joinable;

    @Getter
    protected boolean publiclyListed;

    // Discontinued
    @Getter
    protected long coins;

    @Getter
    protected long coinsEver;

    @Getter
    @SerializedName("legacyRanking")
    protected long oldGuildRanking;

    /**
     * @return Whether or not this guild exists
     */
    public boolean exists() {
        return id != null;
    }

    /**
     * @return A list of ranks that can be held by this guild's members. If this guild has no ranks,
     * a list of pre-guild-update ranks are used
     */
    public List<HypixelGuildRank> getRanks() {
        if (ranks == null || ranks.isEmpty()) {
            return HypixelGuildRank.defaultRankList;
        }
        return ranks;
    }

    @Override
    public String toString() {
        return "HypixelGuild{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", tag='" + tag + '\'' +
            ", tagColor=" + tagColor +
            ", description='" + description + '\'' +
            ", preferredGames=" + preferredGames +
            ", members=" + members +
            ", ranks=" + ranks +
            ", experience=" + experience +
            ", createdAt=" + createdAt +
            ", joinable=" + joinable +
            ", publiclyListed=" + publiclyListed +
            ", coins=" + coins +
            ", coinsEver=" + coinsEver +
            ", oldGuildRanking=" + oldGuildRanking +
            '}';
    }
}
