package seedu.planner.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.RANDOM;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code StatisticCommand}.
 */

public class StatisticCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Date startDate = new Date("1-2-2018");
    private final Date endDate = new Date("1-4-2018");
    private final Predicate datePredicate = new DateIsWithinIntervalPredicate(startDate, endDate);

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
    public void constructor_nullDates_throwsException() {
        thrown.expect(NullPointerException.class);
        new StatisticCommand(null, null);
    }

    @Test
    public void filterRecordListByPredicate_emptyList_givesCorrectList() {
        StatisticCommand command = new StatisticCommand(startDate, endDate);
        List<Record> inputList = emptyModel.getFinancialPlanner().getRecordList();
        List<Record> expectedList = Collections.emptyList();
        assertTwoListsEqualNoOrder(expectedList, command.filterRecordListByPredicate(inputList, datePredicate));
    }

    @Test
    public void filterRecordListByPredicate_validList_givesCorrectList() {
        StatisticCommand command = new StatisticCommand(startDate, endDate);
        List<Record> inputList = model.getFinancialPlanner().getRecordList();
        List<Record> expectedList = Arrays.asList(RANDOM, INDO);
        assertTwoListsEqualNoOrder(expectedList, command.filterRecordListByPredicate(inputList, datePredicate));
    }

    @Test
    public void execute_model_modelUnchanged() {
        StatisticCommand command = new StatisticCommand(startDate, endDate);
        assertCommandSuccess(command, model, commandHistory, StatisticCommand.MESSAGE_SUCCESS, expectedModel);
        assertCommandSuccess(command, emptyModel, commandHistory, StatisticCommand.MESSAGE_SUCCESS, expectedEmptyModel);
    }

    private void assertTwoListsEqualNoOrder(List expectedList, List listToTest) {
        assertTrue(expectedList.containsAll(listToTest));
    }
}
