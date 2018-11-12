package seedu.divelog.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class UnitsUtilTest {
    @Test
    public void testFeetToMeters() {
        assertEquals(UnitsUtil.feetToMeters(1), 0.3048f);
    }
    @Test
    public void testMetersToFeet() {
        assertEquals(UnitsUtil.metersToFeet(1), 1 / 0.3048f);
    }
}
