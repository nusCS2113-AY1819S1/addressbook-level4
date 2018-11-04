package systemtests;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.testutil.TypicalRecords.AA;
import static seedu.planner.testutil.TypicalRecords.BB;
import static seedu.planner.testutil.TypicalRecords.CHICKENRICE;
import static seedu.planner.testutil.TypicalRecords.CLASSMATE;
import static seedu.planner.testutil.TypicalRecords.FRIEND;
import static seedu.planner.testutil.TypicalRecords.KOREAN;
import static seedu.planner.testutil.TypicalRecords.MALA;
import static seedu.planner.testutil.TypicalRecords.PART_TIME;
import static seedu.planner.testutil.TypicalRecords.ZT;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.planner.logic.commands.StatisticCommand;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.CategoryStatistic;
import seedu.planner.model.summary.CategoryStatisticsList;
import seedu.planner.testutil.RecordBuilder;

public class StatisticCommandSystemTest extends FinancialPlannerSystemTest {

    private static final String START_DATE = "25-3-2018";
    private static final String END_DATE = "25-5-2018";
    private final Date startDate = new Date(START_DATE);
    private final Date endDate = new Date(END_DATE);
    private final Predicate<Record> predicate = new DateIsWithinIntervalPredicate(startDate, endDate);

    @Before
    public void setup() {

    }

    @Test
    public void stats() {
        Model model = getModel();
        String command = StatisticCommand.COMMAND_WORD + " " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
        String expectedResultMessage = String.format(StatisticCommand.MESSAGE_SUCCESS, startDate, endDate);

        /* ------------------------ Check starting state of program ------------------------------------------------- */
        /* Case: Starting state -> Income: CAIFAN, JAP, KOREAN; Expense: WORK, ZT, RANDOM */
        model.updateFilteredRecordList(predicate);
        ObservableList<CategoryStatistic> expectedStats = new CategoryStatisticsList(model.getFilteredRecordList())
                .getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        /* ------------------------ Clear the appplication ---------------------------------------------------------- */
        /* Case: Clear the app -> Nothing inside */
        clearModel(model);
        assertCommandSuccess(command, model, expectedResultMessage);
        assertIncomeCategoryBreakdownEmpty();
        assertExpenseCategoryBreakdownEmpty();

        /* -------------------------------------------- Undo and redo ----------------------------------------------- */
        /* Case: Undo -> Income: CAIFAN, JAP, KOREAN; Expense: WORK, ZT, RANDOM */
        undoModel(model);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        /* Case: Redo -> Nothing inside */
        redoModel(model);
        model.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownEmpty();
        assertIncomeBreakdownEmpty();

        /* -------------------------------------------- Adding records ---------------------------------------------- */
        /* Case: Add 2 records different tags, both expense -> Expense: KOREAN, MALA */
        addRecord(model, KOREAN);
        addRecord(model, MALA);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownEmpty();

        clearModel(model);

        /* Case: Add 2 records different tags, both income -> Income: PART_TIME, FRIEND */
        addRecord(model, PART_TIME);
        addRecord(model, FRIEND);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownEmpty();
        assertIncomeBreakdownNotEmpty(expectedStats);

        undoModel(model);

        /* Case: Add 2 records same tags, income and expense -> Income: PART_TIME, FRIEND; Expense : CLASSMATE */
        addRecord(model, CLASSMATE);
        addRecord(model, FRIEND);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        /* Case: Add records outside of date range -> Income: PART_TIME, FRIEND; Expense : CLASSMATE */
        addRecord(model, CHICKENRICE);
        model.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        /* -------------------------------------------- Removing records -------------------------------------------- */
        /* Case: Delete record outside of date range -> Income: PART_TIME, FRIEND; Expense : CLASSMATE */
        findRecord(model, CHICKENRICE);
        deleteRecord(model, 1);
        model.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        /* Case: Delete record unique tag -> Income: FRIEND; Expense : CLASSMATE */
        findRecord(model, PART_TIME);
        deleteRecord(model, 1);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        addRecord(model, ZT);
        addRecord(model, AA);
        addRecord(model, BB);

        /* Case: Delete record with expense and overlapping tag -> Income:  ZT; Expense: AA, BB*/
        findRecord(model, CLASSMATE);
        deleteRecord(model, 1);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        undoModel(model);

        /* Case: Edit tag of existing record with expense to existing tag -> Income: FRIEND, ZT;
        * Expense:  CLASSMATE, AA, BB */
        Record newAa = new RecordBuilder(AA).withTags("BBBB").build();
        findRecord(model, AA);
        editRecord(model, 1, newAa);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        /* Case: Edit moneyflow of existing record with expense to that of income -> Income: FRIEND, ZT, AA;
        * Expense: CLASSMATE, BB */

        newAa = new RecordBuilder(AA).withMoneyFlow("+67.50").build();
        findRecord(model, AA);
        editRecord(model, 1, newAa);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);
    }

    /**
     * Executes the {@code StatisticCommand} that queries the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code StatisticCommand}.<br>
     * 4. {@code Storage} and {@code RecordListPanel} remains unchanged <br>
     * 5. Selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        requireAllNonNull(command, expectedModel);
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
        assertCategoryBreakdownsAreShown();
    }

    private void assertExpenseBreakdownNotEmpty(ObservableList<CategoryStatistic> expectedStats) {
        assertExpenseCategoryBreakdownDataCorrect(expectedStats);
    }

    private void assertIncomeBreakdownNotEmpty(ObservableList<CategoryStatistic> expectedStats) {
        assertIncomeCategoryBreakdownDataCorrect(expectedStats);
    }

    private void assertExpenseBreakdownEmpty() {
        assertExpenseCategoryBreakdownEmpty();
    }

    private void assertIncomeBreakdownEmpty() {
        assertIncomeCategoryBreakdownEmpty();
    }
}
