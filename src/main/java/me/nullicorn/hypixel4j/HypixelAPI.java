package me.nullicorn.hypixel4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.nullicorn.hypixel4j.adapter.HypixelFriendListTypeAdapter;
import me.nullicorn.hypixel4j.adapter.HypixelPlayerTypeAdapter;
import me.nullicorn.hypixel4j.adapter.TrimmedUUIDTypeAdapter;
import me.nullicorn.hypixel4j.adapter.UnixTimestampTypeAdapter;
import me.nullicorn.hypixel4j.data.HypixelObject;
import me.nullicorn.hypixel4j.exception.ApiException;
import me.nullicorn.hypixel4j.exception.KeyThrottleException;
import me.nullicorn.hypixel4j.response.APIResponse;
import me.nullicorn.hypixel4j.response.guild.GuildResponse;
import me.nullicorn.hypixel4j.response.guild.HypixelGuild;
import me.nullicorn.hypixel4j.response.player.FriendshipsResponse;
import me.nullicorn.hypixel4j.response.player.HypixelFriendList;
import me.nullicorn.hypixel4j.response.player.HypixelPlayer;
import me.nullicorn.hypixel4j.response.player.PlayerResponse;
import me.nullicorn.hypixel4j.util.UuidUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

/**
 * Created by Ben on 6/8/20 @ 5:47 PM
 */
public class HypixelAPI {

    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(Date.class, new UnixTimestampTypeAdapter())
        .registerTypeAdapter(UUID.class, new TrimmedUUIDTypeAdapter())
        .registerTypeAdapter(HypixelPlayer.class, new HypixelPlayerTypeAdapter())
        .registerTypeAdapter(HypixelFriendList.class, new HypixelFriendListTypeAdapter())
        .setPrettyPrinting()
        .create();

    protected final UUID            apiKey;
    protected final ExecutorService executor;
    protected final HttpClient      httpClient;
    protected final Header[]        requestHeaders;

    /**
     * Construct using the designated API key. Te default User-Agent is used
     *
     * @param apiKey API key used to access the Hypixel API
     */
    public HypixelAPI(UUID apiKey) {
        this(apiKey, "Hypixel4j/0.0.1 (Generic Application)");
    }

    /**
     * Construct using the designated API key
     *
     * @param apiKey    API key used to access the Hypixel API
     * @param userAgent User-agent sent with requests to the API
     */
    public HypixelAPI(UUID apiKey, String userAgent) {
        this.apiKey = apiKey;
        executor = Executors.newCachedThreadPool();
        httpClient = HttpClients.createMinimal();

        // Set headers to be used on all requests
        requestHeaders = new Header[3];
        requestHeaders[0] = new BasicHeader("User-Agent", userAgent);
        requestHeaders[1] = new BasicHeader("Accept", "application/json");
        requestHeaders[2] = new BasicHeader("Cache-Control", "no-store");
    }

    /**
     * Async method for getting a Hypixel player
     *
     * @see #getPlayer(UUID) Synchronous version
     */
    public CompletableFuture<HypixelPlayer> getPlayerAsync(UUID uuid) {
        return fetchAsync(PlayerResponse.class, "player",
            Collections.singletonMap("uuid", UuidUtil.undash(uuid)));
    }

    /**
     * Get a player object from the Hypixel API
     *
     * @param uuid Minecraft UUID of the requested player
     * @return A HypixelPlayer object representing the requested player
     * @throws ApiException If the request could not be made, or if the Hypixel API returned an
     *                      error
     */
    public HypixelPlayer getPlayer(UUID uuid) throws ApiException {
        return fetch(PlayerResponse.class, "player",
            Collections.singletonMap("uuid", UuidUtil.undash(uuid)));
    }

