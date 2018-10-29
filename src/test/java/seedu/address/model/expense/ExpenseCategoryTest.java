package seedu.address.model.expense;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpenseCategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpenseCategory(null));
    }

    @Test
    public void constructor_invalidExpenseCategory_throwsIllegalArgumentException() {
        String invalidExpenseCategory = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExpenseCategory(invalidExpenseCategory));
    }

    @Test
    public void isValidExpenseCategory() {
        // null expense category
        Assert.assertThrows(NullPointerException.class, () -> ExpenseCategory.isValidExpenseCategory(null));

        // invalid expense category
        assertFalse(ExpenseCategory.isValidExpenseCategory("")); // empty string
        assertFalse(ExpenseCategory.isValidExpenseCategory(" ")); // spaces only
        assertFalse(ExpenseCategory.isValidExpenseCategory("^")); // only non-alphabetic characters
        assertFalse(ExpenseCategory.isValidExpenseCategory("peter*")); // contains non-alphabetic characters
        assertFalse(ExpenseCategory.isValidExpenseCategory("tao bao")); // contains space

        // valid expense category
        assertTrue(ExpenseCategory.isValidExpenseCategory("shopping")); // alphabets only
        assertTrue(ExpenseCategory.isValidExpenseCategory("Food")); // with capital letters
    }
}
