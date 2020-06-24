package me.nullicorn.hypixel4j.util;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UuidUtilTest {

    /**
     * A sample UUID object
     */
    private static final UUID SAMPLE_UUID = UUID
        .fromString("8614fb2d-e71d-4675-95dc-d7da3d977eae");

    /**
     * Stringified version of {@link #SAMPLE_UUID} without hyphens (32 chars)
     */
    private static final String TRIMMED_UUID = "8614fb2de71d467595dcd7da3d977eae";

    @Test
    void test_uuidFromTrimmed() {
        UUID actual = UuidUtil.fromUndashed(TRIMMED_UUID);
        Assertions.assertEquals(SAMPLE_UUID, actual);
    }

    @Test
    void test_trimUuid() {
        String actual = UuidUtil.undash(SAMPLE_UUID);
        Assertions.assertEquals(TRIMMED_UUID, actual);
    }

    @Test
    void test_checkIfStringIsUuid() {
        Assertions.assertTrue(UuidUtil.isUuid(SAMPLE_UUID.toString()));
        Assertions.assertTrue(UuidUtil.isUuid(TRIMMED_UUID));
    }
}