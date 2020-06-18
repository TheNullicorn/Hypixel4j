package me.nullicorn.hypixel4j.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.nullicorn.hypixel4j.util.GsonUtil;

/**
 * Created by Ben on 6/9/20 @ 6:31 AM
 */
public abstract class ComplexHypixelObject implements WrappedJsonObject, HypixelObject {

    @Override
    public double getDoubleProperty(String key, double def) {
        return GsonUtil.getFloat(key, def, getRaw());
    }

    @Override
    public float getFloatProperty(String key, float def) {
        return GsonUtil.getNumber(key, def, getRaw()).floatValue();
    }

    @Override
    public long getLongProperty(String key, long def) {
        return GsonUtil.getLong(key, def, getRaw());
    }

    @Override
    public int getIntProperty(String key, int def) {
        return GsonUtil.getInt(key, def, getRaw());
    }

    @Override
    public Number getNumberProperty(String key, Number def) {
        return GsonUtil.getNumber(key, def, getRaw());
    }

    @Override
    public String getStringProperty(String key, String def) {
        return GsonUtil.getString(key, def, getRaw());
    }

    @Override
    public boolean getBoolProperty(String key, boolean def) {
        return GsonUtil.getBool(key, def, getRaw());
    }

    @Override
    public JsonArray getArrayProperty(String key) {
        JsonElement element = GsonUtil.get(getRaw(), key);
        if (element != null && element.isJsonArray()) {
            return element.getAsJsonArray();
        }
        return null;
    }

    @Override
    public JsonObject getObjectProperty(String key) {
        JsonElement element = GsonUtil.get(getRaw(), key);
        if (element != null && element.isJsonObject()) {
            return element.getAsJsonObject();
        }
        return null;
    }

    @Override
    public JsonElement getProperty(String key) {
        return GsonUtil.get(getRaw(), key);
    }

    @Override
    public boolean hasProperty(String key) {
        JsonElement property = getProperty(key);
        return property != null && !property.isJsonNull();
    }
}
