package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_1ST_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_2;
import static seedu.address.testutil.TypicalTasks.CS2113_TASK_1;
import static seedu.address.testutil.TypicalTasks.CS2113_TASK_2;

import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.testutil.TaskBuilder;

public class TaskTest {
    private static final Logger logger = LogsCenter.getLogger(TaskTest.class);
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
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withDescription(VALID_DESCRIPTION_2)
                .withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
        assertTrue(CS2113_TASK_1.isSameTask(editedTask1));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task task1Copy = new TaskBuilder(CS2113_TASK_1).build();
        logger.info("original : " + CS2113_TASK_1);
        logger.info("copy : " + task1Copy);

        assertTrue(CS2113_TASK_1.equals(task1Copy));

        // same object -> returns true
        assertTrue(CS2113_TASK_1.equals(CS2113_TASK_1));

        // null -> returns false
        assertFalse(CS2113_TASK_1.equals(null));

        // different type -> returns false
        assertFalse(CS2113_TASK_1.equals(5));

        // different task -> returns false
        assertFalse(CS2113_TASK_1.equals(CS2113_TASK_2));

        // different deadline -> returns false
        Task editedTask1 = new TaskBuilder(CS2113_TASK_1).withDeadline(VALID_DEADLINE_1ST_JAN).build();
        assertFalse(CS2113_TASK_1.equals(editedTask1));

        // different title -> returns false
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withTitle(VALID_TITLE_2).build();
        assertFalse(CS2113_TASK_1.equals(editedTask1));

        // different description -> returns false
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withDescription(VALID_DESCRIPTION_2).build();
        assertFalse(CS2113_TASK_1.equals(editedTask1));

        // different priority level -> returns false
        editedTask1 = new TaskBuilder(CS2113_TASK_1).withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
        assertFalse(CS2113_TASK_1.equals(editedTask1));
    }
}
