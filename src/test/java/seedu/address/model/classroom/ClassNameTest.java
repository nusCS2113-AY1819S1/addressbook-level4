package seedu.address.model.classroom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassNameTest {
    @Test
    public void equals() {
        ClassName className = new ClassName("T16");
        // same object -> returns true
        assertTrue(className.equals(className));
        // same values -> returns true
        ClassName classNameCopy = new ClassName(className.getValue());
        assertTrue(className.equals(classNameCopy));
        // different types -> returns false
        assertFalse(className.equals(1));
        // null -> returns false
        assertFalse(className.equals(null));
        // different classname -> returns false
        ClassName differentClassName = new ClassName("wrongclass");
        assertFalse(className.equals(differentClassName));
    }
}
