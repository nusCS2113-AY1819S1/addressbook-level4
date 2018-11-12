package seedu.planner.logic.commands;

import static org.assertj.core.api.Fail.fail;
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
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.summary.SummaryByDateList;
import seedu.planner.model.summary.SummaryList;

/**
 * Integration tests for SummaryByDateCommand
 */
public class SummaryByDateCommandTest {

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
        SummaryByDateCommand summaryByDateCommandOne = new SummaryByDateCommand(sampleStartDate, sampleEndDate);
        SummaryByDateCommand summaryByDateCommandTwo = new SummaryByDateCommand(sampleEndDate, sampleEndDate);

        // same object -> returns true
        assertTrue(summaryByDateCommandOne.equals(summaryByDateCommandOne));

        // same values -> returns true
        SummaryByDateCommand summaryByDateCommandOneCopy = new SummaryByDateCommand(sampleStartDate, sampleEndDate);
        assertTrue(summaryByDateCommandOne.equals(summaryByDateCommandOneCopy));

        // different types -> returns false
        assertFalse(summaryByDateCommandOne.equals(1));

        // different SummaryCommand types -> returns false
        SummaryByCategoryCommand summaryByCategoryCommand =
                new SummaryByCategoryCommand(sampleStartDate, sampleEndDate);
        assertFalse(summaryByDateCommandOne.equals(summaryByCategoryCommand));

        // null -> returns false
        assertFalse(summaryByDateCommandOne.equals(null));

        // different values -> returns false
        assertFalse(summaryByDateCommandOne.equals(summaryByDateCommandTwo));
    }

    @Test
    public void execute_inputDateRangeNotOverlappingWithDataDateRange_noSummaryList() {
        // sampleFutureStartDate > sampleEndDate & sampleFutureEndDate > sampleFutureStartDate
        // -> no summaryList found
        try {
            SummaryByDateCommand command =
                    new SummaryByDateCommand(sampleFutureStartDate, sampleFutureEndDate);

            command.execute(model, commandHistory);
            expectedModel.updateFilteredRecordList(
                    new DateIsWithinIntervalPredicate(sampleFutureStartDate, sampleFutureEndDate));
            SummaryList summaryList = new SummaryByDateList(expectedModel.getFilteredRecordList());
            String expectedMessage = String.format(SummaryByDateCommand.MESSAGE_SUCCESS, summaryList.size());

            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
            assertEquals(Collections.emptyList(), model.getFilteredRecordList());
            assertEquals(summaryList, eventListenerStub.getSummaryList());

            // samplePastEndDate < sampleStartDate & samplePastStartDate < samplePastEndDate
            // -> no summaryList found
            command = new SummaryByDateCommand(samplePastStartDate, samplePastEndDate);

            command.execute(model, commandHistory);
            expectedModel.updateFilteredRecordList(
                    new DateIsWithinIntervalPredicate(samplePastStartDate, samplePastEndDate));
            summaryList = new SummaryByDateList(expectedModel.getFilteredRecordList());
            expectedMessage = String.format(SummaryByDateCommand.MESSAGE_SUCCESS, summaryList.size());

            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
            assertEquals(Collections.emptyList(), model.getFilteredRecordList());
            assertEquals(summaryList, eventListenerStub.getSummaryList());
        } catch (Exception e) {
            fail("this should not happen");
        }
    }

    @Test
    public void execute_inputDateRangeOverlappingWithDataDateRange_summaryList() {
        // Start date and end date are both within date range -> summaryList of all records in range
        try {
            SummaryByDateCommand command = new SummaryByDateCommand(sampleStartDate, sampleEndDate);

            command.execute(model, commandHistory);
            expectedModel.updateFilteredRecordList(
                    new DateIsWithinIntervalPredicate(sampleStartDate, sampleEndDate));
            SummaryList summaryList = new SummaryByDateList(expectedModel.getFilteredRecordList());
            String expectedMessage = String.format(SummaryByDateCommand.MESSAGE_SUCCESS, summaryList.size());

            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
            assertEquals(expectedModel.getFilteredRecordList(), model.getFilteredRecordList());
            assertEquals(summaryList, eventListenerStub.getSummaryList());
        } catch (Exception e) {
            fail("This should not happen");
        }
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
