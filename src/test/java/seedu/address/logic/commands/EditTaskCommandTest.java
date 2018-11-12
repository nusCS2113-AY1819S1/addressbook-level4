package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_MODULE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalExpenditures.getTypicalExpenditureTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTodoList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

public class EditTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(),
            getTypicalExpenditureTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeAllFieldsSpecifiedUnfilteredListSuccess() {
        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.commitTodoList();

        assertCommandSuccess(editTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeSomeFieldsSpecifiedUnfilteredListSuccess() {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withName(VALID_TASK_NAME_TUTORIAL).withDate(VALID_TASK_DATE_TUTORIAL)
                .withModule(VALID_TASK_MODULE_TUTORIAL).build();

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_TUTORIAL).withDate(VALID_TASK_DATE_TUTORIAL)
                .withModule(VALID_TASK_MODULE_TUTORIAL).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);
        expectedModel.commitTodoList();

        assertCommandSuccess(editTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeNoFieldSpecifiedUnfilteredListSuccess() {
        EditTaskCommand editTaskCommand =
                new EditTaskCommand(INDEX_FIRST_TASK, new EditTaskCommand.EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.commitTodoList();

        assertCommandSuccess(editTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeFilteredListSuccess() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withName(VALID_TASK_NAME_TUTORIAL).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_TUTORIAL).build());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.commitTodoList();

        assertCommandSuccess(editTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeDuplicatePersonUnfilteredListFailure() {
        Task firstTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_SECOND_TASK, descriptor);

        assertCommandFailure(editTaskCommand, model, commandHistory, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void executeDuplicateTaskFilteredListFailure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        // edit task in filtered list into a duplicate in to-do list
        Task taskInList = model.getTodoList().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder(taskInList).build());

        assertCommandFailure(editTaskCommand, model, commandHistory, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void executeInvalidTaskIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_TUTORIAL).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of to-do list
     */

    @Test
    public void executeInvalidPersonIndexFilteredListFailure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of to-do list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTodoList().getTaskList().size());

        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_TUTORIAL).build());

        assertCommandFailure(editTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    /*
    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        // edit -> first person edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    @Test
    public void executeUndoRedoInvalidIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_TUTORIAL).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        // execution failed -> to-do list state not added into model
        assertCommandFailure(editTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single to-do list state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
    /*
    /**
     * 1. Edits a {@code Person} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    /*
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> edits same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/


    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, DESC_ASSIGNMENT);

        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor copyDescriptor = new EditTaskCommand.EditTaskDescriptor(DESC_ASSIGNMENT);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_ASSIGNMENT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_TUTORIAL)));
    }
}
