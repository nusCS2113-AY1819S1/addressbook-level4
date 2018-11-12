package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.AttendeeContainsEmailPredicate;
import seedu.address.model.event.EventContainsAttendeeAndDatePredicate;
import seedu.address.model.event.TimeType;
import seedu.address.model.person.Person;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccessNoDateFilter(INDEX_FIRST_PERSON);
        assertExecutionSuccessNoDateFilter(INDEX_THIRD_PERSON);
        assertExecutionSuccessNoDateFilter(lastPersonIndex);

        assertExecutionSuccessWithDateFilter(INDEX_FIRST_PERSON, VALID_DATE, TimeType.DAY);
        assertExecutionSuccessWithDateFilter(INDEX_SECOND_PERSON, VALID_MONTH, TimeType.MONTH);
        assertExecutionSuccessWithDateFilter(INDEX_THIRD_PERSON, VALID_YEAR, TimeType.YEAR);
        assertExecutionSuccessWithDateFilter(lastPersonIndex, VALID_DATE, TimeType.DAY);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailureNoDateFilter(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertExecutionFailureWithDateFilter(outOfBoundsIndex,
                VALID_DATE, TimeType.DAY, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertExecutionSuccessNoDateFilter(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailureNoDateFilter(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertExecutionFailureWithDateFilter(outOfBoundsIndex,
                VALID_DATE, TimeType.DAY, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTimeTypeUnfilteredList_failure() {
        assertExecutionFailureWithDateFilter(INDEX_FIRST_PERSON,
                VALID_DATE, null, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void equals_noDateFilter() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    @Test
    public void equals_hasDateFilter() {
        SelectCommand selectFirstWithDateCommand = new SelectCommand(INDEX_FIRST_PERSON, VALID_DATE, TimeType.DAY);
        SelectCommand selectFirstWithMonthCommand = new SelectCommand(INDEX_FIRST_PERSON, VALID_MONTH, TimeType.MONTH);
        SelectCommand selectFirstWithYearCommand = new SelectCommand(INDEX_FIRST_PERSON, VALID_YEAR, TimeType.YEAR);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON, VALID_YEAR, TimeType.YEAR);


        // same object -> returns true
        assertTrue(selectFirstWithDateCommand.equals(selectFirstWithDateCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandWithDateCopy = new SelectCommand(INDEX_FIRST_PERSON, VALID_DATE, TimeType.DAY);
        assertTrue(selectFirstWithDateCommand.equals(selectFirstCommandWithDateCopy));

        // different types -> returns false
        assertFalse(selectFirstWithDateCommand.equals(selectFirstWithYearCommand));
        assertFalse(selectFirstWithDateCommand.equals(selectFirstWithMonthCommand));

        // null -> returns false
        assertFalse(selectFirstWithDateCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstWithDateCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccessNoDateFilter(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());

        //Update expectedModel
        Person personChosen = expectedModel.getFilteredPersonList().get(index.getZeroBased());
        String personEmail = personChosen.getEmail().toString();
        AttendeeContainsEmailPredicate predicate = new AttendeeContainsEmailPredicate(personEmail);
        expectedModel.updateFilteredEventList(predicate);
        expectedModel.sortByDate();

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, {@code date} and {@code type}
     * and checks that {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccessWithDateFilter(Index index, String date, TimeType type) {
        SelectCommand selectCommand = new SelectCommand(index, date, type);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());

        //Update expectedModel
        Person personChosen = expectedModel.getFilteredPersonList().get(index.getZeroBased());
        String personEmail = personChosen.getEmail().toString();
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate(personEmail, date, type);
        expectedModel.updateFilteredEventList(predicate);
        expectedModel.sortByDate();

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }


    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailureNoDateFilter(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, {@code date} and {@code type},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailureWithDateFilter(Index index, String date, TimeType type, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index, date, type);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }




}
