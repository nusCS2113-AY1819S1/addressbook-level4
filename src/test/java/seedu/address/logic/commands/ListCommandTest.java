package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.address.testutil.TypicalRecords.TYPICAL_END_DATE;
import static seedu.address.testutil.TypicalRecords.TYPICAL_START_DATE;
import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.Date;
import seedu.address.model.record.DateIsWithinIntervalPredicate;
import seedu.address.model.record.Record;
import seedu.address.testutil.AddressBookBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model emptyModel;
    private Model expectedModel;
    private Model expectedEmptyModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();
        emptyModel = new ModelManager(emptyAddressBook, new UserPrefs());
        expectedEmptyModel = emptyModel;
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFilteredWithDefault_showsEverything() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    // TODO: Should I hardcode this?
    /**
     * Tests that the list shown after filtering according to date interval given is equal to the expected model
     * after the same filter
     */
    @Test
    public void execute_listIsFilteredWithDateIntervals_showsCorrectList() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        Model expectedModelAfterFilter = filterListWithSpecificDateInterval(expectedModel, TYPICAL_START_DATE,
                TYPICAL_END_DATE);
        assertCommandSuccess(new ListCommand(TYPICAL_START_DATE, TYPICAL_END_DATE), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS, expectedModelAfterFilter);
    }

    @Test
    public void execute_emptyListFiltered_showsCorrectList() {
        assertCommandSuccess(new ListCommand(TYPICAL_START_DATE, TYPICAL_END_DATE), emptyModel, commandHistory,
                ListCommand.MESSAGE_SUCCESS, expectedEmptyModel);
    }

    /**
     * Filters a model based on the predicate DateIsWithinIntervalPredicate
     * @param toFilter
     * @param startDate all dates must be later or equal to this date
     * @param endDate all dates must be earlier or equal to this date
     * @return Model
     */
    private Model filterListWithSpecificDateInterval(Model toFilter, Date startDate, Date endDate) {
        Predicate<Record> predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
        toFilter.updateFilteredRecordList(predicate);
        Model filtered = toFilter;
        return filtered;
    }
}
