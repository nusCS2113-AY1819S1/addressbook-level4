package seedu.recruit.model.joboffer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recruit.testutil.Assert;



public class SalaryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Salary(null));
    }

    @Test
    public void constructor_invalidSalary_throwsIllegalArgumentException() {
        String invalidSalary = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Salary(invalidSalary));
    }

    @Test
    public void isValidSalary() {
        // null salary
        Assert.assertThrows(NullPointerException.class, () -> Salary.isValidSalary(null));

        // invalid salary
        assertFalse(Salary.isValidSalary("")); // empty string
        assertFalse(Salary.isValidSalary(" ")); // spaces only
        assertFalse(Salary.isValidSalary("^")); // only non-numeric characters
        assertFalse(Salary.isValidSalary("1000a")); // contains non-numeric characters

        // valid salary
        assertTrue(Salary.isValidSalary("1000"));
        assertTrue(Salary.isValidSalary("1234"));
        assertTrue(Salary.isValidSalary("9999"));
    }
}
