package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.NUM_EVENTS_ATTENDING_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.AttendanceContainsUserPredicate;
import seedu.address.model.user.User;
import seedu.address.testutil.UserBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AttendingCommand}.
 */
public class AttendingCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private User user;
    private String expectedMessage;

    @Before
    public void setUp() {
        user = new UserBuilder().build();
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        model.logUser(user);
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel.logUser(user);
        expectedModel.updateFilteredEventList(new AttendanceContainsUserPredicate(user.getUsername()));
        expectedMessage = String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, NUM_EVENTS_ATTENDING_ADMIN);
    }

    @Test
    public void execute_unfilteredList_showsEventsAttending() {
        assertCommandSuccess(new AttendingCommand(), model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_showsEventsAttending() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);
        assertCommandSuccess(new AttendingCommand(), model, commandHistory, expectedMessage, expectedModel);
    }
}
