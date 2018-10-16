package unrefactored.systemtests;

import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_1ST_JAN;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_31ST_MARCH;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_LOW;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_1ST_JAN;
import static unrefactored.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unrefactored.testutil.TypicalTasks.CS2113_TASK_1;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;
import unrefactored.commons.core.Messages;
import unrefactored.testutil.TaskBuilder;
import unrefactored.testutil.TaskUtil;

public class AddTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a task to a non-empty task book, command with leading spaces and trailing spaces
         * -> added
         */
        Task toAdd = CS2113_TASK_1;
        String command = "   " + AddTaskCommand.COMMAND_WORD + "  " + DEADLINE_DESC_31ST_MARCH
                + "  " + TITLE_DESC_1 + " " + DESCRIPTION_DESC_1 + "   " + PRIORITY_LEVEL_DESC_LOW + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding CS2113_TASK_1 to the list -> CS2113_TASK_1 deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding CS2113_TASK_1 to the list -> CS2113_TASK_1 added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a task with all fields same as another task in the address book except name -> added */
        toAdd = new TaskBuilder(CS2113_TASK_1).withDeadline(VALID_DEADLINE_1ST_JAN).build();
        command = AddTaskCommand.COMMAND_WORD + DEADLINE_DESC_1ST_JAN + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except phone and email
         * -> added
         */
        toAdd = new TaskBuilder(CS2113_TASK_1).withTitle(TITLE_DESC_2).withDescription(DESCRIPTION_DESC_2).build();
        command = TaskUtil.getAddTaskCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty task book -> added */
        deleteAllTasks();
        assertCommandSuccess(CS2113_TASK_1);

        /* Case: add a task with tags, command with parameters in random order -> added *//*
        toAdd = CS2113_TASK_2;
        command = AddTaskCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);*/

        /* Case: add a task, missing tags -> added *//*
        assertCommandSuccess(HOON);*/

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the task list before adding -> added *//*
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);*/

        /* ------------------------ Perform add operation while a task card is selected --------------------------- */

        /* Case: selects first card in the task list, add a task -> added, card selection remains unchanged *//*
        selectTask(Index.fromOneBased(1));
        assertCommandSuccess(CARL);*/

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate task -> rejected */
        command = TaskUtil.getAddTaskCommand(CS2113_TASK_1);
        assertCommandFailure(command, AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a duplicate task except with different description -> rejected */
        toAdd = new TaskBuilder(CS2113_TASK_1).withDescription(DESCRIPTION_DESC_2).build();
        command = TaskUtil.getAddTaskCommand(toAdd);
        assertCommandFailure(command, AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a duplicate task except with different priority level -> rejected */
        toAdd = new TaskBuilder(CS2113_TASK_1).withPriority(PRIORITY_LEVEL_DESC_HIGH).build();
        command = TaskUtil.getAddTaskCommand(toAdd);
        assertCommandFailure(command, AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: missing title -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DEADLINE_DESC_31ST_MARCH + DESCRIPTION_DESC_1 + PRIORITY_LEVEL_DESC_LOW;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DEADLINE_DESC_31ST_MARCH + TITLE_DESC_1 + PRIORITY_LEVEL_DESC_LOW;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing priority level -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DEADLINE_DESC_31ST_MARCH + TITLE_DESC_1 + DESCRIPTION_DESC_1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + TaskUtil.getTaskDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid priority level -> rejected */
        command = AddTaskCommand.COMMAND_WORD + TITLE_DESC_1 + DESCRIPTION_DESC_1 + INVALID_PRIORITY_LEVEL_DESC;
        assertCommandFailure(command, PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddTaskCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTaskCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Task toAdd) {
        assertCommandSuccess(TaskUtil.getAddTaskCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Task)}. Executes {@code command}
     * instead.
     * @see AddTaskCommandSystemTest#assertCommandSuccess(Task)
     */
    private void assertCommandSuccess(String command, Task toAdd) {
        Model expectedModel = getModel();
        expectedModel.addTask(toAdd);
        String expectedResultMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Task)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddTaskCommandSystemTest#assertCommandSuccess(String, Task)
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
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
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
