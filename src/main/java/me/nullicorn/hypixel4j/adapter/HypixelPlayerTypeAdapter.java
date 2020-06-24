package me.nullicorn.hypixel4j.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import me.nullicorn.hypixel4j.response.player.HypixelPlayer;

/**
 * Json serializer and deserializer for Hypixel player objects returned from the Hypixel API
 */
public class HypixelPlayerTypeAdapter implements JsonSerializer<HypixelPlayer>,
    JsonDeserializer<HypixelPlayer> {

    @Override
    public JsonElement serialize(HypixelPlayer hypixelPlayer, Type type, JsonSerializationContext jsonSerializationContext) {
        return hypixelPlayer.getRaw();
    }

    @Override
    public HypixelPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {

        if (jsonElement != null && jsonElement.isJsonObject()) {
            return new HypixelPlayer(jsonElement.getAsJsonObject());
        }
        return new HypixelPlayer(null);
    }
}
