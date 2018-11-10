package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.commons.util.DateUtil.generateFirstOfMonth;
import static seedu.planner.commons.util.DateUtil.generateLastOfMonth;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.commons.util.DateUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.Month;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.summary.SummaryByMonthList;
import seedu.planner.model.summary.SummaryList;

/**
 * Integration tests for SummaryByMonthCommand
 */
public class SummaryByMonthCommandTest {

    private Month sampleStartMonth = new Month("mar-2018");
    private Month sampleEndMonth = new Month("may-2018");
    private Month sampleFutureStartMonth = new Month("oct-3018");
    private Month sampleFutureEndMonth = new Month("dec-3018");
    private Month samplePastStartMonth = new Month("oct-1018");
    private Month samplePastEndMonth = new Month("dec-1018");
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
        SummaryByMonthCommand summaryByMonthCommandOne = new SummaryByMonthCommand(sampleStartMonth, sampleEndMonth);
        SummaryByMonthCommand summaryByMonthCommandTwo = new SummaryByMonthCommand(sampleEndMonth, sampleEndMonth);

        // same object -> returns true
        assertTrue(summaryByMonthCommandOne.equals(summaryByMonthCommandOne));

        // same values -> returns true
        SummaryByMonthCommand summaryByMonthCommandOneCopy = new SummaryByMonthCommand(sampleStartMonth, sampleEndMonth);
        assertTrue(summaryByMonthCommandOne.equals(summaryByMonthCommandOneCopy));

        // different types -> returns false
        assertFalse(summaryByMonthCommandOne.equals(1));

        // different SummaryCommand types -> returns false
        SummaryByDateCommand summaryByDateCommand =
                new SummaryByDateCommand(new Date("1-1-2018"), new Date("10-10-2018"));
        assertFalse(summaryByMonthCommandOne.equals(summaryByDateCommand));

        // null -> returns false
        assertFalse(summaryByMonthCommandOne.equals(null));

        // different values -> returns false
        assertFalse(summaryByMonthCommandOne.equals(summaryByMonthCommandTwo));
    }

    @Test
    public void execute_inputMonthRangeNotOverlappingWithDataMonthRange_noSummaryList() {
        // sampleFutureStartMonth > sampleEndMonth & sampleFutureEndMonth > sampleFutureStartMonth
        // -> no summaryList found
        SummaryByMonthCommand command =
                new SummaryByMonthCommand(sampleFutureStartMonth, sampleFutureEndMonth);

        command.execute(model, commandHistory);
        expectedModel.updateFilteredRecordList(
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(sampleFutureStartMonth),
                        generateLastOfMonth(sampleFutureEndMonth)));
        SummaryList summaryList = new SummaryByMonthList(expectedModel.getFilteredRecordList());
        String expectedMessage = String.format(SummaryByMonthCommand.MESSAGE_SUCCESS, summaryList.size());

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
        assertEquals(summaryList, eventListenerStub.getSummaryList());

        // samplePastEndMonth < sampleStartMonth & samplePastStartMonth < samplePastEndMonth
        // -> no summaryList found
        command = new SummaryByMonthCommand(samplePastStartMonth, samplePastEndMonth);

        command.execute(model, commandHistory);
        expectedModel.updateFilteredRecordList(
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(samplePastStartMonth),
                        generateLastOfMonth(samplePastEndMonth)));
        summaryList = new SummaryByMonthList(expectedModel.getFilteredRecordList());
        expectedMessage = String.format(SummaryByMonthCommand.MESSAGE_SUCCESS, summaryList.size());

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
        assertEquals(summaryList, eventListenerStub.getSummaryList());
    }

    @Test
    public void execute_inputMonthRangeOverlappingWithDataMonthRange_summaryList() {
        // Start date and end date are both within date range -> summaryList of all records in range
        SummaryByMonthCommand command = new SummaryByMonthCommand(sampleStartMonth, sampleEndMonth);

        command.execute(model, commandHistory);
        expectedModel.updateFilteredRecordList(
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(sampleStartMonth),
                        generateLastOfMonth(sampleEndMonth)));
        SummaryList summaryList = new SummaryByMonthList(expectedModel.getFilteredRecordList());
        String expectedMessage = String.format(SummaryByMonthCommand.MESSAGE_SUCCESS, summaryList.size());

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
