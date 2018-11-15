package seedu.address.model.clubbudget;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TotalBudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TotalBudget(null));
    }

    @Test
    public void constructor_invalidTotalBudget_throwsIllegalArgumentException() {
        String invalidTotalBudget = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TotalBudget(invalidTotalBudget));
    }

    @Test
    public void isValidTotalBudget() {
        // null total budget
        Assert.assertThrows(NullPointerException.class, () -> TotalBudget.isValidTotalBudget(null));

        // invalid total budget
        assertFalse(TotalBudget.isValidTotalBudget("")); // empty string
        assertFalse(TotalBudget.isValidTotalBudget(" ")); // spaces only
        assertFalse(TotalBudget.isValidTotalBudget("phone")); // non-numeric
        assertFalse(TotalBudget.isValidTotalBudget("9011p041")); // alphabets within digits
        assertFalse(TotalBudget.isValidTotalBudget("9312 1534")); // spaces within digits

        // valid total budget
        assertTrue(TotalBudget.isValidTotalBudget("0")); // zero
        assertTrue(TotalBudget.isValidTotalBudget("911")); // exactly 3 numbers
        assertTrue(TotalBudget.isValidTotalBudget("93121534"));
        assertTrue(TotalBudget.isValidTotalBudget("124293842033123")); // long phone numbers
    }
}
