package me.nullicorn.hypixel4j.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.UUID;
import me.nullicorn.hypixel4j.util.UuidUtil;

/**
 * Created by Ben on 6/10/20 @ 9:40 AM
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
