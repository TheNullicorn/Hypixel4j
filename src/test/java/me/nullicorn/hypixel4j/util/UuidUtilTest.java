package me.nullicorn.hypixel4j.util;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by Ben on 6/9/20 @ 9:16 AM
 */
class UuidUtilTest {

    @Test
    void test_UuidFromUndashedUuidStr() {
        UUID actual = UuidUtil.fromUndashed("8614fb2de71d467595dcd7da3d977eae");
        UUID expected = UUID.fromString("8614fb2d-e71d-4675-95dc-d7da3d977eae");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_undashUuid() {
        String actual = UuidUtil.undash(UUID.fromString("8614fb2d-e71d-4675-95dc-d7da3d977eae"));
        String expected = "8614fb2de71d467595dcd7da3d977eae";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_checkIfUuid() {
        Assertions.assertTrue(UuidUtil.isUuid("8614fb2d-e71d-4675-95dc-d7da3d977eae"));
        Assertions.assertTrue(UuidUtil.isUuid("8614fb2de71d467595dcd7da3d977eae"));
    }
}