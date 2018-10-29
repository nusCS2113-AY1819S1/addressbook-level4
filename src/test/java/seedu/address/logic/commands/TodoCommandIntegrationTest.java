package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;

//@@author linnnruoo
/**
 * Contains integration tests (interaction with the Model) for {@code TodoCommand}.
 */
public class TodoCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTodo_success() {
        Todo validTodo = new TodoBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTodo(validTodo);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new TodoCommand(validTodo), model, commandHistory,
                String.format(TodoCommand.MESSAGE_SUCCESS, validTodo), expectedModel);
    }
}
