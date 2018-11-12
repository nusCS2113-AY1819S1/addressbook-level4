package seedu.address.model.expenses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MedicalExpensesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicalExpenses(null));
    }

    @Test
    public void constructor_invalidMedicalExpenses_throwsIllegalArgumentException() {
        String invalidMedicalExpenses = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicalExpenses(invalidMedicalExpenses));
    }

    @Test
    public void isValidMedicalExpenses() {
        // null MedicalExpenses
        Assert.assertThrows(NullPointerException.class, () -> MedicalExpenses.isValidMedicalExpenses(null));

        // invalid salaries
        assertFalse(MedicalExpenses.isValidMedicalExpenses("")); // empty string
        assertFalse(MedicalExpenses.isValidMedicalExpenses(" ")); // spaces only
        assertFalse(MedicalExpenses.isValidMedicalExpenses("salary")); // non-numeric
        assertFalse(MedicalExpenses.isValidMedicalExpenses("9011p041")); // alphabets within digits
        assertFalse(MedicalExpenses.isValidMedicalExpenses("9312 1534")); // spaces within digits
        assertFalse(MedicalExpenses.isValidMedicalExpenses("9312.1231")); // spaces within digits

        // valid salaries
        assertTrue(MedicalExpenses.isValidMedicalExpenses("10.33")); // numbers and a dot
        assertTrue(MedicalExpenses.isValidMedicalExpenses("9312")); // numbers and a dot
    }
}
