package me.nullicorn.hypixel4j.response.guild;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

    @Getter
    @SerializedName("_id")
    private String id;

    @Getter
    private String name;

    private String     tag;
    private FormatCode tagColor;

    @Getter
    private String description;

    @Getter
    private List<GameType> preferredGames;

    @Getter
    private List<Member> members;

    @Getter
    private List<Rank> ranks;

    @Getter
    @SerializedName("exp")
    private long experience;

    @Getter
    @SerializedName("created")
    private Date createdAt;

    // Privacy settings
    @Getter
    private boolean joinable;

    @Getter
    private boolean publiclyListed;

    // Discontinued
    @Getter
    private long coins;

    @Getter
    private long coinsEver;

    @Getter
    @SerializedName("legacyRanking")
    private long oldRank;

    /**
     * @return Whether or not this guild exists
     */
    public boolean exists() {
        return id != null;
    }

    /**
     * Represents a member of a Hypixel guild
     */
    public class Member {

        @Getter
        private UUID uuid;

        @SerializedName("rank")
        private String guildRank;

        @Getter
        @SerializedName("joined")
        private Date joinDate;

        @Getter
        private int questParticipation;

        /**
         * @return This member's guild rank, or null if it could not be determined
         */
        public Rank getGuildRank() {
            if (ranks == null) {
                return null;
            }

            for (Rank rank : ranks) {
                if (rank.name.equalsIgnoreCase(this.guildRank)) {
                    return rank;
                }
            }

            return null;
        }
    }

    /**
     * Represents a guild rank within a Hypixel guild
     */
    @Getter
    public class Rank {

        private String  name;
        @SerializedName("tag")
        private String  chatTag;
        @SerializedName("boolean")
        private boolean isDefaultRank;
        private int     priority;
        @SerializedName("created")
        private Date    creationDate;
    }
}
