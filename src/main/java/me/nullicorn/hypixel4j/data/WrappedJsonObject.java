package me.nullicorn.hypixel4j.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Represents a JsonObject with fields accessible via dot-notation
 */
public interface WrappedJsonObject {

    JsonObject getRaw();

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    double getDoubleProperty(String key, double def);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    float getFloatProperty(String key, float def);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    long getLongProperty(String key, long def);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    int getIntProperty(String key, int def);

    /**
     * @param def Defaut value
     * @see #getProperty(String)
     */
    Number getNumberProperty(String key, Number def);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    String getStringProperty(String key, String def);

    /**
     * @param def Default value
     * @see #getProperty(String)
     */
    boolean getBoolProperty(String key, boolean def);

    /**
     * @see #getProperty(String)
     */
    JsonArray getArrayProperty(String key);

    /**
     * @see #getProperty(String)
     */
    JsonObject getObjectProperty(String key);

    /**
     * @param key Dot-notation path to the property
     * @return The value of the property, or null if it does not exist
     */
    JsonElement getProperty(String key);

    /**
     * @param key Dot-notation path to the property
     * @return Whether or not the property exists
     */
    boolean hasProperty(String key);
}
