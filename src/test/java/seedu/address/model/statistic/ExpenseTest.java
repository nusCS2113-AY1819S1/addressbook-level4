package seedu.address.model.statistic;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;



public class ExpenseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Expense(null));
    }

    @Test
    public void constructor_invalidExpense_throwsIllegalArgumentException() {
        String invalidExpense = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Expense(invalidExpense));
    }

    @Test
    public void isValidExpense() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Expense.isValidExpense(null));

        // blank price
        assertFalse(Expense.isValidExpense("")); // empty string
        assertFalse(Expense.isValidExpense(" ")); // spaces only


        // invalid parts
        assertFalse(Expense.isValidExpense("6.012")); // too many decimals
        assertFalse(Expense.isValidExpense("-3")); // no negative integers
        assertFalse(Expense.isValidExpense("a1.2")); // alphabet in string

        // valid revenue
        assertTrue(Expense.isValidExpense("5")); // No decimals
        assertTrue(Expense.isValidExpense("0.12")); //2 decimal place
        assertTrue(Expense.isValidExpense("1123.10")); // Only decimals
    }
}
