package me.nullicorn.hypixel4j.util;

/**
 * Created by Ben on 6/9/20 @ 10:13 AM
 */
public class ColorUtil {

    private ColorUtil() {
    }

    /**
     * Convert a string of text (formatted using Minecraft's formatting codes) to a stylized HTML
     * span tag; '§' is used as the format indicator
     *
     * @see #asStyledHtml(String, char)
     * @see FormatCode
     */
    public static String asStyledHtml(String formattedStr) {
        return asStyledHtml(formattedStr, '§');
    }

    /**
     * Convert a string of text to a stylized HTML span
     *
     * @param formattedStr A string formatted using Minecraft's formatting codes
     * @param indicator    Character used to indicate a formatting code; in vanilla Minecraft, this
     *                     is '§'
     * @return An HTML span tag stylized using the input's formatting codes
     * @see FormatCode
     */
    public static String asStyledHtml(String formattedStr, char indicator) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<span>");

        FormatCode lastColor = null;
        boolean isFormatChar = false;
        for (int i = 0; i < formattedStr.length(); i++) {
            // Check if the formatting has changed since the last character
            FormatCode currentColor = getColorAtIndex(i, indicator, formattedStr);
            if (currentColor != lastColor) {
                htmlBuilder.append(i == 0 ? "" : "</span>");
                htmlBuilder.append("<span style=\"").append(currentColor.getStyle()).append("\">");
            }
            lastColor = currentColor;

            if (formattedStr.charAt(i) == indicator) {
                isFormatChar = true;

            } else if (isFormatChar) {
                isFormatChar = false;

            } else {
                htmlBuilder.append(formattedStr.charAt(i));
            }
        }
        // Close the last color tag & wrapper tag
        htmlBuilder.append("</span></span>");

        return htmlBuilder.toString()
            .replaceAll("<(\\w+)[^>]+><\\/\\1>", "") // Delete empty tags
            .replaceAll("\\n", "<br>"); // Fix line-breaks
    }

    /**
     * Get the active format code at the given index of the input string; '§' is used as the format
     * indicator
     *
     * @see #getColorAtIndex(int, char, String)
     * @see FormatCode
     */
    public static FormatCode getColorAtIndex(int index, String formattedStr) {
        return getColorAtIndex(index, '§', formattedStr);
    }

    /**
     * Get the active format code at the given index of the input string
     *
     * @param index        Index of formattedStr to check
     * @param indicator    Character used to indicate a formatting code; in vanilla Minecraft, this
     *                     is '§'
     * @param formattedStr String to check in
     * @return The active formatting code at the given index; multiple formatting codes are not
     * supported, so the most recently-set code will be used
     * @see FormatCode
     */
    public static FormatCode getColorAtIndex(int index, char indicator, String formattedStr) {
        FormatCode currentColor = FormatCode.BLACK;

        boolean isFormatChar = false; // Whether or not the current char is after an indicator (e.g. 'c' in "§c")
        for (int i = 0; i < index; i++) {

            if (formattedStr.charAt(i) == indicator) {
                // Next char should be a formatting code
                isFormatChar = true;

            } else if (isFormatChar) {
                // Current char is a formatting code
                FormatCode code = FormatCode.fromSymbol(formattedStr.charAt(i));
                if (code == FormatCode.RESET) {
                    currentColor = FormatCode.BLACK;
                } else {
                    currentColor = code;
                }
                isFormatChar = false;
            }
        }

        return currentColor;
    }
}
