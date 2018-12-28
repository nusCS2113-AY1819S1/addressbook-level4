package seedu.address.model.person.addinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WeightTest {
    @Test
    public void isValidWeight() {
        assertTrue(Weight.isValidWeight("80"));
        assertTrue(Weight.isValidWeight("56.30"));

        assertFalse(Weight.isValidWeight("A"));
        assertFalse(Weight.isValidWeight("56.3.0"));
    }

    @Test
    public void weightToString() {
        Weight weight = new Weight("80.5");
        assertEquals(weight.toString(), "80.5");
    }

}
