package me.nullicorn.hypixel4j.response.guild;

import me.nullicorn.hypixel4j.response.APIResponse;

/**
 * A response from the Hypixel API's /guild endpoint.
 *
 * @see <a href=https://github.com/HypixelDev/PublicAPI/blob/master/Documentation/methods/guild.md>guild.md</a>
 */
public class GuildResponse extends APIResponse<HypixelGuild> {

    private static final HypixelGuild EMPTY_GUILD = new HypixelGuild();

    protected HypixelGuild guild;

    /**
     * @return The guild object returned by the API, or an empty guild if the API returned null
     */
    @Override
    public HypixelGuild getPayload() {
        return guild == null ? EMPTY_GUILD : guild;
    }
}
