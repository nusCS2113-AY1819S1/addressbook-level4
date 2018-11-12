package seedu.address.model.medhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

class MedHistDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedHistDate(null));
    }

    @Test
    public void constructor_invalidMedHistDate_throwsIllegalArgumentException() {
        String invalidMedHistDate = "10/10/2010";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedHistDate(invalidMedHistDate));
    }

    @Test
    public void isValidMedHistDate() {
        // null medHistDate
        Assert.assertThrows(NullPointerException.class, () -> MedHistDate.isValidMedHistDate(null));

        // invalid medHistDate
        assertFalse(MedHistDate.isValidMedHistDate("^")); // only non-alphanumeric characters
        assertFalse(MedHistDate.isValidMedHistDate("peter*")); // contains non-alphanumeric characters
        assertFalse(MedHistDate.isValidMedHistDate("10/10/1910")); // contains wrong format

        // valid medHistDate
        assertTrue(MedHistDate.isValidMedHistDate("10-10-2010")); // correct format
    }
}
