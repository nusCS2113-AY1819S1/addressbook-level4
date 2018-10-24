package seedu.address.model.classroom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassModuleTest {
    @Test
    public void equals() {
        ClassModule moduleCode = new ClassModule("CG1111");
        // same object -> returns true
        assertTrue(moduleCode.equals(moduleCode));
        // same values -> returns true
        ClassModule moduleCodeCopy = new ClassModule(moduleCode.getValue());
        assertTrue(moduleCode.equals(moduleCodeCopy));
        // different types -> returns false
        assertFalse(moduleCode.equals(1));
        // null -> returns false
        assertFalse(moduleCode.equals(null));
        // different moduleCode -> returns false
        ClassModule differentmoduleCode = new ClassModule("CG1112");
        assertFalse(moduleCode.equals(differentmoduleCode));
    }
}
