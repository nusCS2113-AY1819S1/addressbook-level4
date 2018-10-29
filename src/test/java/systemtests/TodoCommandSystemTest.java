package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TASK2;

import static seedu.address.testutil.TypicalTodos.KEYWORD_MATCHING_TASKC;
import static seedu.address.testutil.TypicalTodos.TASK1;
import static seedu.address.testutil.TypicalTodos.TASK2;
import static seedu.address.testutil.TypicalTodos.TASKB;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandSuggestion;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;
import seedu.address.testutil.TodoUtil;

//@@author linnnruoo
public class TodoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        /* ------------------------ Perform todo operations on the shown unfiltered list --------------------------- */

        /* Case: add a todo
         * -> added
         */
        Todo toAdd = TASK1;
        String command = "   " + TodoCommand.COMMAND_WORD + "  " + TITLE_DESC_TASK1 + "  " + CONTENT_DESC_TASK1 + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a todo task with parameters in reverse order -> added */
        toAdd = TASK2;
        command = TodoCommand.COMMAND_WORD + CONTENT_DESC_TASK2 + TITLE_DESC_TASK2;
        assertCommandSuccess(command, toAdd);

        /* Case: add a todo  with title the same as another todo in the address book except content -> added */
        toAdd = new TodoBuilder(TASK1).withTitle(VALID_TITLE_TASK2).build();
        command = TodoCommand.COMMAND_WORD + TITLE_DESC_TASK2 + CONTENT_DESC_TASK1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a todo  with content the same as another todo in the address book except title -> added */
        toAdd = new TodoBuilder(TASK1).withContent(VALID_CONTENT_TASK2).build();
        command = TodoCommand.COMMAND_WORD + TITLE_DESC_TASK1 + CONTENT_DESC_TASK2;
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform todo operation on the shown filtered list ---------------------------- */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_TASKC);
        assertCommandSuccess(TASKB);

        /* ----------------------------------- Perform invalid todo operations ------------------------------------- */

        /* Case: missing title -> rejected */
        command = TodoCommand.COMMAND_WORD + TITLE_DESC_TASK2;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE));

        /* Case: missing title -> rejected */
        command = TodoCommand.COMMAND_WORD + CONTENT_DESC_TASK2;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "todos " + TodoUtil.getTodoDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND
                + "\n" + String.format(CommandSuggestion.SUGGESTION_HEADER, TodoCommand.COMMAND_WORD));

        /* Case: invalid content -> rejected */
        command = TodoCommand.COMMAND_WORD + TITLE_DESC_TASK1 + INVALID_CONTENT_DESC;
        assertCommandFailure(command, Content.MESSAGE_CONTENT_CONSTRAINTS);

        /* Case: invalid title -> rejected */
        command = TodoCommand.COMMAND_WORD + INVALID_TITLE_DESC + CONTENT_DESC_TASK1;
        assertCommandFailure(command, Title.MESSAGE_TITLE_CONSTRAINTS);
    }

    /**
     * Executes the {@code TodoCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code TodoCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code TodoListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Todo toAdd) {
        assertCommandSuccess(TodoUtil.getTodoCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Todo)}. Executes {@code command}
     * instead.
     * @see TodoCommandSystemTest#assertCommandSuccess(Todo)
     */
    private void assertCommandSuccess(String command, Todo toAdd) {
        Model expectedModel = getModel();
        expectedModel.addTodo(toAdd);
        String expectedResultMessage = String.format(TodoCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Todo)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TodoListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see TodoCommandSystemTest#assertCommandSuccess(String, Todo)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code TodoListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
