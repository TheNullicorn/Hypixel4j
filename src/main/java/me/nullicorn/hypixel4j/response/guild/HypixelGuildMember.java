package me.nullicorn.hypixel4j.response.guild;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import me.nullicorn.hypixel4j.HypixelAPI;
import me.nullicorn.hypixel4j.exception.ApiException;
import me.nullicorn.hypixel4j.response.player.HypixelPlayer;

/**
 * Represents a member of a Hypixel guild
 */
public class HypixelGuildMember {

    protected HypixelGuildMember() {
    }

    @Getter
    protected UUID uuid;

    @SerializedName("rank")
    protected String guildRank;

    @Getter
    @SerializedName("joined")
    protected Date joinDate;

    @Getter
    protected int questParticipation;

    /**
     * @param guild The guild that the member is apart of
     * @return Whether or not this member is the guildmaster of their guild
     */
    public boolean isGuildMaster(HypixelGuild guild) {
        return getGuildRank(guild) == HypixelGuildRank.GUILDMASTER_RANK;
    }

    /**
     * Get this guild member's entire player object. Depending on the provided API implementation,
     * this may use up a query
     *
     * @param api Hypixel API instance to use when requesting this member's player object
     * @return The full player object associated with this member's UUID
     * @throws ApiException If the provided API instance failed to retrieve the player
     */
    public HypixelPlayer getAsPlayer(HypixelAPI api) throws ApiException {
        return api.getPlayer(uuid);
    }

    /**
     * @param guild The player's guild
     * @return This member's guild rank, or {@link HypixelGuildRank#OLD_MEMBER_RANK} if it could not
     * be determined
     */
    public HypixelGuildRank getGuildRank(HypixelGuild guild) {
        if (guild.ranks != null && !guild.ranks.isEmpty()) {

            for (HypixelGuildRank rank : guild.ranks) {
                if (rank.name.equalsIgnoreCase(this.guildRank)) {
                    return rank;
                }
            }
        }

        switch (this.guildRank.toLowerCase()) {
            case "guildmaster":
            case "guild master":
                return HypixelGuildRank.GUILDMASTER_RANK;

            case "officer":
                return HypixelGuildRank.OLD_OFFICER_RANK;

            default:
                return HypixelGuildRank.OLD_MEMBER_RANK;
        }
    }

    @Override
    public String toString() {
        return "HypixelGuildMember{" +
            "uuid=" + uuid +
            ", guildRankName='" + guildRank + '\'' +
            ", joinDate=" + joinDate +
            ", questParticipation=" + questParticipation +
            '}';
    }
}
