package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

//@@author ChanChunCheong
public class SelectTagCommandTest {
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
    public void execute_validTagFilteredList_success() {
        Tag tag = new Tag("database");
        SelectTagCommand selectTagCommand = new SelectTagCommand(tag);

        String expectedMessage = String.format(selectTagCommand.MESSAGE_SUCCESS, tag.toString());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredTaskList(predicateShowTasksWithSameTag(tag));
        expectedModel.commitTaskBook();

        assertCommandSuccess(selectTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_noMatchingTag_noPersonFound() {
        Tag tag = new Tag("yo");
        SelectTagCommand selectTagCommand = new SelectTagCommand(tag);
        String expectedMessage = String.format(selectTagCommand.MESSAGE_SUCCESS, tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredTaskList(predicateShowTasksWithSameTag(tag));
        expectedModel.commitTaskBook();
        assertCommandSuccess(selectTagCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void equals() {
        Tag tagFriend = new Tag("friend");
        Tag tagEnemy = new Tag ("enemy");
        SelectTagCommand selectFriendTagCommand = new SelectTagCommand(tagFriend);
        SelectTagCommand selectEnemyTagCommand = new SelectTagCommand(tagEnemy);

        // same object -> returns true
        assertTrue(selectFriendTagCommand.equals(selectFriendTagCommand));

        // same values -> returns true
        SelectTagCommand selectFriendTagCommandCopy = new SelectTagCommand(tagFriend);
        assertTrue(selectFriendTagCommand.equals(selectFriendTagCommandCopy));

        // different types -> returns false
        assertFalse(selectFriendTagCommand.equals(1));

        // null -> returns false
        assertFalse(selectFriendTagCommand.equals(null));

        // different tag selected -> returns false
        assertFalse(selectFriendTagCommand.equals(selectEnemyTagCommand));
    }

    private Predicate<Task> predicateShowTasksWithSameTag(Tag tag) {
        return task -> task.getTags().contains(tag);
    }
}
//@@author
