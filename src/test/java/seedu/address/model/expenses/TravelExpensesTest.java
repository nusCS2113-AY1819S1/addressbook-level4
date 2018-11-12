package seedu.address.model.expenses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TravelExpensesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TravelExpenses(null));
    }

    @Test
    public void constructor_invalidTravelExpenses_throwsIllegalArgumentException() {
        String invalidTravelExpenses = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TravelExpenses(invalidTravelExpenses));
    }

    @Test
    public void isValidTravelExpenses() {
        // null travel expnses
        Assert.assertThrows(NullPointerException.class, () -> TravelExpenses.isValidTravelExpenses(null));

        // invalid travel expnses
        assertFalse(TravelExpenses.isValidTravelExpenses("")); // empty string
        assertFalse(TravelExpenses.isValidTravelExpenses(" ")); // spaces only
        assertFalse(TravelExpenses.isValidTravelExpenses("salary")); // non-numeric
        assertFalse(TravelExpenses.isValidTravelExpenses("9011p041")); // alphabets within digits
        assertFalse(TravelExpenses.isValidTravelExpenses("9312 1534")); // spaces within digits
        assertFalse(TravelExpenses.isValidTravelExpenses("9312.1231")); // spaces within digits

        // valid travel expnses
        assertTrue(TravelExpenses.isValidTravelExpenses("10.33")); // numbers and a dot
        assertTrue(TravelExpenses.isValidTravelExpenses("9312")); // numbers and a dot
    }
}
