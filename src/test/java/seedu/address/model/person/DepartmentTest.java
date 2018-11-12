package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DepartmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Department(null));
    }

    @Test
    public void constructor_invalidDepartment_throwsIllegalArgumentException() {
        String invalidDepartment = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Department(invalidDepartment));
    }

    @Test
    public void isValidDepartment() {
        // null department
        Assert.assertThrows(NullPointerException.class, () -> Department.isValidDepartment(null));

        // invalid departments
        assertFalse(Department.isValidDepartment("")); // empty string
        assertFalse(Department.isValidDepartment("a")); // less than 2 characters
        assertFalse(Department.isValidDepartment("91")); // only numeric characters
        assertFalse(Department.isValidDepartment("Finance123")); // contains numeric characters
        assertFalse(Department.isValidDepartment("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // exactly 31 characters

        // valid departments
        assertTrue(Department.isValidDepartment("aa")); // exactly 2 characters
        assertTrue(Department.isValidDepartment("Finance")); // alphabets only
        assertTrue(Department.isValidDepartment("Human Resource")); // alphabets and space
        assertTrue(Department.isValidDepartment("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // exactly 30 characters
    }

    @Test
    public void hashCodeSameObject_equals() {
        Department expectedHashCode = new Department("Finance");
        assertEquals("Finance".hashCode(), expectedHashCode.hashCode());
    }

    @Test
    public void hashCodeDifferentValue_notEquals() {
        Department expectedHashCode = new Department("Finance");
        assertNotEquals("HR".hashCode(), expectedHashCode.hashCode());
    }
}
