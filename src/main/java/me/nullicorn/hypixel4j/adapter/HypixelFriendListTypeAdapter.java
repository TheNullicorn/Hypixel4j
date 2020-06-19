package me.nullicorn.hypixel4j.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import me.nullicorn.hypixel4j.response.player.HypixelFriendList;
import me.nullicorn.hypixel4j.response.player.HypixelFriendList.Friendship;

/**
 * Created by Ben on 6/18/20 @ 6:21 PM
 */
public class HypixelFriendListTypeAdapter implements
    JsonDeserializer<HypixelFriendList> {


    @Override
    public HypixelFriendList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

        List<Friendship> friendships = new ArrayList<>();

        if (json != null && json.isJsonArray()) {

            // Deserialize each friendship record
            json.getAsJsonArray().forEach(friendShipJson ->
                friendships.add(context.deserialize(friendShipJson, Friendship.class)));
        }

        HypixelFriendList friendList = new HypixelFriendList();
        try {
            // Attempt to set the friend list using reflection
            Field friendshipsField = friendList.getClass().getDeclaredField("friendships");
            friendshipsField.setAccessible(true);
            friendshipsField.set(friendList, friendships);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return friendList;
    }
}
