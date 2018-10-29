package seedu.address.model.expense;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpenseValueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpenseValue(null));
    }

    @Test
    public void constructor_invalidExpenseValue_throwsIllegalArgumentException() {
        String invalidExpenseValue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExpenseValue(invalidExpenseValue));
    }

    @Test
    public void isValidExpenseValue() {
        // null expense values
        Assert.assertThrows(NullPointerException.class, () -> ExpenseValue.isValidExpenseValue(null));

        // invalid expense values
        assertFalse(ExpenseValue.isValidExpenseValue("")); // empty string
        assertFalse(ExpenseValue.isValidExpenseValue(" ")); // spaces only
        assertFalse(ExpenseValue.isValidExpenseValue("91")); // not in 2 decimal places
        assertFalse(ExpenseValue.isValidExpenseValue("-91.00")); // contains non-numeric
        assertFalse(ExpenseValue.isValidExpenseValue("0.00")); // no expense should be 0.00
        assertFalse(ExpenseValue.isValidExpenseValue("expense value")); // non-numeric
        assertFalse(ExpenseValue.isValidExpenseValue("901p1.41")); // alphabets within digits
        assertFalse(ExpenseValue.isValidExpenseValue("931 2.34")); // spaces within digits

        // valid expense values
        assertTrue(ExpenseValue.isValidExpenseValue("0.01")); // small numbers
        assertTrue(ExpenseValue.isValidExpenseValue("1242938420331.23")); // long numbers
    }
}
