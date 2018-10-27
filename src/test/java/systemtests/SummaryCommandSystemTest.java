package systemtests;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.commons.util.DateUtil.generateFirstOfMonth;
import static seedu.planner.commons.util.DateUtil.generateLastOfMonth;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.CHICKENRICE;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.MALA;
import static seedu.planner.testutil.TypicalRecords.WORK;
import static seedu.planner.testutil.TypicalRecords.ZT;
import static systemtests.SummaryCommandSystemTest.Mode.BY_DATE;
import static systemtests.SummaryCommandSystemTest.Mode.BY_MONTH;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.logic.commands.AddCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryByMonthCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.Month;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.SummaryByDateList;
import seedu.planner.model.summary.SummaryByMonthList;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.ui.SummaryEntry;

public class SummaryCommandSystemTest extends FinancialPlannerSystemTest {

    private static final String INDO_DATE = INDO.getDate().value;
    private static final String CAIFAN_DATE = CAIFAN.getDate().value;
    private static final String WORK_DATE = WORK.getDate().value;
    private static final String ZT_DATE = ZT.getDate().value;
    private static final String MALA_DATE = MALA.getDate().value;
    private static final String CHICKEN_RICE_DATE = CHICKENRICE.getDate().value;

    private static final String START_DATE = "25-3-2018";
    private static final String END_DATE = "25-5-2018";
    private static final String START_MONTH = "MAR-2018";
    private static final String END_MONTH = "MAY-2018";
    private static final String UNTRACKED_DATE = "12-12-2018";

