package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_MODULE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_MODULE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_ASSIGNMENT;
import static seedu.address.testutil.TypicalTasks.LAB;
import static seedu.address.testutil.TypicalTasks.PROJECT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameTask() {
        // same object -> returns true
        assertTrue(LAB.isSameTask(LAB));

        // null -> returns false
        assertFalse(LAB.isSameTask(null));

        // different module -> returns false
        Task editedLab = new TaskBuilder(LAB).withModule(VALID_TASK_MODULE_ASSIGNMENT).build();
        assertFalse(LAB.isSameTask(editedLab));

        // different name -> returns false
        editedLab = new TaskBuilder(LAB).withName(VALID_TASK_NAME_ASSIGNMENT).build();
        assertFalse(LAB.isSameTask(editedLab));

        // same name, different attribues -> returns false
        editedLab = new TaskBuilder(LAB).withName(VALID_TASK_NAME_ASSIGNMENT)
                .withDate(VALID_TASK_DATE_ASSIGNMENT).build();
        assertFalse(LAB.isSameTask(editedLab));

        // same name, same module, different attributes -> returns true
        editedLab = new TaskBuilder(LAB).withModule(VALID_TASK_MODULE_TUTORIAL)
                .withDate(VALID_TASK_DATE_ASSIGNMENT).build();
        assertTrue(LAB.isSameTask(editedLab));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task labCopy = new TaskBuilder(LAB).build();
        assertTrue(LAB.equals(labCopy));

        // same object -> returns true
        assertTrue(LAB.equals(LAB));

        // null -> returns false
        assertFalse(LAB.equals(null));

        // different type -> returns false
        assertFalse(LAB.equals(5));

        // different task -> returns false
        assertFalse(LAB.equals(PROJECT));

        // different name -> returns false
        Task editedLab = new TaskBuilder(LAB).withName(VALID_TASK_NAME_ASSIGNMENT).build();
        assertFalse(LAB.equals(editedLab));

        // different module -> returns false
        editedLab = new TaskBuilder(LAB).withModule(VALID_TASK_MODULE_ASSIGNMENT).build();
        assertFalse(LAB.equals(editedLab));

        // different date -> returns false
        editedLab = new TaskBuilder(LAB).withDate(VALID_TASK_DATE_ASSIGNMENT).build();
        assertFalse(LAB.equals(editedLab));

        // different priority -> returns false
        editedLab = new TaskBuilder(LAB).withPriority(VALID_TASK_PRIORITY_ASSIGNMENT).build();
        assertFalse(LAB.equals(editedLab));
    }
}
