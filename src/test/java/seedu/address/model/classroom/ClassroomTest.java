package seedu.address.model.classroom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassroomTest {
    @Test
    public void equals() {
        Classroom className = new Classroom("T16");
        // same object -> returns true
        assertTrue(className.equals(className));
        // same values -> returns true
        Classroom classNameCopy = new Classroom(className.value);
        assertTrue(className.equals(classNameCopy));
        // different types -> returns false
        assertFalse(className.equals(1));
        // null -> returns false
        assertFalse(className.equals(null));
        // different classname -> returns false
        Classroom differentClassName = new Classroom("wrongclass");
        assertFalse(className.equals(differentClassName));
    }
}
