package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

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

        // invalid salaries
        assertFalse(Salary.isValidSalary("")); // empty string
        assertFalse(Salary.isValidSalary(" ")); // spaces only
        assertFalse(Salary.isValidSalary("salary")); // non-numeric
        assertFalse(Salary.isValidSalary("9011p041")); // alphabets within digits
        assertFalse(Salary.isValidSalary("9312 1534")); // spaces within digits
        assertFalse(Salary.isValidSalary("9312.1231")); // spaces within digits

        // valid salaries
        assertTrue(Salary.isValidSalary("10.33")); // numbers and a dot
        assertTrue(Salary.isValidSalary("9312")); // numbers and a dot
    }

    @Test
    public void hashCodeSameObject_equals() {
        Salary expectedHashCode = new Salary("1234");
        assertEquals("1234".hashCode(), expectedHashCode.hashCode());
    }

    @Test
    public void hashCodeDifferentValue_notEquals() {
        Salary expectedHashCode = new Salary("9999");
        assertNotEquals("8888".hashCode(), expectedHashCode.hashCode());
    }
}
