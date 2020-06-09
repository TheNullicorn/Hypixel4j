package me.nullicorn.hypixel4j;

import java.util.UUID;

/**
 * Created by Ben on 6/8/20 @ 7:02 PM
 */
class HypixelAPITest {

    public static void main(String[] args) {
        if (args.length != 1 || args[0].isEmpty()) {
            System.err.println("Please specify your API key");
            return;
        }

        UUID apiKey = UUID.fromString(args[0]);
        HypixelAPI api = new HypixelAPI(apiKey, "Hypixel4j/0.0.1 (test env)");

        api.getPlayer(UUID.fromString("69de4c87-f9ea-4f22-9851-6e33c4b67a35")).whenComplete((player, error) -> {
            if (error != null) {
                error.printStackTrace();
                return;
            }

            System.out.println("Name:          " + player.getName());
            System.out.println("Rank Prefix:   " + player.getRankPrefix());
            System.out.println("Exp:           " + player.getExperience());
            System.out.println("Karma:         " + player.getKarma());
            System.out.println("Has Rank:      " + player.hasRank());
            System.out.println("Is Build Team: " + player.isOnBuildTeam());
            System.out.println("Online:        " + player.isOnline());

        }).handle((hypixelPlayer, throwable) -> {

            // Shutdown when finished
            api.shutdown();
            return hypixelPlayer;
        });
    }
}