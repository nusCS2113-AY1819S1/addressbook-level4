package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_2_HOURS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_3;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalTasks.CS2101_HOMEWORK;
import static seedu.address.testutil.TypicalTasks.CS2113_HOMEWORK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

//@@author emobeany
public class EditTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullEditedTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditTaskCommand(null, null);
    }

    @Test
    public void execute_allFieldsSpecified_editSuccessful() {
        // Task created with default deadline 1/1/2018 which is the same as deadline of third task
        Task editedTask = new TaskBuilder().withTitle(VALID_TITLE_3).withDescription(VALID_DESCRIPTION_3)
                .withModuleCode(VALID_MODULE_CODE_CS2101).withPriority(VALID_PRIORITY_LEVEL_HIGH)
                .withExpectedNumOfHours(Integer.parseInt(VALID_2_HOURS)).build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_THIRD_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // Get the third task from the FilteredTaskList
        expectedModel.updateTask(model.getFilteredTaskList().get(2), editedTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(editTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_editSuccessful() {
        Task editedTask = new TaskBuilder().withTitle(VALID_TITLE_3).withPriority(VALID_PRIORITY_LEVEL_HIGH)
                .withExpectedNumOfHours(Integer.parseInt(VALID_2_HOURS)).withModuleCode(VALID_MODULE_CODE_CS2113).build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_THIRD_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(2), editedTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(editTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCommandResultingInSameTaskAsBefore_editFailure() {
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(CS2113_HOMEWORK).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_THIRD_TASK, descriptor);

        String expectedMessage = EditTaskCommand.MESSAGE_NOT_EDITED;

        assertCommandFailure(editTaskCommand, model, commandHistory, expectedMessage);
    }

    //buggy Test Check with chels if I can change one of the TypicalTask deadline
    @Test
    public void execute_editCommandResultingInDuplicateTasks_editFailure() {
        // since editTaskCommand does not alter deadline, two tasks wth the same deadline are chosen
        Task thirdTaskInList = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FOURTH_TASK,
                new EditTaskDescriptorBuilder(thirdTaskInList).build());

        assertCommandFailure(editTaskCommand, model, commandHistory, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndex_editFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_3).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        String expectedMessage = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

        assertCommandFailure(editTaskCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void isAnyFieldEdited() {
        EditTaskDescriptor nullDescriptor = new EditTaskDescriptorBuilder().withTitle(null).withDescription(null)
                .withModuleCode(null).withPriorityLevel(null).withExpectedNumOfHours(null).build();

        EditTaskDescriptor nonNullDescriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_3).build();

        // all fields null in descriptor -> return false
        assertFalse(nullDescriptor.isAnyFieldEdited());

        // non null field present in descriptor -> return true
        assertTrue(nonNullDescriptor.isAnyFieldEdited());
    }

    @Test
    public void executeUndoRedo_validIndexEditTaskCommand_success() throws Exception {
        // Task with same deadline but different fields to be edited
        Task editedTask = new TaskBuilder().withTitle(VALID_TITLE_3).withDescription(VALID_DESCRIPTION_3)
                .withModuleCode(VALID_MODULE_CODE_CS2101).withPriority(VALID_PRIORITY_LEVEL_HIGH)
                .withExpectedNumOfHours(Integer.parseInt(VALID_2_HOURS)).build();
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_THIRD_TASK, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(taskToEdit, editedTask);
        expectedModel.commitTaskBook();

        // edit -> first person edited
        editTaskCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoTaskBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoTaskBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexEditTaskCommand_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_1).build();
        EditTaskCommand editCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(CS2113_HOMEWORK).build();
        EditTaskDescriptor anotherDescriptor = new EditTaskDescriptorBuilder(CS2101_HOMEWORK).build();
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);

        // same values -> return true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(descriptor);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditTaskCommand(INDEX_SECOND_TASK, descriptor));

        // different descriptor -> return false
        assertNotEquals(standardCommand, new EditTaskCommand(INDEX_FIRST_TASK, anotherDescriptor));
    }
}
