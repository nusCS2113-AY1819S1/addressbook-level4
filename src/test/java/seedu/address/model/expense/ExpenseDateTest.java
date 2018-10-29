package seedu.address.model.expense;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpenseDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpenseDate(null));
    }

    @Test
    public void constructor_invalidExpenseDate_throwsIllegalArgumentException() {
        String invalidExpenseDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExpenseDate(invalidExpenseDate));
    }

    @Test
    public void isValidDate() {
        // null expense date
        Assert.assertThrows(NullPointerException.class, () -> ExpenseDate.isValidDate(null));

        // invalid expense dates
        assertFalse(ExpenseDate.isValidDate("")); // empty string
        assertFalse(ExpenseDate.isValidDate(" ")); // spaces only
        assertFalse(ExpenseDate.isValidDate("4/12/2018")); // not in DD/MM/YYYY format
        assertFalse(ExpenseDate.isValidDate("04-12-2018")); // not in DD/MM/YYYY format
        assertFalse(ExpenseDate.isValidDate("30/02/2018")); // not a valid date

        // valid expense dates
        assertTrue(ExpenseDate.isValidDate("01/01/0001")); // valid date in the past
        assertTrue(ExpenseDate.isValidDate("11/11/2018")); // valid date
        assertTrue(ExpenseDate.isValidDate("31/12/3000")); // valid date in the future
    }
}
