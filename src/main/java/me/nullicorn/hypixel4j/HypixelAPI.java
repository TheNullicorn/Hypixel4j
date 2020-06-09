package me.nullicorn.hypixel4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.nullicorn.hypixel4j.exception.ApiException;
import me.nullicorn.hypixel4j.exception.KeyThrottleException;
import me.nullicorn.hypixel4j.response.APIResponse;
import me.nullicorn.hypixel4j.response.player.HypixelPlayer;
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

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected final UUID            apiKey;
    protected final ExecutorService executor;
    protected final HttpClient      httpClient;
    protected final Header[]        requestHeaders;

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

    public CompletableFuture<HypixelPlayer> getPlayer(UUID uuid) {
        return fetch(HypixelPlayer.class, "player", Collections.singletonMap("uuid", formatUuid(uuid)));
    }

    public HypixelPlayer getPlayerSync(UUID uuid) throws ApiException {
        return fetchSync(HypixelPlayer.class, "player", Collections.singletonMap("uuid", formatUuid(uuid)));
    }

    protected <T extends APIResponse> CompletableFuture<T> fetch(Class<T> type, String endpoint, Map<String, Object> params) {
        CompletableFuture<T> future = new CompletableFuture<>();

        executor.submit(() -> {
            try {
                T response = fetchSync(type, endpoint, params);
                future.complete(response);

            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    protected <T extends APIResponse> T fetchSync(final Class<T> type, String endpoint, Map<String, Object> params) throws ApiException {
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
            T apiResponse = gson.fromJson(EntityUtils.toString(response.getEntity()), type);

            if (!apiResponse.isSuccessful()) {
                throw new ApiException(apiResponse.getError());
            } else if (apiResponse.isThrottled()) {
                throw new KeyThrottleException();
            }

            return apiResponse;

        } catch (IOException | URISyntaxException e) {
            throw new ApiException("Failed to make API request", e);
        }
    }

    private String formatUuid(UUID uuid) {
        return uuid.toString().replace("-", "");
    }

    public void shutdown() {
        executor.shutdown();
        System.out.println("Shutdown API executor service");
    }
}
