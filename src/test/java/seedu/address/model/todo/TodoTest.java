package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TASK2;
import static seedu.address.testutil.TypicalTodos.TASK1;
import static seedu.address.testutil.TypicalTodos.TASK2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TodoBuilder;

//@@author linnnruoo
public class TodoTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameTodo() {
        // same object -> returns true
        assertTrue(TASK1.isSameTodo(TASK1));

        // null -> returns false
        assertFalse(TASK1.isSameTodo(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Todo task1Copy = new TodoBuilder(TASK1).build();
        assertTrue(TASK1.equals(task1Copy));

        // same object -> returns true
        assertTrue(TASK1.equals(TASK1));

        // null -> returns false
        assertFalse(TASK1.equals(null));

        // different type -> returns false
        assertFalse(TASK1.equals(5));

        // different todo -> returns false
        assertFalse(TASK1.equals(TASK2));

        // different title -> returns false
        Todo editedTask1 = new TodoBuilder(TASK1).withTitle(VALID_TITLE_TASK2).build();
        assertFalse(TASK1.equals(editedTask1));

        // different content -> returns false
        editedTask1 = new TodoBuilder(TASK1).withContent(VALID_CONTENT_TASK2).build();
        assertFalse(TASK1.equals(editedTask1));
    }
}
