package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.MonthlyExpenseCommand.MESSAGE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.DisplayMonthlyExpenseEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.testutil.EventsCollectorRule;

public class MonthlyExpenseCommandTest {

    private static final String SAMPLE_MONTH = "11/2018";

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_help_success() {
        assertCommandSuccess(
                new MonthlyExpenseCommand(SAMPLE_MONTH), model, commandHistory, MESSAGE_SUCCESS, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplayMonthlyExpenseEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