    @Test
    public void summary() {
        Model model = getModel();
        SummaryByDateList summariesByDate;
        SummaryByMonthList summariesByMonth;
        String command;

        /* ------------------------ Check starting state of program ------------------------------------------------- */

        /* Case: Perform an initial summaryByDate operation -> Success */
        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        command = "   " + SummaryCommand.COMMAND_WORD + "  " + SummaryByDateCommand.COMMAND_MODE_WORD
                + "  " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
        assertCommandSuccess(command, model, summariesByDate.getSummaryList(), BY_DATE);

        /* Case: Perform an initialSummaryByMonthOperation -> Success */
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        command = " " + SummaryCommand.COMMAND_WORD + " " + SummaryByMonthCommand.COMMAND_MODE_WORD
                + " " + PREFIX_DATE + " " + START_MONTH + " " + END_MONTH;
        assertCommandSuccess(command, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* ------------------------ Clear starting state and check state -------------------------------------------- */

        /* Case: Clear initial financial planner -> nothing in summary table */
        command = ClearCommand.COMMAND_WORD;
        executeCommand(command);
        clearModel(model);

        command = "   " + SummaryCommand.COMMAND_WORD + "  " + SummaryByDateCommand.COMMAND_MODE_WORD
                + "  " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
        assertCommandSuccess(command, model, FXCollections.emptyObservableList(), BY_DATE);
        command = " " + SummaryCommand.COMMAND_WORD + " " + SummaryByMonthCommand.COMMAND_MODE_WORD
                + " " + PREFIX_DATE + " " + START_MONTH + " " + END_MONTH;
        assertCommandSuccess(command, model, FXCollections.emptyObservableList(), BY_MONTH);

        /* ---------------Add records into the program and check state -----------------------------------------------*/

        /* Case : Add 2 records different in date and month, then do summary by date and by month
        -> 2 entries in summary table */
        command = "   " + AddCommand.COMMAND_WORD + "  " + PREFIX_NAME + CAIFAN.getName().fullName
                + " " + PREFIX_DATE + CAIFAN_DATE + " " + PREFIX_MONEYFLOW + CAIFAN.getMoneyFlow().value;
        executeCommand(command);
        command = "   " + AddCommand.COMMAND_WORD + "  " + PREFIX_NAME + ZT.getName().fullName
                + " " + PREFIX_DATE + ZT_DATE + " " + PREFIX_MONEYFLOW + ZT.getMoneyFlow().value;
        executeCommand(command);
        model.addRecord(removeTagsFromRecord(CAIFAN));
        model.addRecord(removeTagsFromRecord(ZT));

        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        command = "   " + SummaryCommand.COMMAND_WORD + "  " + SummaryByDateCommand.COMMAND_MODE_WORD
                + "  " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
        assertCommandSuccess(command, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        command = " " + SummaryCommand.COMMAND_WORD + " " + SummaryByMonthCommand.COMMAND_MODE_WORD
                + " " + PREFIX_DATE + " " + START_MONTH + " " + END_MONTH;
        assertCommandSuccess(command, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* Case : Add 1 record with same date as ZT, then do summary by date and by month
        -> 2 entries in summary table */
        command = "   " + AddCommand.COMMAND_WORD + "  " + PREFIX_NAME + INDO.getName().fullName
                + " " + PREFIX_DATE + ZT_DATE + " " + PREFIX_MONEYFLOW + INDO.getMoneyFlow().value;
        executeCommand(command);
        Record toAdd = new RecordBuilder(ZT).withName(INDO.getName().fullName)
                .withMoneyFlow(INDO.getMoneyFlow().value).withTags().build();
        model.addRecord(toAdd);

        summariesByDate = new SummaryByDateList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(START_DATE, END_DATE));
        command = "   " + SummaryCommand.COMMAND_WORD + "  " + SummaryByDateCommand.COMMAND_MODE_WORD
                + "  " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
        assertCommandSuccess(command, model, summariesByDate.getSummaryList(), BY_DATE);
        summariesByMonth = new SummaryByMonthList(model.getFinancialPlanner().getRecordList(),
                new DateIsWithinIntervalPredicate(generateFirstOfMonth(new Month(START_MONTH)),
                        generateLastOfMonth(new Month(END_MONTH))));
        command = " " + SummaryCommand.COMMAND_WORD + " " + SummaryByMonthCommand.COMMAND_MODE_WORD
                + " " + PREFIX_DATE + " " + START_MONTH + " " + END_MONTH;
        assertCommandSuccess(command, model, summariesByMonth.getSummaryList(), BY_MONTH);

        /* Case : Add 2 records with a date that is not tracked by summary -> no change */
        command = "   " + AddCommand.COMMAND_WORD + "  " + PREFIX_NAME + CAIFAN.getName().fullName + "a"
                + " " + PREFIX_DATE + UNTRACKED_DATE + " " + PREFIX_MONEYFLOW + INDO.getMoneyFlow().value;
        executeCommand(command);
        toAdd = new RecordBuilder(ZT).withName(CAIFAN.getName().fullName + "a")
                .withMoneyFlow(INDO.getMoneyFlow().value).withTags().withDate(UNTRACKED_DATE).build();
        model.addRecord(toAdd);
        command = "   " + AddCommand.COMMAND_WORD + "  " + PREFIX_NAME + MALA.getName().fullName
                + " " + PREFIX_DATE + UNTRACKED_DATE + " " + PREFIX_MONEYFLOW + MALA.getMoneyFlow().value;
        executeCommand(command);
        toAdd = new RecordBuilder(MALA).withTags().withDate(UNTRACKED_DATE).build();
        model.addRecord(toAdd);

        command = "   " + SummaryCommand.COMMAND_WORD + "  " + SummaryByDateCommand.COMMAND_MODE_WORD
                + "  " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
        assertCommandSuccess(command, model, summariesByDate.getSummaryList(), BY_DATE);
        command = " " + SummaryCommand.COMMAND_WORD + " " + SummaryByMonthCommand.COMMAND_MODE_WORD
                + " " + PREFIX_DATE + " " + START_MONTH + " " + END_MONTH;
        assertCommandSuccess(command, model, summariesByMonth.getSummaryList(), BY_MONTH);
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

    private void clearModel(Model model) {
        model.resetData(new FinancialPlanner());
        model.commitFinancialPlanner();
    }

    private Record removeTagsFromRecord(Record record) {
        return new RecordBuilder(record).withTags().build();
    }

    enum Mode {
        BY_DATE, BY_MONTH
    }
}
