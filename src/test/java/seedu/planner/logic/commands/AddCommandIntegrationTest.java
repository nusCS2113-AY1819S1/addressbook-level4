package seedu.planner.logic.commands;

import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Before;
import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.RecordBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    }

    @Test
    public void execute_newRecord_success() {
        Record validRecord = new RecordBuilder().build();

        Model expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
        expectedModel.addRecord(validRecord);
        expectedModel.commitFinancialPlanner();

        assertCommandSuccess(new AddCommand(validRecord), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validRecord), expectedModel);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() {
        Record recordInList = model.getFinancialPlanner().getRecordList().get(0);
        assertCommandFailure(new AddCommand(recordInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_RECORD);
    }


}
