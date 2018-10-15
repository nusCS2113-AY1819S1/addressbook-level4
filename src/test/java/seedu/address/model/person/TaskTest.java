package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_1ST_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_31ST_MARCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_2;
import static UnRefactored.testutil.TypicalTasks.CS2113_TASK_1;
import static UnRefactored.testutil.TypicalTasks.CS2113_TASK_2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.task.Task;
import UnRefactored.testutil.TaskBuilder;

public class TaskTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameTask() {
        // same object -> returns true
        assertTrue(CS2113_TASK_1.isSameTask(CS2113_TASK_1));

        // null -> returns false
        assertFalse(CS2113_TASK_1.isSameTask(null));

        // different deadline -> returns false
        Task editedTask1 = new TaskBuilder(CS2113_TASK_1).withDeadline(VALID_DEADLINE_1ST_JAN).build();
        assertFalse(CS2113_TASK_1.isSameTask(editedTask1));

        // different title -> returns false
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withTitle(VALID_TITLE_2).build();
        assertFalse(CS2113_TASK_1.isSameTask(editedTask1));

        // same title, same deadline, different attributes -> returns true
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withDescription(DESCRIPTION_DESC_2)
                .withPriority(PRIORITY_LEVEL_DESC_HIGH).build();
        assertTrue(CS2113_TASK_1.isSameTask(editedTask1));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task task1Copy = new TaskBuilder(CS2113_TASK_1).build();
        assertTrue(CS2113_TASK_1.equals(task1Copy));

        // same object -> returns true
        assertTrue(CS2113_TASK_1.equals(CS2113_TASK_1));

        // null -> returns false
        assertFalse(CS2113_TASK_1.equals(null));

        // different type -> returns false
        assertFalse(CS2113_TASK_1.equals(5));

        // different task -> returns false
        assertFalse(CS2113_TASK_1.equals(CS2113_TASK_2));

        // different name -> returns false
        Task editedTask1 = new TaskBuilder(CS2113_TASK_1).withDeadline(VALID_DEADLINE_31ST_MARCH).build();
        assertFalse(CS2113_TASK_1.equals(editedTask1));

        // different phone -> returns false
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withTitle(VALID_TITLE_2).build();
        assertFalse(CS2113_TASK_1.equals(editedTask1));

        // different email -> returns false
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withDescription(VALID_DESCRIPTION_2).build();
        assertFalse(CS2113_TASK_1.equals(editedTask1));

        // different address -> returns false
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
        assertFalse(CS2113_TASK_1.equals(editedTask1));
    }
}
