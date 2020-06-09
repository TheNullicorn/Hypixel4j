package me.nullicorn.hypixel4j.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by Ben on 6/9/20 @ 10:16 AM
 */
class ColorUtilTest {

    // Taken from https://minecraft.gamepedia.com/Formatting_codes#Sample_text
    static String sampleText = "§nMinecraft Formatting§r\n"
        + "\n"
        + "§00 §11 §22 §33\n"
        + "§44 §55 §66 §77\n"
        + "§88 §99 §aa §bb\n"
        + "§cc §dd §ee §ff\n"
        + "\n"
        + "§rk §kMinecraft\n"
        + "§rl §lMinecraft\n"
        + "§rm §mMinecraft\n"
        + "§rn §nMinecraft\n"
        + "§ro §oMinecraft\n"
        + "§rr §rMinecraft";

    static String sampleTextExpected = "<span>"
        + "<span style=\"text-decoration:underline;\">Minecraft Formatting</span>"
        + "<span style=\"color:#000000;\"><br><br>0 </span>"
        + "<span style=\"color:#0000AA;\">1 </span>"
        + "<span style=\"color:#00AA00;\">2 </span>"
        + "<span style=\"color:#00AAAA;\">3<br></span>"
        + "<span style=\"color:#AA0000;\">4 </span>"
        + "<span style=\"color:#AA00AA;\">5 </span>"
        + "<span style=\"color:#FFAA00;\">6 </span>"
        + "<span style=\"color:#AAAAAA;\">7<br></span>"
        + "<span style=\"color:#555555;\">8 </span>"
        + "<span style=\"color:#5555FF;\">9 </span>"
        + "<span style=\"color:#55FF55;\">a </span>"
        + "<span style=\"color:#55FFFF;\">b<br></span>"
        + "<span style=\"color:#FF5555;\">c </span>"
        + "<span style=\"color:#FF55FF;\">d </span>"
        + "<span style=\"color:#FFFF55;\">e </span>"
        + "<span style=\"color:#FFFFFF;\">f<br><br></span>"
        + "<span style=\"color:#000000;\">k </span>"
        + "<span style=\"\">Minecraft<br></span>"
        + "<span style=\"color:#000000;\">l </span>"
        + "<span style=\"font-weight:bold;\">Minecraft<br></span>"
        + "<span style=\"color:#000000;\">m </span>"
        + "<span style=\"text-decoration: line-through;\">Minecraft<br></span>"
        + "<span style=\"color:#000000;\">n </span>"
        + "<span style=\"text-decoration:underline;\">Minecraft<br></span>"
        + "<span style=\"color:#000000;\">o </span>"
        + "<span style=\"font-style:italic;\">Minecraft<br></span>"
        + "<span style=\"color:#000000;\">r Minecraft</span>"
        + "</span>";

    static String sampleUsername = "§b[MVP§c+§b] Nullicorn";

    static String sampleUsernameExpected = "<span>"
        + "<span style=\"color:#55FFFF;\">[MVP</span>"
        + "<span style=\"color:#FF5555;\">+</span>"
        + "<span style=\"color:#55FFFF;\">] Nullicorn</span>"
        + "</span>";

    @Test
    void test_formattedStringToHtml() {
        // Username
        Assertions.assertEquals(sampleUsernameExpected, ColorUtil.asStyledHtml(sampleUsername));

        // Paragraph of text
        Assertions.assertEquals(sampleTextExpected, ColorUtil.asStyledHtml(sampleText));
    }

    @Test
    void test_getColorAtIndex() {
        Assertions.assertEquals(
            FormatCode.RED,
            ColorUtil.getColorAtIndex(8, '§', sampleUsername)
        );
    }
}