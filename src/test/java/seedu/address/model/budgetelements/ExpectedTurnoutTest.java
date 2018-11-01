package seedu.address.model.budgetelements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpectedTurnoutTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpectedTurnout(null));
    }

    @Test
    public void constructor_invalidExpectedTurnout_throwsIllegalArgumentException() {
        String invalidExpectedTurnout = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExpectedTurnout (invalidExpectedTurnout));
    }

    @Test
    public void isValidExpectedTurnout () {
        // null expected turnout
        Assert.assertThrows(NullPointerException.class, () -> ExpectedTurnout.isValidExpectedTurnout(null));

        // invalid expected turnout
        assertFalse(ExpectedTurnout.isValidExpectedTurnout ("")); // empty string
        assertFalse(ExpectedTurnout.isValidExpectedTurnout (" ")); // spaces only
        assertFalse(ExpectedTurnout.isValidExpectedTurnout ("expectedTurnout")); // non-numeric
        assertFalse(ExpectedTurnout.isValidExpectedTurnout ("90p0")); // alphabets within digits
        assertFalse(ExpectedTurnout.isValidExpectedTurnout ("93 15")); // spaces within digits

        // valid expected turnout
        assertTrue(ExpectedTurnout.isValidExpectedTurnout ("90"));
        assertTrue(ExpectedTurnout.isValidExpectedTurnout ("93121534"));
        assertTrue(ExpectedTurnout.isValidExpectedTurnout ("124293842033123")); // long expected turnout
    }
}
