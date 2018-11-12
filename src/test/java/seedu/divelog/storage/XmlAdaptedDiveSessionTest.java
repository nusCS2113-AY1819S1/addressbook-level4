package seedu.divelog.storage;

import static org.junit.Assert.assertEquals;
import static seedu.divelog.testutil.TypicalDiveSessions.DIVE_AT_BALI;

import org.junit.Test;


public class XmlAdaptedDiveSessionTest {

    private static final String VALID_START = "0700";
    private static final String VALID_END = "1000";
    private static final String VALID_SAFETY_STOP = "0945";
    private static final String VALID_PRESSURE_GROUP_START = "A";
    private static final String VALID_PRESSURE_GROUP_END = "R";
    private static final String VALID_LOCATION = "Bali";
    @Test
    public void toModelType_validDiveSessionDetails_returnsDiveSession() throws Exception {
        XmlAdaptedDiveSession dive = new XmlAdaptedDiveSession(DIVE_AT_BALI);
        assertEquals(DIVE_AT_BALI, dive.toModelType());
    }
}
