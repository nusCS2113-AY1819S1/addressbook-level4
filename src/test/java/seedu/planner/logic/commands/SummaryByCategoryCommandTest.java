package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.Month;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.summary.SummaryByCategoryList;
import seedu.planner.model.summary.SummaryList;

/**
 * Integration tests for SummaryByCategoryCommand
 */
public class SummaryByCategoryCommandTest {

    private Date sampleStartDate = new Date("2-3-2018");
    private Date sampleEndDate = new Date("10-5-2018");
    private Date sampleFutureStartDate = new Date("2-10-3018");
    private Date sampleFutureEndDate = new Date("10-12-3018");
    private Date samplePastStartDate = new Date("2-10-1018");
    private Date samplePastEndDate = new Date("10-12-1018");
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory;
    private EventListenerStub eventListenerStub;

    @Before
    public void setup() {
        model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        commandHistory = new CommandHistory();
        eventListenerStub = new EventListenerStub();
    }

    @Test
    public void equals() {
        SummaryByCategoryCommand summaryByCategoryCommandOne = new SummaryByCategoryCommand(sampleStartDate, sampleEndDate);
        SummaryByCategoryCommand summaryByCategoryCommandTwo = new SummaryByCategoryCommand(sampleEndDate, sampleEndDate);

        // same object -> returns true
        assertTrue(summaryByCategoryCommandOne.equals(summaryByCategoryCommandOne));

        // same values -> returns true
        SummaryByCategoryCommand summaryByCategoryCommandOneCopy = new SummaryByCategoryCommand(sampleStartDate, sampleEndDate);
        assertTrue(summaryByCategoryCommandOne.equals(summaryByCategoryCommandOneCopy));

        // different types -> returns false
        assertFalse(summaryByCategoryCommandOne.equals(1));

        // different SummaryCommand types -> returns false
        SummaryByMonthCommand summaryByMonthCommand =
                new SummaryByMonthCommand(new Month("apr-2018"), new Month("sep-2018"));
        assertFalse(summaryByCategoryCommandOne.equals(summaryByMonthCommand));

        // null -> returns false
        assertFalse(summaryByCategoryCommandOne.equals(null));

        // different values -> returns false
        assertFalse(summaryByCategoryCommandOne.equals(summaryByCategoryCommandTwo));
    }

    @Test
    public void execute_inputDateRangeNotOverlappingWithDataDateRange_noSummaryList() {
        // sampleFutureStartDate > sampleEndDate & sampleFutureEndDate > sampleFutureStartDate
        // -> no summaryList found
        SummaryByCategoryCommand command =
                new SummaryByCategoryCommand(sampleFutureStartDate, sampleFutureEndDate);

        command.execute(model, commandHistory);
        expectedModel.updateFilteredRecordList(
                new DateIsWithinIntervalPredicate(sampleFutureStartDate, sampleFutureEndDate));
        SummaryList summaryList = new SummaryByCategoryList(expectedModel.getFilteredRecordList());
        String expectedMessage = String.format(SummaryByCategoryCommand.MESSAGE_SUCCESS, summaryList.size());

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
        assertEquals(summaryList, eventListenerStub.getSummaryList());

        // samplePastEndDate < sampleStartDate & samplePastStartDate < samplePastEndDate
        // -> no summaryList found
        command = new SummaryByCategoryCommand(samplePastStartDate, samplePastEndDate);

        command.execute(model, commandHistory);
        expectedModel.updateFilteredRecordList(
                new DateIsWithinIntervalPredicate(samplePastStartDate, samplePastEndDate));
        summaryList = new SummaryByCategoryList(expectedModel.getFilteredRecordList());
        expectedMessage = String.format(SummaryByCategoryCommand.MESSAGE_SUCCESS, summaryList.size());

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
        assertEquals(summaryList, eventListenerStub.getSummaryList());
    }

    @Test
    public void execute_inputDateRangeOverlappingWithDataDateRange_summaryList() {
        // Start date and end date are both within date range -> summaryList of all records in range
        SummaryByCategoryCommand command = new SummaryByCategoryCommand(sampleStartDate, sampleEndDate);

        command.execute(model, commandHistory);
        expectedModel.updateFilteredRecordList(
                new DateIsWithinIntervalPredicate(sampleStartDate, sampleEndDate));
        SummaryList summaryList = new SummaryByCategoryList(expectedModel.getFilteredRecordList());
        String expectedMessage = String.format(SummaryByCategoryCommand.MESSAGE_SUCCESS, summaryList.size());

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredRecordList(), model.getFilteredRecordList());
        assertEquals(summaryList, eventListenerStub.getSummaryList());
    }

    public class EventListenerStub {

        private SummaryList summaryList;

        public EventListenerStub() {
            EventsCenter.getInstance().registerHandler(this);
        }

        @Subscribe
        private void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
            summaryList = event.data;
        }

        public SummaryList getSummaryList() {
            return summaryList;
        }
    }
}
