package me.nullicorn.hypixel4j.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Ben on 6/9/20 @ 6:27 AM
 */
public interface WrappedJsonObject {

    JsonObject getRaw();

    /**
     * @return The boolean value of the property, or false if it does not exist
     * @see #getProperty(String)
     */
    boolean getBool(String name);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    boolean getBool(String name, boolean def);

    // Integer properties

    /**
     * @return The integer value of the property, or 0 if it does not exist
     * @see #getProperty(String)
     */
    long getInt(String name);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    long getInt(String name, long def);

    // Floating-point properties

    /**
     * @return The float value of the property, or 0.0D if it does not exist
     * @see #getProperty(String)
     */
    double getFloat(String name);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    double getFloat(String name, double def);

    // String properties

    /**
     * @return The string value of the property, or an empty string if it does not exist
     * @see #getProperty(String)
     */
    String getStr(String name);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    String getStr(String name, String def);

    /**
     * @param name Dot-notation path to the property
     * @return Whether or not the property exists
     */
    boolean hasProperty(String name);

    /**
     * @param name Dot-notation path to the property
     * @return The value of the property, or null if it does not exist
     */
    JsonElement getProperty(String name);
}
