package systemtests;

import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCalendarCommandSystemTest {
    private Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
}
