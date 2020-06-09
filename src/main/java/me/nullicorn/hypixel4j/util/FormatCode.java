package me.nullicorn.hypixel4j.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Created by Ben on 6/8/20 @ 9:38 PM
 */
@Getter
public enum FormatCode {

    // Colors
    BLACK('0', "color:#000000;"),
    DARK_BLUE('1', "color:#0000AA;"),
    DARK_GREEN('2', "color:#00AA00;"),
    DARK_AQUA('3', "color:#00AAAA;"),
    DARK_RED('4', "color:#AA0000;"),
    DARK_PURPLE('5', "color:#AA00AA;"),
    GOLD('6', "color:#FFAA00;"),
    GRAY('7', "color:#AAAAAA;"),
    DARK_GRAY('8', "color:#555555;"),
    BLUE('9', "color:#5555FF;"),
    GREEN('a', "color:#55FF55;"),
    AQUA('b', "color:#55FFFF;"),
    RED('c', "color:#FF5555;"),
    LIGHT_PURPLE('d', "color:#FF55FF;"),
    YELLOW('e', "color:#FFFF55;"),
    WHITE('f', "color:#FFFFFF;"),

    // Formatting
    BOLD('l', "font-weight:bold;", false),
    STRIKETHROUGH('m', "text-decoration: line-through;", false),
    UNDERLINE('n', "text-decoration:underline;", false),
    ITALIC('o', "font-style:italic;", false),

    // Special
    MAGIC('k', false),
    RESET('r', false);

    // Unmodifiable maps of enum values (for reverse search w/ default value)
    private static final Map<String, FormatCode>    ENUM_NAME_MAP   = Collections.unmodifiableMap(
        Arrays.stream(FormatCode.values())
            .collect(Collectors.toMap(FormatCode::name, Function.identity()))
    );
    private static final Map<Character, FormatCode> ENUM_SYMBOL_MAP = Collections.unmodifiableMap(
        Arrays.stream(FormatCode.values())
            .collect(Collectors.toMap(FormatCode::getSymbol, Function.identity()))
    );

    private final char    symbol;
    private final String  style;
    private final boolean isColor;

    FormatCode(char symbol, boolean isColor) {
        this(symbol, "", isColor);
    }

    FormatCode(char symbol, String cssStyle) {
        this(symbol, cssStyle, true);
    }

    FormatCode(char symbol, String cssStyle, boolean isColor) {
        this.symbol = symbol;
        this.style = cssStyle;
        this.isColor = isColor;
    }

    @Override
    public String toString() {
        return toString('§');
    }

    public String toString(char indicator) {
        return "" + indicator + symbol;
    }

    public static FormatCode fromName(String key) {
        FormatCode value = ENUM_NAME_MAP.get(key.toUpperCase());
        return value == null ? RESET : value;
    }

    public static FormatCode fromSymbol(char symbol) {
        FormatCode value = ENUM_SYMBOL_MAP.get(symbol);
        return value == null ? RESET : value;
    }
}
