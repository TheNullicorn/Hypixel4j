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

        api.getPlayer(UUID.fromString("8614fb2d-e71d-4675-95dc-d7da3d977eae")).whenComplete((player, error) -> {
            if (error != null) {
                error.printStackTrace();
                return;
            }

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


        }).handle((hypixelPlayer, throwable) -> {

            // Shutdown when finished
            api.shutdown();
            return hypixelPlayer;
        });
    }
}