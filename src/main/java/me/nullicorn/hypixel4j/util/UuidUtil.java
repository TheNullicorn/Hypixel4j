package me.nullicorn.hypixel4j.util;

import java.util.UUID;

/**
 * Created by Ben on 6/9/20 @ 9:03 AM
 */
public class UuidUtil {

    private static final String UUID_REGEX             = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}";
    private static final String UNDASHED_UUID_REGEX    = "[a-f0-9]{12}4[a-f0-9]{3}[89aAbB][a-f0-9]{15}";
    private static final String ADD_UUID_HYPHENS_REGEX = "([a-f0-9]{8})([a-f0-9]{4})(4[a-f0-9]{3})([89aAbB][a-f0-9]{3})([a-f0-9]{12})";

    private UuidUtil() {
    }

    public static UUID fromUndashed(String input) {
        if (!isUuid(input)) {
            throw new IllegalArgumentException("Not a UUID");

        } else if (input.contains("-")) {
            // Already has hyphens
            return UUID.fromString(input);
        }

        return UUID.fromString(input.replaceAll(ADD_UUID_HYPHENS_REGEX, "$1-$2-$3-$4-$5"));
    }

    public static String undash(UUID input) {
        return undash(input.toString());
    }

    public static String undash(String input) {
        return input.replace("-", "");
    }

    public static boolean isUuid(String input) {
        return input.matches(UNDASHED_UUID_REGEX) || input.matches(UUID_REGEX);
    }
}
