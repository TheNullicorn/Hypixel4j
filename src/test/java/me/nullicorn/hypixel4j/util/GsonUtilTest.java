package me.nullicorn.hypixel4j.util;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Created by Ben on 6/8/20 @ 7:56 PM
 */
class GsonUtilTest {

    /**
     * A sample JsonObject that looks like the following:
     * <pre>
     * {
     *     "str": "Hello world!",
     *     "nested_1": {
     *         "nested_2": {
     *             "int": 1234,
     *             "bool": true
     *         },
     *         "float": 3.141592"
     *     }
     * }
     * </pre>
     */
    private static JsonObject sampleJson;

    // Values within sampleJson
    private static final String  STR_VALUE   = "Hello world!";
    private static final int     INT_VALUE   = 1234;
    private static final boolean BOOL_VALUE  = true;
    private static final float   FLOAT_VALUE = 3.141592F;

    @BeforeAll
    static void beforeAll() {
        sampleJson = new JsonObject();

        JsonObject nested1 = new JsonObject();
        JsonObject nested2 = new JsonObject();

        nested2.addProperty("int", INT_VALUE);
        nested2.addProperty("bool", BOOL_VALUE);

        nested1.add("nested_2", nested2);
        nested1.addProperty("float", FLOAT_VALUE);

        sampleJson.addProperty("str", STR_VALUE);
        sampleJson.add("nested_1", nested1);
    }

    @Test
    void test_getPropertiesFromPath() {
        Assertions.assertEquals(STR_VALUE, GsonUtil.getString("str", sampleJson));
        Assertions.assertEquals(FLOAT_VALUE, GsonUtil.getFloat("nested_1.float", sampleJson));
        Assertions.assertEquals(INT_VALUE, GsonUtil.getInt("nested_1.nested_2.int", sampleJson));
        Assertions.assertEquals(BOOL_VALUE, GsonUtil.getBool("nested_1.nested_2.bool", sampleJson));
        Assertions.assertEquals(sampleJson, GsonUtil.get(sampleJson, "").getAsJsonObject());
    }

    @Test
    void test_propertyFromNullObjectIsNull() {
        Assertions.assertNull(GsonUtil.get(null, "this.doesnt.exist"));
    }
}