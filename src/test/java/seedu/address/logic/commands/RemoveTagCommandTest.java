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
public class RemoveTagCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

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
    public void execute_validIndexUnFilteredList_success() {
        Tag tag = new Tag("english");
        Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task taskTagged = new TaskBuilder(task).withTags().build();
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(INDEX_FIRST_TASK, tag);

        String expectedMessage = String.format(removeTagCommand.MESSAGE_SUCCESS, taskTagged.getTitle(), tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(task , taskTagged);
        expectedModel.commitTaskBook();

        assertCommandSuccess(removeTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Tag tag = new Tag("friends");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(outOfBoundIndex, tag);

        assertCommandFailure(removeTagCommand, model, commandHistory,
                removeTagCommand.MESSAGE_TASK_NOT_FOUND);
    }


    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Tag tag = new Tag("english");
        Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task taskTagged = new TaskBuilder(task).withTags().build();
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(INDEX_FIRST_TASK, tag);

        String expectedMessage = String.format(removeTagCommand.MESSAGE_SUCCESS, taskTagged.getTitle(), tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(task , taskTagged);
        expectedModel.commitTaskBook();

        assertCommandSuccess(removeTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Tag tag = new Tag("friends");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(outOfBoundIndex, tag);

        assertCommandFailure(removeTagCommand, model, commandHistory,
                AddTagCommand.MESSAGE_TASK_NOT_FOUND);
    }

    @Test
    public void execute_tagCannotBeFound_throwsCommandException() {
        Tag tag = new Tag("module");
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(INDEX_FIRST_TASK, tag);

        assertCommandFailure(removeTagCommand, model, commandHistory,
                removeTagCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void equals() {
        Tag tag = new Tag("friend");
        RemoveTagCommand removeTagToFirstTaskCommand = new RemoveTagCommand(INDEX_FIRST_TASK, tag);
        RemoveTagCommand removeTagToSecondTaskCommand = new RemoveTagCommand(INDEX_SECOND_TASK, tag);

        // same object -> returns true
        assertTrue(removeTagToFirstTaskCommand.equals(removeTagToFirstTaskCommand));

        // same values -> returns true
        RemoveTagCommand removeTagToFirstTaskCommandCopy = new RemoveTagCommand(INDEX_FIRST_TASK, tag);
        assertTrue(removeTagToFirstTaskCommand.equals(removeTagToFirstTaskCommandCopy));

        // different types -> returns false
        assertFalse(removeTagToFirstTaskCommand.equals(1));

        // null -> returns false
        assertFalse(removeTagToFirstTaskCommand.equals(null));

        // different task -> returns false
        assertFalse(removeTagToFirstTaskCommand.equals(removeTagToSecondTaskCommand));
    }
}
//@@author
