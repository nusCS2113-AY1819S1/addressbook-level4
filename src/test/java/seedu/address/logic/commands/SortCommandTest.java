package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getAllTypicalEventList;
import static seedu.address.testutil.TypicalEvents.getSortByDateEventList;
import static seedu.address.testutil.TypicalEvents.getSortByEndTimeEventList;
import static seedu.address.testutil.TypicalEvents.getSortByNameEventList;
import static seedu.address.testutil.TypicalEvents.getSortByStartTimeEventList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;

//@@author: IcedCoffeeBoy

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class SortCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getAllTypicalEventList(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getAllTypicalEventList(), new UserPrefs());
    }

    @Test
    public void equals() {
        String sortByName = "name";
        String sortByDate = "date";
        String sortByStartTime = "starttime";
        String sortByEndTime = "endtime";

        SortCommand sortByNameCommand = new SortCommand(sortByName);
        SortCommand sortByDateCommand = new SortCommand(sortByDate);
        SortCommand sortByStartTimeCommand = new SortCommand(sortByStartTime);
        SortCommand sortByEndTimeCommand = new SortCommand(sortByEndTime);


        // same object -> returns true
        assertTrue(sortByNameCommand.equals(sortByNameCommand));

        // different types -> returns false
        assertFalse(sortByNameCommand.equals(1));

        // null -> returns false
        assertFalse(sortByNameCommand.equals(null));

        // different person -> returns false
        assertFalse(sortByNameCommand.equals(sortByDateCommand));

        // same object -> returns true
        assertTrue(sortByDateCommand.equals(sortByDateCommand));

        // different types -> returns false
        assertFalse(sortByDateCommand.equals(1));

        // null -> returns false
        assertFalse(sortByDateCommand.equals(null));

        // same object -> returns true
        assertTrue(sortByStartTimeCommand.equals(sortByStartTimeCommand));

        // different types -> returns false
        assertFalse(sortByStartTimeCommand.equals(1));

        // null -> returns false
        assertFalse(sortByStartTimeCommand.equals(null));

        // same object -> returns true
        assertTrue(sortByEndTimeCommand.equals(sortByEndTimeCommand));

        // different types -> returns false
        assertFalse(sortByEndTimeCommand.equals(1));

        // null -> returns false
        assertFalse(sortByEndTimeCommand.equals(null));

    }

    @Test
    public void execute_sortByName_success() {
        String sortByParam = "name";
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortByParam);

        ObservableList<Event> expectedEventList = getSortByNameEventList().getEventList();
        expectedModel.sortByName();
        expectedModel.commitEventList();
        ObservableList<Event> eventList = expectedModel.getEventList().getEventList();

        assertEquals(eventList, expectedEventList);

        SortCommand sortByNameCommand = new SortCommand(sortByParam);


        assertCommandSuccess(sortByNameCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_sortByDate_success() {
        String sortByParam = "date";
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortByParam);

        ObservableList<Event> expectedEventList = getSortByDateEventList().getEventList();
        expectedModel.sortByDate();
        expectedModel.commitEventList();
        ObservableList<Event> eventList = expectedModel.getEventList().getEventList();

        assertEquals(expectedEventList, eventList);

        SortCommand sortByNameCommand = new SortCommand(sortByParam);


        assertCommandSuccess(sortByNameCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByStartTime_success() {
        String sortByParam = "starttime";
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortByParam);

        ObservableList<Event> expectedEventList = getSortByStartTimeEventList().getEventList();
        expectedModel.sortByStartTime();
        expectedModel.commitEventList();
        ObservableList<Event> eventList = expectedModel.getEventList().getEventList();

        assertEquals(expectedEventList, eventList);

        SortCommand sortByNameCommand = new SortCommand(sortByParam);


        assertCommandSuccess(sortByNameCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByEndTime_success() {
        String sortByParam = "endtime";
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortByParam);

        ObservableList<Event> expectedEventList = getSortByEndTimeEventList().getEventList();
        expectedModel.sortByEndTime();
        expectedModel.commitEventList();
        ObservableList<Event> eventList = expectedModel.getEventList().getEventList();

        assertEquals(expectedEventList, eventList);

        SortCommand sortByNameCommand = new SortCommand(sortByParam);


        assertCommandSuccess(sortByNameCommand, model, commandHistory, expectedMessage, expectedModel);
    }

}
