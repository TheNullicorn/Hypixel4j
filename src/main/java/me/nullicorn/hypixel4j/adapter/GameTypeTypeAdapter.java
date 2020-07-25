package me.nullicorn.hypixel4j.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import me.nullicorn.hypixel4j.util.GameType;

/**
 * Json deserializer for Hypixel GameTypes used in the API
 */
public class GameTypeTypeAdapter implements JsonDeserializer<GameType> {

    @Override
    public GameType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (json == null || !json.isJsonPrimitive()) {
            return GameType.UNKNOWN;
        }

        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        if (jsonPrimitive.isString()) {
            // If the input is not a GameType's type name (all caps), try using it as a db name
            GameType type = GameType.fromTypeName(jsonPrimitive.getAsString());
            if (type == GameType.UNKNOWN) {
                type = GameType.fromDbName(jsonPrimitive.getAsString());
            }
            return type;

        } else if (jsonPrimitive.isNumber()) {
            // Get the gameType by ID
            return GameType.fromId(jsonPrimitive.getAsNumber().intValue());
        }
        return GameType.UNKNOWN;
    }
}
