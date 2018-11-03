package systemtests;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

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
        model.updateFilteredRecordList(predicate);
        ObservableList<CategoryStatistic> expectedStats = new CategoryStatisticsList(model.getFilteredRecordList())
                .getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        /* ------------------------ Clear the appplication ---------------------------------------------------------- */
        clearModel(model);
        assertCommandSuccess(command, model, expectedResultMessage);
        assertIncomeCategoryBreakdownEmpty();
        assertExpenseCategoryBreakdownEmpty();

        /* -------------------------------------------- Undo and redo ----------------------------------------------- */
        undoModel(model);
        model.updateFilteredRecordList(predicate);
        expectedStats = new CategoryStatisticsList(model.getFilteredRecordList()).getReadOnlyStatsList();
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownNotEmpty(expectedStats);
        assertIncomeBreakdownNotEmpty(expectedStats);

        redoModel(model);
        model.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, expectedResultMessage);
        assertExpenseBreakdownEmpty();
        assertIncomeBreakdownEmpty();
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
