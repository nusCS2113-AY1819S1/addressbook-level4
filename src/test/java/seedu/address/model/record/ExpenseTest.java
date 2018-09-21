package seedu.address.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpenseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Expense(null));
    }

    @Test
    public void constructor_invalidExpenseArgumentException() {
        String invalidExpense = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Expense(invalidExpense));
    }

    @Test
    public void isValidExpense() {
        // null expense
        Assert.assertThrows(NullPointerException.class, () -> Expense.isValidExpense(null));

        // invalid expense
        assertFalse(Expense.isValidExpense("")); // empty string
        assertFalse(Expense.isValidExpense(" ")); // spaces only
        assertFalse(Expense.isValidExpense("Blk 456, Den Road, #01-355")); // letters
        assertFalse(Expense.isValidExpense("-")); // symbols which is not '.'
        assertFalse(Expense.isValidExpense("123.442.456")); // multiple decimal points
        assertFalse(Expense.isValidExpense("01.23")); // whole number can only start with 0 if it is 1 digit
        assertFalse(Expense.isValidExpense("1.")); // there must be at least 1 digit after the decimal point
        assertFalse(Expense.isValidExpense(".1")); // there must be at least 1 digit before decimal point

        // valid expense
        assertTrue(Expense.isValidExpense("0")); // 0 is valid
        assertTrue(Expense.isValidExpense("123456789.00")); // long expense
        assertTrue(Expense.isValidExpense("12.30")); // standard expense
        assertTrue(Expense.isValidExpense("12")); // no need for the decimal point
        assertTrue(Expense.isValidExpense("123.0011")); // expense with decimal places greater than 2
    }
}
