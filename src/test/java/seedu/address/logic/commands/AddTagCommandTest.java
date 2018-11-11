package seedu.address.logic.commands;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

//@@author ChanChunCheong
public class AddTagCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeferDeadlineCommand(null, 0);
    }


    @Test
    public void execute_validIndexUnFilteredList_success() throws Exception {
        Tag tag = new Tag("module");
        Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task taskTagged = new TaskBuilder(task).withTags("english", "module").build();
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_TASK, tag);

        String expectedMessage = String.format(addTagCommand.MESSAGE_SUCCESS, taskTagged.getTitle(), tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(task , taskTagged);
        expectedModel.commitTaskBook();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Tag tag = new Tag("friends");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, tag);

        assertCommandFailure(addTagCommand, model, commandHistory,
                AddTagCommand.MESSAGE_TASK_NOT_FOUND);
    }


    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Tag tag = new Tag("module");
        Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task taskTagged = new TaskBuilder(task).withTags("english", "module").build();
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_TASK, tag);

        String expectedMessage = String.format(addTagCommand.MESSAGE_SUCCESS, taskTagged.getTitle(), tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(task , taskTagged);
        expectedModel.commitTaskBook();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Tag tag = new Tag("friends");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, tag);

        assertCommandFailure(addTagCommand, model, commandHistory,
                AddTagCommand.MESSAGE_TASK_NOT_FOUND);
    }

    @Test
    public void execute_setsWillPreventDuplicateTags_success() {
        Tag tag = new Tag("english");
        Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_TASK, tag);

        String expectedMessage = String.format(addTagCommand.MESSAGE_SUCCESS, task.getTitle(), tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.commitTaskBook();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Tag tag = new Tag("friend");
        AddTagCommand addTagToFirstTaskCommand = new AddTagCommand(INDEX_FIRST_TASK, tag);
        AddTagCommand addTagToSecondTaskCommand = new AddTagCommand(INDEX_SECOND_TASK, tag);

        // same object -> returns true
        assertTrue(addTagToFirstTaskCommand.equals(addTagToFirstTaskCommand));

        // same values -> returns true
        AddTagCommand addTagToFirstTaskCommandCopy = new AddTagCommand(INDEX_FIRST_TASK, tag);
        assertTrue(addTagToFirstTaskCommand.equals(addTagToFirstTaskCommandCopy));

        // different types -> returns false
        assertFalse(addTagToFirstTaskCommand.equals(1));

        // null -> returns false
        assertFalse(addTagToFirstTaskCommand.equals(null));

        // different task -> returns false
        assertFalse(addTagToFirstTaskCommand.equals(addTagToSecondTaskCommand));
    }
}
//@@author
