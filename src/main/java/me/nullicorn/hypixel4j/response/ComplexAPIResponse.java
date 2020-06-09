package me.nullicorn.hypixel4j.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.nullicorn.hypixel4j.data.WrappedJsonObject;
import me.nullicorn.hypixel4j.util.GsonUtil;

/**
 * Created by Ben on 6/9/20 @ 6:31 AM
 */
public abstract class ComplexAPIResponse extends APIResponse implements WrappedJsonObject {

    public abstract JsonObject getRaw();

    /**
     * @return The boolean value of the property, or false if it does not exist
     * @see #getProperty(String)
     */
    @Override
    public boolean getBool(String name) {
        return GsonUtil.getBool(name, getRaw());
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    @Override
    public boolean getBool(String name, boolean def) {
        return GsonUtil.getBool(name, def, getRaw());
    }

    // Long integer properties

    /**
     * @return The long integer value of the property, or 0 if it does not exist
     * @see #getProperty(String)
     */
    @Override
    public long getLong(String name) {
        return GsonUtil.getLong(name, getRaw());
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    @Override
    public long getLong(String name, long def) {
        return GsonUtil.getLong(name, def, getRaw());
    }

    // Integer properties

    /**
     * @return The integer value of the property, or 0 if it does not exist
     * @see #getProperty(String)
     */
    @Override
    public long getInt(String name) {
        return GsonUtil.getInt(name, getRaw());
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    @Override
    public long getInt(String name, int def) {
        return GsonUtil.getInt(name, def, getRaw());
    }

    // Floating-point properties

    /**
     * @return The float value of the property, or 0.0D if it does not exist
     * @see #getProperty(String)
     */
    @Override
    public double getFloat(String name) {
        return GsonUtil.getFloat(name, getRaw());
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    @Override
    public double getFloat(String name, double def) {
        return GsonUtil.getFloat(name, def, getRaw());
    }

    // String properties

    /**
     * @return The string value of the property, or an empty string if it does not exist
     * @see #getProperty(String)
     */
    @Override
    public String getStr(String name) {
        return GsonUtil.getString(name, getRaw());
    }

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    @Override
    public String getStr(String name, String def) {
        return GsonUtil.getString(name, def, getRaw());
    }

    /**
     * @param name Dot-notation path to the property
     * @return Whether or not the property exists
     */
    @Override
    public boolean hasProperty(String name) {
        return getProperty(name) != null;
    }

    /**
     * @param name Dot-notation path to the property
     * @return The value of the property, or null if it does not exist
     */
    @Override
    public JsonElement getProperty(String name) {
        return GsonUtil.get(getRaw(), name);
    }
}
