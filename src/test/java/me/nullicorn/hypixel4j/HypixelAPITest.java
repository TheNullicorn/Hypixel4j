package me.nullicorn.hypixel4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import me.nullicorn.hypixel4j.exception.ApiException;
import me.nullicorn.hypixel4j.response.booster.BoosterResponse;
import me.nullicorn.hypixel4j.response.gamecounts.GameCountsResponse;
import me.nullicorn.hypixel4j.response.gamecounts.HypixelGameCounts;
import me.nullicorn.hypixel4j.response.guild.HypixelGuild;
import me.nullicorn.hypixel4j.response.player.HypixelFriendList;
import me.nullicorn.hypixel4j.response.player.HypixelPlayer;
import me.nullicorn.hypixel4j.response.player.HypixelPlayerSession;
import me.nullicorn.hypixel4j.response.player.RecentGamesResponse.HypixelGameSessionList;
import net.jodah.concurrentunit.Waiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
            api = new HypixelAPI(UUID.fromString(keyValue), "Hypixel4j/0.0.3 (test env)");
        }
    }

    /*
    Fetch Player
     */

    @Test
    void test_fetchPlayer_Sync() throws ApiException {
        HypixelPlayer player = api.getPlayer(APITestConstants.HYPIXEL_UUID);
        System.out.println(player);

        Assertions.assertTrue(player.exists());
        Assertions.assertEquals(APITestConstants.HYPIXEL_UUID, player.getUuid());
    }

    @Test
    void test_fetchPlayer_Async() throws InterruptedException, TimeoutException {
        final Waiter waiter = new Waiter();

        api.getPlayerAsync(APITestConstants.HYPIXEL_UUID).whenComplete((player, error) -> {
            if (error != null) {
                waiter.fail(error);
                return;
            }

            try {
                System.out.println(player);

                Assertions.assertTrue(player.exists());
                Assertions.assertEquals(APITestConstants.HYPIXEL_UUID, player.getUuid());
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
        System.out.println(player);

        Assertions.assertFalse(player.exists());
    }

    /*
    Fetch Guild
     */

    @Test
    void test_fetchGuildSync_ById() throws ApiException {
        HypixelGuild guild = api.getGuildById(APITestConstants.SAMPLE_GUILD_ID);
        System.out.println(guild);

        Assertions.assertTrue(guild.exists());
        Assertions.assertEquals(APITestConstants.SAMPLE_GUILD_ID, guild.getId());
    }

    /*
    Fetch Friend List
     */

    @Test
    void test_fetchFriendList_Sync() throws ApiException {
        HypixelFriendList friendList = api.getPlayerFriendList(APITestConstants.HYPIXEL_UUID);
        Assertions.assertEquals(APITestConstants.HYPIXEL_UUID, friendList.getOwnerUuid());
        Assertions.assertTrue(friendList.getSize() > 0);

        System.out.println("Player '" + friendList.getOwnerUuid() + "' has "
            + friendList.getSize() + " friends:");
        friendList.getFriendships().forEach(friendship -> {
            System.out.print(friendship.wasOutgoing() ? "Sent:     " : "Received: ");
            System.out.print(friendship.getOtherPlayerUuid());
            System.out.println(" @ " + friendship.getStartDate());
        });
    }

    /*
    Fetch Session Status
     */

    @Test
    void test_fetchPlayerSessionStatus_Sync() throws ApiException {
        HypixelPlayerSession session = api.getPlayerSession(APITestConstants.SAMPLE_PLAYER_UUID);
        System.out.println(session);

        Assertions.assertNotNull(session);
    }

    /*
    Fetch Recent Games
     */

    @Test
    void test_fetchPlayerRecentGames_Sync() throws ApiException {
        HypixelGameSessionList recentGames
            = api.getPlayerRecentGames(APITestConstants.SAMPLE_PLAYER_UUID);
        System.out.println(recentGames);

        Assertions.assertNotNull(recentGames);
    }

    /*
    Fetch Booster Data
     */

    @Test
    void test_fetchBoosterData_Sync() throws ApiException {
        BoosterResponse boosterData = api.getBoosterData();
        System.out.println(boosterData);

        Assertions.assertNotNull(boosterData);
        Assertions.assertFalse(boosterData.getAllBoosters().isEmpty());
    }

    /*
    Fetch Player Counts
     */

    @Test
    void test_fetchGameCounts_Sync() throws ApiException {
        GameCountsResponse gameCounts = api.getGameCounts();
        System.out.println(gameCounts);

        Assertions.assertNotNull(gameCounts);
        Assertions.assertFalse(gameCounts.getGames().isEmpty());

        for (String gameName : gameCounts.getGames()) {
            HypixelGameCounts game = gameCounts.getGameCountsFor(gameName);

            System.out.println(gameName + ": " + game.getTotalPlayers() + " playing");
            game.getModeMap().forEach((modeName, playersInMode) -> {
                System.out.println("    - " + modeName + ": " + playersInMode + " playing");
            });
            System.out.println();
        }
    }
}