    /**
     * Get a guild from the Hypixel API by its ID
     *
     * @param guildId Guild ID of the desired guild; should be a valid <a href=https://docs.mongodb.com/manual/reference/bson-types/#objectid>BSON
     *                ObjectId</a>
     * @return A HypixelGuild representing the requested guild; be sure to check that {@link
     * HypixelGuild#exists()}
     * @throws ApiException If the request could not be made, or if the Hypixel API returned an
     *                      error
     */
    public HypixelGuild getGuildById(String guildId) throws ApiException {
        if (!guildId.matches("[A-Fa-f0-9]{24}")) {
            throw new IllegalArgumentException("Malformed guild id");
        }
        return fetch(GuildResponse.class, "guild",
            Collections.singletonMap("id", guildId));
    }

    /**
     * Get a guild from the Hypixel API by its name
     *
     * @param guildName Name of the desired guild; case-insensitive
     * @return A HypixelGuild representing the requested guild; be sure to check that {@link
     * HypixelGuild#exists()}
     * @throws ApiException If the request could not be made, or if the Hypixel API returned an
     *                      error
     */
    public HypixelGuild getGuildByName(String guildName) throws ApiException {
        if (!guildName.matches("[\\w ]{3,30}")) {
            throw new IllegalArgumentException("Malformed guild name");
        }
        return fetch(GuildResponse.class, "guild",
            Collections.singletonMap("name", guildName));
    }

    /**
     * Get a guild from the Hypixel API by one of its members' Minecraft UUIDs
     *
     * @param memberUuid Minecraft UUID of a member of the guild
     * @return A HypixelGuild representing the requested guild; be sure to check that {@link
     * HypixelGuild#exists()}
     * @throws ApiException If the request could not be made, or if the Hypixel API returned an
     *                      error
     */
    public HypixelGuild getGuildByPlayer(UUID memberUuid) throws ApiException {
        return fetch(GuildResponse.class, "guild",
            Collections.singletonMap("player", UuidUtil.undash(memberUuid)));
    }

    /**
     * Get a player's friend list from the Hypixel API
     *
     * @param playerUuid Minecraft UUID of a player
     * @return The Hypixel friend list of the desired player
     * @throws ApiException If the request could not be made, or if the Hypixel API returned an
     *                      error
     */
    public HypixelFriendList getPlayerFriendList(UUID playerUuid) throws ApiException {
        HypixelFriendList friendList = fetch(FriendshipsResponse.class, "friends",
            Collections.singletonMap("uuid", UuidUtil.undash(playerUuid)));

        if (friendList != null) {
            friendList.setOwner(playerUuid);
        }
        return friendList;
    }

    protected <T extends HypixelObject> CompletableFuture<T> fetchAsync(Class<? extends APIResponse<T>> type, String endpoint, Map<String, Object> params) {
        CompletableFuture<T> future = new CompletableFuture<>();

        executor.submit(() -> {
            try {
                T response = fetch(type, endpoint, params);
                future.complete(response);

            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    protected <T extends HypixelObject> T fetch(final Class<? extends APIResponse<T>> type, String endpoint, Map<String, Object> params)
        throws ApiException {
        try {
            // Build request url
            URIBuilder ub = new URIBuilder()
                .setScheme("https")
                .setHost("api.hypixel.net")
                .setPath(endpoint)
                .setParameter("key", apiKey.toString());
            params.forEach((key, value) -> ub.setParameter(key, value.toString()));

            HttpGet request = new HttpGet(ub.build());
            request.setHeaders(requestHeaders);

            HttpResponse response = httpClient.execute(request);
            APIResponse<T> apiResponse = gson
                .fromJson(EntityUtils.toString(response.getEntity()), type);

            if (apiResponse.isThrottled()) {
                // Key throttled
                throw new KeyThrottleException();

            } else if (!apiResponse.isSuccessful()) {
                // Other failure
                throw new ApiException(apiResponse.getError());
            }

            // Successful response
            return apiResponse.getPayload();

        } catch (IOException | URISyntaxException e) {
            throw new ApiException("Failed to make API request", e);
        }
    }

    public void shutdown() {
        executor.shutdown();
        System.out.println("Shutdown API executor service");
    }
}
