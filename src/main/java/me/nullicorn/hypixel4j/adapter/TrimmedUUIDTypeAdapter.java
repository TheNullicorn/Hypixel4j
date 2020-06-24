package me.nullicorn.hypixel4j.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.UUID;
import me.nullicorn.hypixel4j.util.UuidUtil;

/**
 * Json type adapter for UUID strings without hyphens
 */
public class TrimmedUUIDTypeAdapter extends TypeAdapter<UUID> {

    @Override
    public void write(JsonWriter jsonWriter, UUID uuid) throws IOException {
        jsonWriter.value(UuidUtil.undash(uuid));
    }

    @Override
    public UUID read(JsonReader jsonReader) throws IOException {
        return UuidUtil.fromUndashed(jsonReader.nextString());
    }
}
