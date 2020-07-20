package me.nullicorn.hypixel4j.response.player;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.nullicorn.hypixel4j.data.HypixelObject;
import me.nullicorn.hypixel4j.response.APIResponse;
import me.nullicorn.hypixel4j.response.player.RecentGamesResponse.HypixelGameSessionList;

public class RecentGamesResponse extends APIResponse<HypixelGameSessionList> {

    @SerializedName("games")
    protected HypixelGameSessionList recentGamesList;

    @Override
    public HypixelGameSessionList getPayload() {
        return recentGamesList;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HypixelGameSessionList extends ArrayList<HypixelGameSession> implements
        HypixelObject {

    }
}
