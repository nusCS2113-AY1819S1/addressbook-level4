package seedu.address.model.expenses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MiscellaneousExpensesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MiscellaneousExpenses(null));
    }

    @Test
    public void constructor_invalidMiscellaneousExpenses_throwsIllegalArgumentException() {
        String invalidMiscellaneousExpenses = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MiscellaneousExpenses(
                invalidMiscellaneousExpenses));
    }

    @Test
    public void isValidMiscellaneousExpenses() {
        // null salary
        Assert.assertThrows(NullPointerException.class, () -> MiscellaneousExpenses.isValidMiscellaneousExpenses(null));

        // invalid salaries
        assertFalse(MiscellaneousExpenses.isValidMiscellaneousExpenses("")); // empty string
        assertFalse(MiscellaneousExpenses.isValidMiscellaneousExpenses(" ")); // spaces only
        assertFalse(MiscellaneousExpenses.isValidMiscellaneousExpenses("salary")); // non-numeric
        assertFalse(MiscellaneousExpenses.isValidMiscellaneousExpenses("9011p041")); // alphabets within digits
        assertFalse(MiscellaneousExpenses.isValidMiscellaneousExpenses("9312 1534")); // spaces within digits
        assertFalse(MiscellaneousExpenses.isValidMiscellaneousExpenses("9312.1231")); // spaces within digits

        // valid salaries
        assertTrue(MiscellaneousExpenses.isValidMiscellaneousExpenses("10.33")); // numbers and a dot
        assertTrue(MiscellaneousExpenses.isValidMiscellaneousExpenses("9312")); // numbers and a dot
    }
}
