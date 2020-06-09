package me.nullicorn.hypixel4j.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Ben on 6/8/20 @ 7:26 PM
 */
public class GsonUtil {

    private GsonUtil() {
    }

    // ========== Boolean ==========

    public static boolean getBool(String path, JsonObject obj) {
        return getBool(path, false, obj);
    }

    public static boolean getBool(String path, boolean def, JsonObject obj) {
        JsonElement result = get(obj, path);
        if (result == null || !result.isJsonPrimitive() || !result.getAsJsonPrimitive().isBoolean()) {
            return def;

        } else {
            return result.getAsBoolean();
        }
    }

    // ========== Floating-Point Number ==========

    public static double getFloat(String path, JsonObject obj) {
        return getFloat(path, 0, obj);
    }

    public static double getFloat(String path, double def, JsonObject obj) {
        return getNumber(path, def, obj).doubleValue();
    }

    // ========== Long Integer ==========

    public static long getLong(String path, JsonObject obj) {
        return getLong(path, 0, obj);
    }

    public static long getLong(String path, long def, JsonObject obj) {
        return getNumber(path, def, obj).longValue();
    }

    // ========== Integer ==========

    public static int getInt(String path, JsonObject obj) {
        return getInt(path, 0, obj);
    }

    public static int getInt(String path, int def, JsonObject obj) {
        return getNumber(path, def, obj).intValue();
    }

    // ========== Number ==========

    public static Number getNumber(String path, JsonObject obj) {
        return getNumber(path, 0, obj);
    }

    public static Number getNumber(String path, Number def, JsonObject obj) {
        JsonElement result = get(obj, path);
        if (result == null || !result.isJsonPrimitive() || !result.getAsJsonPrimitive().isNumber()) {
            return def;

        } else {
            return result.getAsNumber();
        }
    }

    // ========== String ==========

    public static String getString(String path, JsonObject obj) {
        return getString(path, "", obj);
    }

    public static String getString(String path, String def, JsonObject obj) {
        JsonElement result = get(obj, path);
        if (result == null || !result.isJsonPrimitive() || !result.getAsJsonPrimitive().isString()) {
            return def;

        } else {
            return result.getAsString();
        }
    }

    // ========== JsonElement ==========

    public static JsonElement get(JsonObject obj, String path) {
        return get(obj, path, "\\.");
    }

    // TODO: 6/8/20 Finish this method
    public static JsonElement get(JsonObject obj, String path, String separator) {
        if (path.trim().isEmpty()) {
            return obj;
        }

        String[] pathParts = path.split(separator);

        JsonObject currentObj = obj;
        for (int i = 0; i < pathParts.length; i++) {

            JsonElement value = currentObj.get(pathParts[i]);
            if (value != null) {

                if (i < pathParts.length - 1 && value.isJsonObject()) {
                    // The child was a json object & there's more to the path
                    currentObj = value.getAsJsonObject();

                } else if (i < pathParts.length - 1) {
                    // We reached a value before the end of the path
                    return null;

                } else {
                    // We reached the end of the path, return the value
                    return value;
                }

            } else {
                return null;
            }
        }

        return null;
    }
}
