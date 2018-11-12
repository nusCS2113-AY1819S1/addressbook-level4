package seedu.address.model.expenses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpensesAmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpensesAmount(null));
    }

    @Test
    public void constructor_invalidExpensesAmount_throwsIllegalArgumentException() {
        String invalidExpensesAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExpensesAmount(invalidExpensesAmount));
    }

    @Test
    public void isValidExpensesAmount() {
        // null salary
        Assert.assertThrows(NullPointerException.class, () -> ExpensesAmount.isValidExpensesAmount(null));

        // invalid salaries
        assertFalse(ExpensesAmount.isValidExpensesAmount("")); // empty string
        assertFalse(ExpensesAmount.isValidExpensesAmount(" ")); // spaces only
        assertFalse(ExpensesAmount.isValidExpensesAmount("salary")); // non-numeric
        assertFalse(ExpensesAmount.isValidExpensesAmount("9011p041")); // alphabets within digits
        assertFalse(ExpensesAmount.isValidExpensesAmount("9312 1534")); // spaces within digits
        assertFalse(ExpensesAmount.isValidExpensesAmount("9312.1231")); // spaces within digits

        // valid salaries
        assertTrue(ExpensesAmount.isValidExpensesAmount("10.33")); // numbers and a dot
        assertTrue(ExpensesAmount.isValidExpensesAmount("9312")); // numbers and a dot
    }
}
