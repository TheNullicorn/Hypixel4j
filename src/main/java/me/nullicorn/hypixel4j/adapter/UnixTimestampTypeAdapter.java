package me.nullicorn.hypixel4j.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Date;

/**
 * JSON type adapter for converting java.util.Date -> unix timestamp and vice-verse
 */
public class UnixTimestampTypeAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        jsonWriter.value(date.getTime());
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        return new Date(jsonReader.nextLong());
    }
}
