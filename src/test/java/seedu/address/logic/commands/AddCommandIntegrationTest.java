package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Event validEvent = new EventBuilder().build();

        Model expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());
        expectedModel.addEvent(validEvent);
        expectedModel.commitEventManager();

        assertCommandSuccess(new AddCommand(validEvent), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Event eventInList = model.getEventManager().getEventList().get(0);
        assertCommandFailure(new AddCommand(eventInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_EVENT);
    }

}
