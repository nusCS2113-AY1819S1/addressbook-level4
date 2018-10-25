package seedu.address.model.classroom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalClassrooms.T16;
import static seedu.address.testutil.TypicalClassrooms.T17;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ClassroomBuilder;

public class ClassroomTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameClassroom() {
        // same object -> returns true
        assertTrue(T16.isSameClassroom(T16));

        // null -> returns false
        assertFalse(T16.isSameClassroom(null));

        //same classname and module, different enrollment size -> returns true
        Classroom editedT16 = new ClassroomBuilder(T16).withEnrollment("21").build();
        assertTrue(T16.isSameClassroom(editedT16));

        //same classname and different module -> returns false
        editedT16 = new ClassroomBuilder(T16).withModuleCode("CG1112").build();
        assertFalse(T16.isSameClassroom(editedT16));

        //different classname
        editedT16 = new ClassroomBuilder(T16).withClassName("T17").build();
        assertFalse(T16.isSameClassroom(editedT16));
    }

    @Test
    public void equals() {
        Classroom classRoomCopy = new ClassroomBuilder(T16).build();
        // same values -> returns true
        assertTrue(T16.equals(classRoomCopy));

        // same object -> returns true
        assertTrue(T16.equals(T16));

        // null -> returns false
        assertFalse(T16.equals(null));

        // different types -> returns false
        assertFalse(T16.equals(1));

        // different classroom -> returns false
        assertFalse(T16.equals(T17));

        // different maxEnrollment -> returns false
        Classroom editedT16 = new ClassroomBuilder(T16).withEnrollment("21").build();;
        assertFalse(T16.equals(editedT16));

        // different className -> returns false
        editedT16 = new ClassroomBuilder(T16).withClassName("T17").build();;
        assertFalse(T16.equals(editedT16));

        // different moduleCode -> returns false
        editedT16 = new ClassroomBuilder(T16).withModuleCode("CG1112").build();;
        assertFalse(T16.equals(editedT16));
    }
}
