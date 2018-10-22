package seedu.planner.logic.commands;

import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.planner.testutil.TypicalRecords.TYPICAL_END_DATE;
import static seedu.planner.testutil.TypicalRecords.TYPICAL_START_DATE;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.FinancialPlannerBuilder;

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
        model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
        ReadOnlyFinancialPlanner emptyFinancialPlanner = new FinancialPlannerBuilder().build();
        emptyModel = new ModelManager(emptyFinancialPlanner, new UserPrefs());
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

    /**
     * Tests that the list shown after filtering according to date interval given is equal to the expected model
     * after the same filter
     */
    @Test
    public void execute_listIsFilteredWithDateIntervals_showsCorrectList() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        Model expectedModelAfterFilter = filterListWithSpecificDateInterval(
                expectedModel, TYPICAL_START_DATE, TYPICAL_END_DATE);
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
