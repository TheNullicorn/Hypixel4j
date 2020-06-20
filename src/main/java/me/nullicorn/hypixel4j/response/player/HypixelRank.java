package me.nullicorn.hypixel4j.response.player;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import me.nullicorn.hypixel4j.util.FormatCode;

/**
 * All Hypixel ranks that are attached to a permission level
 * <p>
 * This does not include prefixes, which are only cosmetic
 */
public enum HypixelRank {

    // Package ranks
    DEFAULT("§7"),
    VIP("§a[VIP]"),
    VIP_PLUS("§a[VIP§6+§a]"),
    MVP("§b[MVP]"),
    MVP_PLUS("§b[MVP%2$s+§b]"),
    SUPERSTAR("%1$s[MVP%2$s++%1$s]"),

    // Special ranks
    YOUTUBER("§c[§fYOUTUBER§c]"),
    HELPER("§9[HELPER]"),
    MODERATOR("§2[MOD]"),
    ADMIN("§c[ADMIN]");

    // Unmodifiable map of enum values (for reverse search w/ default value)
    private static final Map<String, HypixelRank> ENUM_MAP = Collections.unmodifiableMap(
        Arrays.stream(HypixelRank.values())
            .collect(Collectors.toMap(HypixelRank::name, Function.identity()))
    );

    private final String prefix;

    HypixelRank(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @param tagColor  The player's "monthlyRankColor" value
     * @param plusColor The player's "rankPlusColor" value
     * @return The player's rank, formatted using the provided tag and plus colors
     */
    public String getPrefix(FormatCode tagColor, FormatCode plusColor) {
        return String.format(prefix, tagColor, plusColor);
    }

    /**
     * @return This rank's raw prefix (may contain string formatting codes)
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Read a Hypixel rank from a string
     *
     * @param key Input string
     * @return The corresponding HypixelRank, or HypixelRank.DEFAULT if the input matched no known
     * rank
     */
    public static HypixelRank from(String key) {
        HypixelRank value = ENUM_MAP.get(key.toUpperCase());
        return value == null ? DEFAULT : value;
    }
}
