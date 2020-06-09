package me.nullicorn.hypixel4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import me.nullicorn.hypixel4j.exception.ApiException;
import me.nullicorn.hypixel4j.response.player.HypixelPlayer;
import net.jodah.concurrentunit.Waiter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Created by Ben on 6/8/20 @ 7:02 PM
 */
class HypixelAPITest {

    static HypixelAPI api;
    static UUID       sampleUuid;

    @BeforeAll
    public static void createApi() {
        String keyValue = System.getProperty("apiKey");

        if (keyValue == null || keyValue.isEmpty()) {
            System.err.println("\"apiKey\" property must be set!");

        } else {
            api = new HypixelAPI(UUID.fromString(keyValue), "Hypixel4j/0.0.1 (test env)");
        }

        sampleUuid = UUID.fromString("8614fb2d-e71d-4675-95dc-d7da3d977eae");
    }

    @Test
    public void testFetchPlayerSync() throws ApiException {
        HypixelPlayer player = api.getPlayerSync(sampleUuid);
        printPlayer(player);
    }

    @Test
    public void testFetchPlayerAsync() throws InterruptedException, TimeoutException {
        final Waiter waiter = new Waiter();

        api.getPlayer(sampleUuid).whenComplete((player, error) -> {
            if (error != null) {
                error.printStackTrace();
                return;
            }
            printPlayer(player);
            waiter.resume();
        });

        waiter.await(10, TimeUnit.SECONDS);
    }

    private static void printPlayer(HypixelPlayer player) {
        System.out.println("Display Name:     " + player.getRankPrefix() + " " + player.getName());
        System.out.println("Exp:              " + player.getExperience());
        System.out.println("Karma:            " + player.getKarma());
        System.out.println("Has Rank:         " + player.hasRank());
        System.out.println("Is Build Team:    " + player.isOnBuildTeam());
        System.out.println("-------------------------");
        System.out.println("Online:           " + player.isOnline());
        System.out.println("First Joined:     " + player.getFirstLogin());
        System.out.println("Last Joined:      " + player.getLastLogin());
        System.out.println("Last Quit:        " + player.getLastLogout());
        System.out.println("Most Recent Game: " + player.getMostRecentGameType());
        System.out.println("-------------------------");
    }
}