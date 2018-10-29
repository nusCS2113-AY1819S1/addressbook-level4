package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_GROUP;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectGroupCommand}.
 */
public class SelectGroupCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastGroupIndex = Index.fromOneBased(model.getFilteredGroupList().size());

        assertExecutionSuccess(INDEX_FIRST_GROUP);
        assertExecutionSuccess(INDEX_THIRD_GROUP);
        assertExecutionSuccess(lastGroupIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        showGroupAtIndex(expectedModel, INDEX_FIRST_GROUP);

        assertExecutionSuccess(INDEX_FIRST_GROUP);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        showGroupAtIndex(expectedModel, INDEX_FIRST_GROUP);

        Index outOfBoundsIndex = INDEX_SECOND_GROUP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getGroupList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectGroupCommand selectFirstCommand = new SelectGroupCommand(INDEX_FIRST_GROUP);
        SelectGroupCommand selectSecondCommand = new SelectGroupCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectGroupCommand selectFirstCommandCopy = new SelectGroupCommand(INDEX_FIRST_GROUP);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different group -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }


    /**
     * Executes a {@code SelectGroupCommand} with the given {@code index},
     * and checks that {@code JumpToGroupListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectGroupCommand selectGroupCommand = new SelectGroupCommand(index);
        String expectedMessage = String.format(SelectGroupCommand.MESSAGE_SELECT_GROUP_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectGroupCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToGroupListRequestEvent lastEvent =
                (JumpToGroupListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectGroupCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectGroupCommand selectGroupCommand = new SelectGroupCommand(index);
        assertCommandFailure(selectGroupCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

}
