package me.nullicorn.hypixel4j.response.player;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import me.nullicorn.hypixel4j.response.APIResponse;

/**
 * A response from the Hypixel API's /friends endpoint.
 *
 * @see <a href=https://github.com/HypixelDev/PublicAPI/blob/master/Documentation/methods/friends.md>friends.md</a>
 */
public class FriendshipsResponse extends APIResponse<HypixelFriendList> {

    /**
     * The friend list returned by the API
     */
    @Getter
    @SerializedName("records")
    protected HypixelFriendList friendList;

    @Override
    public HypixelFriendList getPayload() {
        return friendList;
    }
}
