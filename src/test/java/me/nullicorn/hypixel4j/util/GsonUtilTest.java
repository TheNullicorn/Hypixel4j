package me.nullicorn.hypixel4j.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by Ben on 6/8/20 @ 7:56 PM
 */
class GsonUtilTest {

    @Test
    void testGetGsonPath() {
        JsonObject data = JsonParser.parseString(
            "{"
                + "    \"str\": \"Hello world!\","
                + "    \"nested_1\": {"
                + "        \"nested_2\": {"
                + "            \"int\": 1234,"
                + "            \"bool\": true"
                + "        },"
                + "        \"float\": 3.141592"
                + "    }"
                + "}"
        ).getAsJsonObject();

        Assertions.assertEquals("Hello world!", GsonUtil.getString("str", data));
        Assertions.assertEquals(3.141592, GsonUtil.getFloat("nested_1.float", data));
        Assertions.assertEquals(1234, GsonUtil.getInt("nested_1.nested_2.int", data));
        Assertions.assertTrue(GsonUtil.getBool("nested_1.nested_2.bool", data));
        Assertions.assertEquals(data, GsonUtil.get(data, "").getAsJsonObject());
    }
}