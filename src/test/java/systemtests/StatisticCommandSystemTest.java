package systemtests;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.JAP;
import static seedu.planner.testutil.TypicalRecords.KOREAN;
import static seedu.planner.testutil.TypicalRecords.RANDOM;
import static seedu.planner.testutil.TypicalRecords.WORK;
import static seedu.planner.testutil.TypicalRecords.ZT;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.logic.commands.StatisticCommand;
import seedu.planner.model.Model;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.CategoryStatistic;

public class StatisticCommandSystemTest extends FinancialPlannerSystemTest {

    private static final String START_DATE = "25-3-2018";
    private static final String END_DATE = "25-5-2018";

    @Test
    public void stats() throws Exception {
        Model model = getModel();
        String command = StatisticCommand.COMMAND_WORD + " " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
        List<Record> records = Arrays.asList(RANDOM, CAIFAN, WORK, KOREAN, JAP, ZT);
        assertCommandSuccess(command, model, FXCollections.emptyObservableList());
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
    private void assertCommandSuccess(String command, Model expectedModel,
                                      ObservableList<CategoryStatistic> expectedStats) {
        requireAllNonNull(command, expectedModel, expectedStats);
        String expectedResultMessage = StatisticCommand.MESSAGE_SUCCESS;
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
        assertCategoryBreakdownShownCorrectly(expectedStats);
    }
}
