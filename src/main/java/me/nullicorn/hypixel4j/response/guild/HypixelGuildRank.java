package me.nullicorn.hypixel4j.response.guild;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.nullicorn.hypixel4j.data.HypixelObject;

/**
 * Represents a rank within a Hypixel guild
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HypixelGuildRank implements HypixelObject {

    /**
     * Since the guild master rank is not explicitly defined in a guild object, we declare it here
     * just as a placeholder
     */
    protected static final HypixelGuildRank GUILDMASTER_RANK = new HypixelGuildRank(
        "Guild Master",
        "GM",
        false,
        Integer.MAX_VALUE,
        new Date(0)
    );

    /**
     * Represents the default Officer rank before the guild update introduced custom ranks
     */
    protected static final HypixelGuildRank OLD_OFFICER_RANK = new HypixelGuildRank(
        "Officer",
        null,
        false,
        Integer.MAX_VALUE / 2,
        new Date(0)
    );

    /**
     * Represents the default Member rank before the guild update introduced custom ranks
     */
    protected static final HypixelGuildRank OLD_MEMBER_RANK = new HypixelGuildRank(
        "Member",
        null,
        true,
        0,
        new Date(0)
    );

    /**
     * A list of default guild ranks for old guilds that haven't created custom ranks after the
     * guild update
     */
    protected static List<HypixelGuildRank> defaultRankList;

    static {
        defaultRankList = new ArrayList<>();
        defaultRankList.add(OLD_OFFICER_RANK);
        defaultRankList.add(OLD_MEMBER_RANK);
        defaultRankList = Collections.unmodifiableList(defaultRankList);
    }

    protected HypixelGuildRank() {
    }

    protected String name;

    @SerializedName("tag")
    protected String chatTag;

    @SerializedName("default")
    protected boolean isDefaultRank;

    protected int priority;

    @SerializedName("created")
    protected Date creationDate;

    @Override
    public String toString() {
        return "HypixelGuildRank{" +
            "name='" + name + '\'' +
            ", chatTag='" + chatTag + '\'' +
            ", isDefaultRank=" + isDefaultRank +
            ", priority=" + priority +
            ", createdAt=" + creationDate +
            '}';
    }
}
