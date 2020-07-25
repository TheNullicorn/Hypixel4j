package me.nullicorn.hypixel4j.util;

import java.util.Date;

/**
 * Utility class for methods relating to BSON ObjectIds
 */
public final class ObjectIdUtil {

    /**
     * Read the timestamp from the BSON ObjectId
     *
     * @param objectId ObjectId as a string
     * @return The ObjectId's timestamp as a Date object
     */
    public static Date getTimestamp(String objectId) {
        if (objectId.matches("[A-Fa-f0-9]{24}")) {
            return new Date(1000L * Integer.parseInt(objectId.substring(0, 8), 16));
        }

        System.err.println("Provided string was not a valid ObjectId: \"" + objectId + "\"");
        return new Date(0);
    }

    private ObjectIdUtil() {
    }
}
