package me.nullicorn.hypixel4j.util;

import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectIdUtilTest {

    /**
     * A sample BSON objectId
     */
    private static final String SAMPLE_OBJECTID = "5efae3db0cf2f97106ac7c97";

    private static final long SAMPLE_OBJECTID_TIMESTAMP = 1593500635000L;

    @Test
    void test_dateFromObjectId() {
        Date actual = ObjectIdUtil.getTimestamp(SAMPLE_OBJECTID);
        Assertions.assertEquals(SAMPLE_OBJECTID_TIMESTAMP, actual.getTime());

        actual = ObjectIdUtil.getTimestamp("Not an objectId");
        Assertions.assertEquals(0, actual.getTime());
    }
}