package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PositionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    public void constructor_invalidPosition_throwsIllegalArgumentException() {
        String invalidPosition = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Position(invalidPosition));
    }

    @Test
    public void isValidPosition() {
        // null position
        Assert.assertThrows(NullPointerException.class, () -> Position.isValidPosition(null));

        // invalid positions
        assertFalse(Position.isValidPosition("")); // empty string
        assertFalse(Position.isValidPosition("a")); // // less than 2 characters
        assertFalse(Position.isValidPosition("91")); // only numeric characters
        assertFalse(Position.isValidPosition("Finance123")); // contains numeric characters
        assertFalse(Department.isValidDepartment("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // exactly 31 characters

        // valid positions
        assertTrue(Department.isValidDepartment("aa")); // exactly 2 characters
        assertTrue(Position.isValidPosition("Manager")); // alphabets only
        assertTrue(Position.isValidPosition("Part time Admin")); // alphabets and space
        assertTrue(Department.isValidDepartment("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // exactly 30 characters
    }

    @Test
    public void hashCodeSameObject_equals() {
        Position expectedHashCode = new Position("Admin");
        assertEquals("Admin".hashCode(), expectedHashCode.hashCode());
    }

    @Test
    public void hashCodeDifferentValue_notEquals() {
        Position expectedHashCode = new Position("Admin");
        assertNotEquals("Manager".hashCode(), expectedHashCode.hashCode());
    }
}
