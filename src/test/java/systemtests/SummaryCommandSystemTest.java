package systemtests;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.commons.util.DateUtil.generateFirstOfMonth;
import static seedu.planner.commons.util.DateUtil.generateLastOfMonth;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.testutil.TypicalRecords.BURSARY;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.IDA;
import static seedu.planner.testutil.TypicalRecords.JAP;
import static seedu.planner.testutil.TypicalRecords.KOREAN;
import static seedu.planner.testutil.TypicalRecords.ZT;
import static systemtests.SummaryCommandSystemTest.Mode.BY_DATE;
import static systemtests.SummaryCommandSystemTest.Mode.BY_MONTH;

import org.junit.Ignore;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryByMonthCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.Month;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.summary.SummaryByDateList;
import seedu.planner.model.summary.SummaryByMonthList;
import seedu.planner.ui.SummaryEntry;

public class SummaryCommandSystemTest extends FinancialPlannerSystemTest {

    private static final String START_DATE = "25-3-2018";
    private static final String END_DATE = "25-5-2018";
    private static final String START_MONTH = "MAR-2018";
    private static final String END_MONTH = "MAY-2018";

    @Ignore
    public void summary() throws Exception {
        Model model = getModel();
        SummaryByDateList summariesByDate;
        SummaryByMonthList summariesByMonth;
        String defaultSummaryByDateCommand = "   " + SummaryCommand.COMMAND_WORD + "  "
                + SummaryByDateCommand.COMMAND_MODE_WORD + "  " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
        String defaultSummaryByMonthCommand = " " + SummaryCommand.COMMAND_WORD + " "
                + SummaryByMonthCommand.COMMAND_MODE_WORD + " " + PREFIX_DATE + " " + START_MONTH + " " + END_MONTH;

        /* ------------------------ Check starting state of program ------------------------------------------------- */

        /* Case: Perform an initial summaryByDate operation -> Success */
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);

        /* Case: Perform an initialSummaryByMonthOperation -> Success */
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* ------------------------ Clear starting state and check state -------------------------------------------- */

        /* Case: Clear initial financial planner -> nothing in summary table */
        clearModel(model);
        assertCommandSuccess(defaultSummaryByDateCommand, model, FXCollections.emptyObservableList(), BY_DATE);
        assertCommandSuccess(defaultSummaryByMonthCommand, model, FXCollections.emptyObservableList(), BY_MONTH);

        /* ---------------Add records into the program and check state -----------------------------------------------*/

        /* Case: Add 2 records (CAIFAN, ZT) different in date and month, then do summary by date and by month
        -> 2 entries in summary table */
        addRecord(model, CAIFAN);
        addRecord(model, ZT);
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* Case: Add 2 records with same date as ZT, then do summary by date and by month
        -> 2 entries in summary table */
        addRecord(model, JAP);
        addRecord(model, KOREAN);
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* Case: Add 2 records with a date that is not tracked by summary, then do summary by date and by month
         -> no change */
        addRecord(model, IDA);
        addRecord(model, BURSARY);
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* --------------------- Edit existing records and check state -----------------------------------------------*/

        /* Case: Finds the CAIFAN record and edit it with another date that is tracked by summary,
         then do summary by date and by month -> change is reflected in summary table
          */
        findRecord(model, CAIFAN);
        editRecord(model, 1, "1-4-2018");
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* Case: Finds the ZT record and edit it with another date that is not tracked by summary,
         then do summary by date and by month -> change is still reflected in summary table
         */
        findRecord(model, ZT);
        editRecord(model, 1, "1-4-2019");
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* --------------------- Deletes existing records and check state --------------------------------------------*/

        /* Case: Removes all records of a single date then do summary by date and by month
        ->
         */
        deleteRecordByDate(model, KOREAN.getDate().value);
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* Case: Removes records not tracked by summary and then do summary
        -> No change in summary table
         */
        findRecord(model, BURSARY);
        deleteRecord(model, 1);
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        findRecord(model, IDA);
        deleteRecord(model, 1);
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* ----------------------------------- Undo, redo and check state --------------------------------------------*/
        undoModel(model);
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);

        redoModel(model);
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        assertCommandSuccess(defaultSummaryByDateCommand, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        assertCommandSuccess(defaultSummaryByMonthCommand, model, summariesByMonth.getSummaryList(), BY_MONTH);
    }

    /**
     * Executes the ClearCommand on the ui and updates the expected model
     * @param model expectedModel to update
     */
    protected void clearModel(Model model) {
        executeCommand(ClearCommand.COMMAND_WORD);
        model.resetData(new FinancialPlanner());
        model.commitFinancialPlanner();
    }

    /**
     * Executes the {@code SummaryCommand} that queries the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code SummaryCommand}.<br>
     * 4. {@code Storage} and {@code RecordListPanel} remains unchanged <br>
     * 5. Selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel,
                                      ObservableList<SummaryEntry> expectedList, Mode mode) {
        requireAllNonNull();
        String expectedResultMessage;
        if (mode.equals(BY_DATE)) {
            expectedResultMessage = String.format(SummaryByDateCommand.MESSAGE_SUCCESS, expectedList.size());
        } else if (mode.equals(BY_MONTH)) {
            expectedResultMessage = String.format(SummaryByMonthCommand.MESSAGE_SUCCESS, expectedList.size());
        } else {
            expectedResultMessage = "";
        }
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
        assertSummaryDisplayShownCorrectly(expectedList);
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    enum Mode {
        BY_DATE, BY_MONTH
    }
}
