package me.nullicorn.hypixel4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import me.nullicorn.hypixel4j.exception.ApiException;
import me.nullicorn.hypixel4j.response.guild.HypixelGuild;
import me.nullicorn.hypixel4j.response.player.HypixelPlayer;
import net.jodah.concurrentunit.Waiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Created by Ben on 6/8/20 @ 7:02 PM
 */
class HypixelAPITest {

    /**
     * A basic HypixelAPI instance used to test requests
     */
    private static HypixelAPI api;

    @BeforeAll
    static void createApi() {
        String keyValue = System.getenv("API_KEY");

        if (keyValue == null || keyValue.isEmpty()) {
            System.err.println("\"apiKey\" property must be set!");

        } else {
            api = new HypixelAPI(UUID.fromString(keyValue), "Hypixel4j/0.0.1 (test env)");
        }
    }

    /*
    Fetch Player
     */

    @Test
    void test_fetchPlayer_Sync() throws ApiException {
        HypixelPlayer player = api.getPlayer(APITestConstants.SAMPLE_PLAYER_UUID);
        Assertions.assertTrue(player.exists());
        Assertions.assertEquals(APITestConstants.SAMPLE_PLAYER_UUID, player.getUuid());
    }

    @Test
    void test_fetchPlayer_Async() throws InterruptedException, TimeoutException {
        final Waiter waiter = new Waiter();

        api.getPlayerAsync(APITestConstants.SAMPLE_PLAYER_UUID).whenComplete((player, error) -> {
            if (error != null) {
                waiter.fail(error);
                return;
            }

            try {
                Assertions.assertTrue(player.exists());
                Assertions.assertEquals(APITestConstants.SAMPLE_PLAYER_UUID, player.getUuid());
                waiter.resume();
            } catch (Throwable t) {
                waiter.fail(t);
            }
        });

        waiter.await(10, TimeUnit.SECONDS);
    }

    @Test
    void test_fetchPlayer_Sync_NonExistent() throws ApiException {
        HypixelPlayer player = api.getPlayer(APITestConstants.NULL_PLAYER_UUID);
        Assertions.assertFalse(player.exists());
    }

    /*
    Fetch Guild
     */

    @Test
    void test_fetchGuildSync_ById() throws ApiException {
        HypixelGuild guild = api.getGuildById(APITestConstants.SAMPLE_GUILD_ID);
        Assertions.assertTrue(guild.exists());
        Assertions.assertEquals(APITestConstants.SAMPLE_GUILD_ID, guild.getId());
    }
}