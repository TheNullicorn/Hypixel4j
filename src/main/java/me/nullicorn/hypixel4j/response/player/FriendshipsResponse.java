package me.nullicorn.hypixel4j.response.player;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import me.nullicorn.hypixel4j.response.APIResponse;

/**
 * Created by Ben on 6/18/20 @ 5:55 PM
 */
public class FriendshipsResponse extends APIResponse<HypixelFriendList> {

    @Getter
    @SerializedName("records")
    protected HypixelFriendList friendList;

    @Override
    public HypixelFriendList getPayload() {
        return friendList;
    }
}
