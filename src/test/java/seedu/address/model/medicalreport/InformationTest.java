package seedu.address.model.medicalreport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author chewkahmeng
public class InformationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Information(null));
    }

    @Test
    public void constructor_invalidInformation_throwsIllegalArgumentException() {
        String invalidInformation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Information(invalidInformation));
    }

    @Test
    public void isValidInformation() {
        // null information
        Assert.assertThrows(NullPointerException.class, () -> Information.isValidInformation(null));

        // invalid information
        assertFalse(Information.isValidInformation("")); // empty string
        assertFalse(Information.isValidInformation(" ")); // spaces only

        // valid information
        assertTrue(Information.isValidInformation("Prescribed XXX medicine, next appointment on 02-02-2018."));
    }
}
