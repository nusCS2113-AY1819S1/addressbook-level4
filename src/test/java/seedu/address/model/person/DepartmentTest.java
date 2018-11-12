package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Woonhian
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
        // null department name
        Assert.assertThrows(NullPointerException.class, () -> Department.isValidDepartment(null));

        // invalid department name
        assertFalse(Department.isValidDepartment("")); // empty string
        assertFalse(Department.isValidDepartment(" ")); // spaces only
        assertFalse(Department.isValidDepartment("^")); // only non-alphanumeric characters
        assertFalse(Department.isValidDepartment("junior*")); // contains non-alphanumeric characters
        assertFalse(Department.isValidDepartment("Junior Manage")); // does not end with Management
        assertFalse(Department.isValidDepartment("Jun1or Management")); // contains alphanumeric characters

        // valid department name
        assertTrue(Department.isValidDepartment("junior Management")); // alphabets only
        assertTrue(Department.isValidDepartment("Junior Management")); // with capital letters
        assertTrue(Department.isValidDepartment("First Junior Management")); // long names
    }

    @Test
    public void hashCode_sameObject_equals() {
        Department department = new Department("Junior Management");
        int expectedHash = department.hashCode();
        assertEquals(department.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentObject_equals() {
        Department department = new Department("Junior Management");
        int expectedHash = department.hashCode();
        Department sameDepartment = new Department("Junior Management");
        assertEquals(sameDepartment.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentValues_notEquals() {
        Department department = new Department("Junior Management");
        int expectedHash = department.hashCode();
        Department differentDepartment = new Department("Senior Management");
        assertNotEquals(differentDepartment.hashCode(), expectedHash);
    }
}
