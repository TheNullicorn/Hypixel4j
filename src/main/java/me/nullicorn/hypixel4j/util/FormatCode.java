package me.nullicorn.hypixel4j.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Ben on 6/8/20 @ 9:38 PM
 */
public enum FormatCode {

    // Colors
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),

    // Special
    MAGIC('k'),
    BOLD('l'),
    STRIKETHROUGH('m'),
    UNDERLINE('n'),
    ITALIC('o'),
    RESET('r');

    // Unmodifiable map of enum values (for reverse search w/ default value)
    private static final Map<String, FormatCode> ENUM_MAP = Collections.unmodifiableMap(
        Arrays.stream(FormatCode.values())
            .collect(Collectors.toMap(FormatCode::name, Function.identity()))
    );

    private final char symbol;

    FormatCode(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "§" + symbol;
    }

    public static FormatCode from(String key) {
        FormatCode value = ENUM_MAP.get(key.toUpperCase());
        return value == null ? RESET : value;
    }
}
