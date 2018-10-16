package seedu.address.model.classroom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassroomTest {
    @Test
    public void equals() {

        Classroom classRoom = new Classroom(new ClassName("T16"),
                new ClassModule("CG1111"), new Enrollment("20"));
        // same object -> returns true
        assertTrue(classRoom.equals(classRoom));
        // different types -> returns false
        assertFalse(classRoom.equals(1));
        // null -> returns false
        assertFalse(classRoom.equals(null));
        // different maxEnrollment -> returns false
        Classroom differentClassroom = new Classroom(new ClassName("wrongclass"),
                new ClassModule("wrongmodule"), new Enrollment("wrongsize"));
        assertFalse(classRoom.equals(differentClassroom));
    }
}
