package me.nullicorn.hypixel4j.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import me.nullicorn.hypixel4j.response.player.HypixelFriendList;
import me.nullicorn.hypixel4j.response.player.HypixelFriendList.Friendship;

/**
 * Json deserializer for Hypixel friend list objects returned from the Hypixel API
 */
public class HypixelFriendListTypeAdapter implements
    JsonDeserializer<HypixelFriendList> {


    @Override
    public HypixelFriendList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

        HypixelFriendList friendList = new HypixelFriendList();
        List<Friendship> friendships = new ArrayList<>();

        if (json != null && json.isJsonArray()) {

            // Deserialize each friendship record
            json.getAsJsonArray().forEach(friendShipJson -> {
                Friendship friendship = context.deserialize(friendShipJson, Friendship.class);
                friendship.setFriendList(friendList);
                friendships.add(friendship);
            });
        }

        friendList.setFriendships(friendships);
        return friendList;
    }
}
